package com.coderizzard.core

import com.coderizzard.core.data.AsyncData

fun <T> Result<T>.toAsyncData() : AsyncData<T> {
    return if(isFailure ){
        val err = this.exceptionOrNull()!!
        AsyncData.Error(err.message.toString(), err)
    }
    else {
        AsyncData.Success(this.getOrThrow())
    }
}