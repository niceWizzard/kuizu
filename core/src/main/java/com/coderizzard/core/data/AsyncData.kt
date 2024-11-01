package com.coderizzard.core.data

sealed interface AsyncData<out T> {
    data object Loading : AsyncData<Nothing>
    data class Success<T>(val data : T) : AsyncData<T>
    data class Error(val message : String, val error : Throwable?) : AsyncData<Nothing>
}