package info.rashadtanjim.interactivephotogallery.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import info.rashadtanjim.interactivephotogallery.data.UserPreferences
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}