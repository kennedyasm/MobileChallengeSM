package com.challenges.mobilechallengesm.utils

sealed class Output<out T> {
    data class Success<out T : Any>(val data: T) : Output<T>()
    object Loading : Output<Nothing>()
    data class Error(var message: String?) : Output<Nothing>()
}