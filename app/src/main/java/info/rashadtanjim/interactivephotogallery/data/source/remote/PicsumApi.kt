package info.rashadtanjim.interactivephotogallery.data.source.remote

import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotoList
import retrofit2.http.GET
import retrofit2.http.Path

interface PicsumApi {

    @GET("list?page={page_no}&limit={limit_count}")
    suspend fun getPicsumPhotos(
        @Path("page_no") pageNo: Int,
        @Path("limit_count") limitCount: Int
    ): PicsumPhotoList

}