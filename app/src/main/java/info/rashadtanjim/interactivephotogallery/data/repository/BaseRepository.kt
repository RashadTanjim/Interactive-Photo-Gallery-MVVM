package info.rashadtanjim.interactivephotogallery.data.repository

import android.util.Log
import info.rashadtanjim.interactivephotogallery.data.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): DataState<T> {
        return withContext(Dispatchers.IO) {
            try {
                DataState.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.e("Api Error", throwable.message, throwable)
                when (throwable) {
                    is HttpException -> {
                        DataState.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        DataState.Failure(true, null, null)
                    }
                }
            }
        }
    }
}