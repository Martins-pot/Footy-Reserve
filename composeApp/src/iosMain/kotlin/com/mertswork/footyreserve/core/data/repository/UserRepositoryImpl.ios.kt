package com.mertswork.footyreserve.core.data.repository


import kotlinx.cinterop.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun getImageBytes(uri: String): ByteArray = withContext(Dispatchers.Main) {
    try {
        val url = NSURL.URLWithString(uri)
        val data = NSData.dataWithContentsOfURL(url ?: return@withContext byteArrayOf())

        data?.let {
            val bytes = ByteArray(it.length.toInt())
            memcpy(bytes.refTo(0), it.bytes, it.length)
            bytes
        } ?: byteArrayOf()
    } catch (e: Exception) {
        println("Error reading image bytes: ${e.message}")
        byteArrayOf()
    }
}

@OptIn(ExperimentalForeignApi::class)
actual suspend fun getCompressedImageData(uri: String): ByteArray = withContext(Dispatchers.Main) {
    try {
        val url = NSURL.URLWithString(uri) ?: return@withContext byteArrayOf()
        val data = NSData.dataWithContentsOfURL(url) ?: return@withContext byteArrayOf()

        // Create UIImage from data
        val image = UIImage.imageWithData(data) ?: return@withContext byteArrayOf()

        // Resize image if too large
        val resizedImage = resizeImage(image, ImageUtils.MAX_DIMENSION.toDouble())

        // Compress to JPEG
        var quality = ImageUtils.COMPRESSION_QUALITY.toDouble() / 100.0
        var compressedData: NSData?

        do {
            compressedData = UIImageJPEGRepresentation(resizedImage, quality)
            quality -= 0.1
        } while (compressedData != null &&
            compressedData!!.length > ImageUtils.MAX_IMAGE_SIZE_BYTES.toULong() &&
            quality > 0.1)

        compressedData?.let { data ->
            val bytes = ByteArray(data.length.toInt())
            memcpy(bytes.refTo(0), data.bytes, data.length)
            println("Compressed image size: ${bytes.size} bytes")
            bytes
        } ?: byteArrayOf()

    } catch (e: Exception) {
        println("Error compressing image: ${e.message}")
        byteArrayOf()
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun resizeImage(image: UIImage, maxDimension: Double): UIImage {
    val size = image.size
    val maxSize = maxOf(size.width, size.height)

    if (maxSize <= maxDimension) {
        return image
    }

    val scale = maxDimension / maxSize
    val newSize = CGSizeMake(size.width * scale, size.height * scale)

    UIGraphicsBeginImageContextWithOptions(newSize, false, 0.0)
    image.drawInRect(CGRectMake(0.0, 0.0, newSize.width, newSize.height))
    val resizedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return resizedImage ?: image
}