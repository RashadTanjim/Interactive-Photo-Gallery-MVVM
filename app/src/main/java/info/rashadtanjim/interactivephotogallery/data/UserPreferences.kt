package info.rashadtanjim.interactivephotogallery.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserPreferences @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "photo_gallery")

//    val nightMode: Flow<Boolean?>
//        get() = context.dataStore.data.map { preferences ->
//            preferences[NIGHT_MODE]
//        }
//
//    suspend fun toggleNightMode(isNightMode: Boolean) {
//        context.dataStore.edit { preferences ->
//            preferences[NIGHT_MODE] = isNightMode
//        }
//    }

    companion object {
        private val NIGHT_MODE = booleanPreferencesKey("night_mode")
    }
}