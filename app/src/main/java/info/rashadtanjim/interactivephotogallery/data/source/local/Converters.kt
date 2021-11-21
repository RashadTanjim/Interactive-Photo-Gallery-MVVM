package info.rashadtanjim.interactivephotogallery.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotoList

class Converters {
    @TypeConverter
    fun listToJson(value: List<PicsumPhotoList>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<PicsumPhotoList>::class.java).toList()

    @TypeConverter
    fun topicsToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToTopics(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}