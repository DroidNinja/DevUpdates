package com.dev.network.utils

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

/**
 * A [Converter] that only decodes responses with a given [convertBody].
 */
class DecodingConverter<T> private constructor(
    private val convertBody: (ResponseBody) -> T
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        return convertBody(value)
    }

    companion object {
        /** Creates a new factory for creating converter. We only care about decoding responses. */
        fun <T> newFactory(decoder: (ResponseBody) -> T): Converter.Factory {
            return object : Converter.Factory() {
                private val converter by lazy { DecodingConverter(decoder) }
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ) = converter
            }
        }
    }
}
