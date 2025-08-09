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