package info.rashadtanjim.interactivephotogallery.data.source.remote

import info.rashadtanjim.core.utlis.Constants
import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotoList
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {
    @GET("list/")
    suspend fun getPicsumPhotos(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = Constants.API.PHOTO_LIMIT
    ): PicsumPhotoList
}