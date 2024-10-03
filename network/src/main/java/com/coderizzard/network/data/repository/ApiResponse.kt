package com.coderizzard.network.data.repository


sealed interface ApiResponse<T> {
    data class Success<T>(val value: T) : ApiResponse<T>
    data class Error<T>(val message: String) : ApiResponse<T>
}