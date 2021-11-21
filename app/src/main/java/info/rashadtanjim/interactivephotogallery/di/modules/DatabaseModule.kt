package info.rashadtanjim.interactivephotogallery.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.rashadtanjim.interactivephotogallery.data.source.local.AppDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun providePicsumDao(db: AppDatabase) = db.PicsumDao()
}