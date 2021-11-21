package info.rashadtanjim.interactivephotogallery.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject


class UserPreferences @Inject constructor(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "photo_store")


}