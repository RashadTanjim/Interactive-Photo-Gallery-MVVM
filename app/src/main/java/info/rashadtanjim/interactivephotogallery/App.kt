package info.rashadtanjim.interactivephotogallery

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import info.rashadtanjim.core.utlis.isNight
import info.rashadtanjim.interactivephotogallery.data.UserPreferences
import info.rashadtanjim.interactivephotogallery.data.source.local.AppDatabase
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        /**
         * setting night mode use preferences which will save the value in local database name photo_store
         */

        setupDayNightMode()
    }

    fun setupDayNightMode() {    // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }


    companion object {
        lateinit var appContext: Context
    }
}