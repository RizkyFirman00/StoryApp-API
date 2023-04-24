package com.example.submissionintermediate.utils

sealed class ResultMain<T>(
    val value: T? = null,
    val message: String = "",
) {
    class Success<T>(value: T) : ResultMain<T>(value)

    class Idle<T> : ResultMain<T>()

    class Loading<T> : ResultMain<T>()

    class Error<T>(message: String, value: T? = null) :
        ResultMain<T>(value, message)
}
