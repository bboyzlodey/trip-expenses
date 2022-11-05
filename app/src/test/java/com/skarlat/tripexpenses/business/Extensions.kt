package com.skarlat.tripexpenses.business

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import okio.Buffer

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> Moshi.readObjectFromJsonResource(resourceName: String): T {
    val jsonStream = ClassLoader.getSystemResourceAsStream(resourceName)
    val buffer = Buffer()
    buffer.readFrom(jsonStream)
    val deserializedObject = adapter<T>().fromJson(buffer)
    jsonStream.close()
    buffer.clear()
    return deserializedObject ?: throw IllegalStateException("Can't parse resource $resourceName")
}