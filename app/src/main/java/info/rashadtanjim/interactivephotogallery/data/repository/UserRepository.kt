package info.rashadtanjim.interactivephotogallery.data.repository

import info.rashadtanjim.interactivephotogallery.data.source.local.AppDatabase
import info.rashadtanjim.interactivephotogallery.data.source.local.PicsumDao
import info.rashadtanjim.interactivephotogallery.data.source.remote.PicsumApi

class UserRepository(
    private val api: PicsumApi,
    private val picsumDao: PicsumDao,
    private val db: AppDatabase? = null
) : BaseRepository() {

    suspend fun getPicsumPhotos() = safeApiCall {
        api.getPicsumPhotos()
    }
}