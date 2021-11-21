package info.rashadtanjim.interactivephotogallery.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PicsumPhotosItem(
    @ColumnInfo(name = "author") val author: String,
    val download_url: String,
    val height: Int,
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int
)