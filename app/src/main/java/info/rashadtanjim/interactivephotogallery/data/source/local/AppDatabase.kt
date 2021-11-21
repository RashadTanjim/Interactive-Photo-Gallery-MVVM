package info.rashadtanjim.interactivephotogallery.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotoList
import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotosItem


@Database(
    entities = [PicsumPhotosItem::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun PicsumDao(): PicsumDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(appContext: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    AppDatabase::class.java,
                    "interactive_photo_gallery"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                instance
            }
        }
    }
}