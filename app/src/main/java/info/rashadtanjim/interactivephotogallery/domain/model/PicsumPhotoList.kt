package info.rashadtanjim.interactivephotogallery.domain.model

import androidx.room.Entity

@Entity(tableName = "photoList")
class PicsumPhotoList(
    val PicsumPhotoList: List<PicsumPhotosItem>?,
)