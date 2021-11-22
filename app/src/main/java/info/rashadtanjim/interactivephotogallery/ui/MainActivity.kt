package info.rashadtanjim.interactivephotogallery.ui

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.databinding.ActivityMainBinding
import info.rashadtanjim.interactivephotogallery.ui.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

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