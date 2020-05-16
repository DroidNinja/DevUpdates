package com.dev.network.model

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class APIErrorException(apiError: APIError) : Throwable(apiError.errorMessage) {

    companion object {

        const val ERROR_CODE_TIMEOUT = "504"
        const val ERROR_MESSAGE_TIMEOUT = "Couldn't connect to server"

        const val ERROR_CODE_UNKNOWN = "-1"
        const val ERROR_MESSAGE_UNKNOWN =
            "Something went wrong and we are sorry for that. Please try again later"

        fun newInstance(throwable: Throwable): APIErrorException {
            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                return httpError(response)
            }
            // A network error happened
            return if (throwable is IOException) {
                networkError()
            } else {
                // We don't know what happened. We need to simply convert to an unknown error
                unexpectedError(throwable)
            }
        }

        private fun unexpectedError(exception: Throwable): APIErrorException {
            exception.printStackTrace()
            return APIErrorException(APIError(ERROR_CODE_UNKNOWN, ERROR_MESSAGE_UNKNOWN))
        }

        private fun httpError(response: Response<*>?): APIErrorException {
            val message = response?.code()?.toString() + " " + response?.message()
            return APIErrorException(APIError(response?.code().toString(), message))
        }

        private fun networkError(): APIErrorException {
            return APIErrorException(APIError(ERROR_CODE_TIMEOUT, ERROR_MESSAGE_TIMEOUT))
        }
    }
}