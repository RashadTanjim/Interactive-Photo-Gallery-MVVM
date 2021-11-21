package info.rashadtanjim.interactivephotogallery.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photo")
data class PicsumPhotosItem(
    val author: String,
    val download_url: String,
    val height: Int,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    val url: String,
    val width: Int
)