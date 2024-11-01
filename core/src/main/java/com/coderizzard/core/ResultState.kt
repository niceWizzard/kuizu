package com.coderizzard.core

import com.coderizzard.core.data.AsyncData

sealed interface ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val message: String, val exception: Throwable? = null) : ResultState<Nothing>

    fun toAsyncData() : AsyncData<T> {
        return when(this) {
            is Error -> AsyncData.Error(message, exception)
            is Success -> AsyncData.Success(data)
        }
    }
}