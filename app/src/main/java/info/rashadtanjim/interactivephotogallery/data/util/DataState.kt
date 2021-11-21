package info.rashadtanjim.interactivephotogallery.data.util

import okhttp3.ResponseBody

sealed class DataState<out T> {
    data class Success<out T>(val value: T) : DataState<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : DataState<Nothing>()

    object Loading : DataState<Nothing>()
}