package com.mertswork.footyreserve.core.data.repository


import android.content.Context
import android.net.Uri
import com.mertswork.footyreserve.utils.ContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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