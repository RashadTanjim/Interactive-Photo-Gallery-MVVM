package info.rashadtanjim.interactivephotogallery.domain.model


import androidx.room.PrimaryKey

data class PicsumPhotosItem(
    val author: String,
    val download_url: String,
    val height: Int,
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int
)