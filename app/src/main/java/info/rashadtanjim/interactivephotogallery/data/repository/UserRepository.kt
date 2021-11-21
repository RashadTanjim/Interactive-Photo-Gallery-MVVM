package info.rashadtanjim.interactivephotogallery.data.repository

import info.rashadtanjim.interactivephotogallery.data.source.local.AppDatabase
import info.rashadtanjim.interactivephotogallery.data.source.remote.PicsumApi

class UserRepository(
    private val api: PicsumApi,
    private val db: AppDatabase? = null
) : BaseRepository() {

    suspend fun getPicsumPhotos(pageNo: Int, limitCount: Int) = safeApiCall {
        api.getPicsumPhotos(pageNo, limitCount)
    }
}