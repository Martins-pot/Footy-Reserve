package com.mertswork.footyreserve.core.data.repository


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import com.mertswork.footyreserve.utils.ContextProvider
import com.mertswork.footyreserve.utils.ImageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream


actual suspend fun getImageBytes(uri: String): ByteArray = withContext(Dispatchers.IO) {
    try {
        val context = ContextProvider.getContext()
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(Uri.parse(uri))
        inputStream?.use { it.readBytes() } ?: byteArrayOf()
    } catch (e: Exception) {
        println("Error reading image bytes: ${e.message}")
        byteArrayOf()
    }
}

actual suspend fun getCompressedImageData(uri: String): ByteArray = withContext(Dispatchers.IO) {
    try {
        val context = ContextProvider.getContext()
        val contentResolver = context.contentResolver
        val imageUri = Uri.parse(uri)

        // Get input stream
        val inputStream: InputStream = contentResolver.openInputStream(imageUri)
            ?: return@withContext byteArrayOf()

        // Decode with inSampleSize to reduce memory usage
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        // Get image dimensions without loading the full image
        BitmapFactory.decodeStream(inputStream, null, options)
        inputStream.close()

        // Calculate sample size
        options.inSampleSize = calculateInSampleSize(options, ImageUtils.MAX_DIMENSION, ImageUtils.MAX_DIMENSION)
        options.inJustDecodeBounds = false

        // Decode the actual bitmap
        val newInputStream = contentResolver.openInputStream(imageUri)
            ?: return@withContext byteArrayOf()

        val bitmap = BitmapFactory.decodeStream(newInputStream, null, options)
            ?: return@withContext byteArrayOf()

        newInputStream.close()

        // Rotate bitmap if needed based on EXIF data
        val rotatedBitmap = rotateImageIfRequired(bitmap, imageUri)

        // Compress bitmap to JPEG
        val outputStream = ByteArrayOutputStream()
        var quality = ImageUtils.COMPRESSION_QUALITY

        do {
            outputStream.reset()
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            quality -= 10
        } while (outputStream.size() > ImageUtils.MAX_IMAGE_SIZE_BYTES && quality > 10)

        val result = outputStream.toByteArray()

        // Clean up
        if (bitmap != rotatedBitmap) {
            bitmap.recycle()
        }
        rotatedBitmap.recycle()
        outputStream.close()

        println("Compressed image size: ${result.size} bytes")
        result

    } catch (e: Exception) {
        println("Error compressing image: ${e.message}")
        byteArrayOf()
    }
}

private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

private fun rotateImageIfRequired(bitmap: Bitmap, imageUri: Uri): Bitmap {
    return try {
        val context = ContextProvider.getContext()
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val exif = inputStream?.let { ExifInterface(it) }
        inputStream?.close()

        val orientation = exif?.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        ) ?: ExifInterface.ORIENTATION_NORMAL

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
            else -> bitmap
        }
    } catch (e: Exception) {
        println("Error rotating image: ${e.message}")
        bitmap
    }
}

private fun rotateImage(bitmap: Bitmap, degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
