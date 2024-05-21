package com.rasyidin.connectopia.utils

sealed class UiEvent<T> {
    class Idle<T> : UiEvent<T>()
    class Loading<T> : UiEvent<T>()
    data class Success<T>(val data : T?) : UiEvent<T>()
    data class Failure<T>(val message : UiText) : UiEvent<T>()
}