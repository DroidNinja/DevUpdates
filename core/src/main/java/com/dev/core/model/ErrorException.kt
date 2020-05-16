package com.dev.core.model

open class ErrorException(error: String?) : Throwable(error) {

    companion object {

        const val UNKNOWN_ERROR = "Something went wrong"

        fun unexpectedError(exception: Throwable): ErrorException {
            exception.printStackTrace()
            return ErrorException(UNKNOWN_ERROR)
        }

        fun unexpectedError(message: String?): ErrorException {
            return ErrorException(message)
        }
    }
}