package info.rashadtanjim.interactivephotogallery.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.scopes.ActivityScoped
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.databinding.ActivityMainBinding

@ActivityScoped
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor =
            ResourcesCompat.getColor(resources, R.color.background, null)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}