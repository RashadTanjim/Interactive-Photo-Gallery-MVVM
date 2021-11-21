package info.rashadtanjim.interactivephotogallery

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import info.rashadtanjim.interactivephotogallery.data.UserPreferences
import info.rashadtanjim.interactivephotogallery.data.source.local.AppDatabase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlinx.coroutines.flow.first

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
        val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)

        val isNightMode = runBlocking {
            if (mode == Configuration.UI_MODE_NIGHT_YES) {
                userPreferences.toggleNightMode(true)
            }
            userPreferences.nightMode.first()
        }

        if (isNightMode == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }


    companion object {
        lateinit var appContext: Context
    }
}