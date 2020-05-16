package com.dev.network.model


sealed class ResponseStatus<T> {
    data class Progress<T>(var loading: Boolean = true) : ResponseStatus<T>()
    data class Success<T>(val data: T) : ResponseStatus<T>()
    data class Failure<T>(val exception: APIErrorException) : ResponseStatus<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): ResponseStatus<T> = Progress(isLoading)
        fun <T> success(data: T): ResponseStatus<T> {
            loading<T>(false)
            return Success(data)
        }

        fun <T> failure(e: APIErrorException): ResponseStatus<T> {
            loading<T>(false)
            return Failure(e)
        }
    }
}
