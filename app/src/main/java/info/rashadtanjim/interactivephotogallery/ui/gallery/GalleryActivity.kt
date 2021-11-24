package info.rashadtanjim.interactivephotogallery.ui.gallery

import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.data.UserPreferences
import info.rashadtanjim.interactivephotogallery.databinding.ActivityGalleryBinding
import info.rashadtanjim.interactivephotogallery.ui.base.BaseActivity
import javax.inject.Inject

@AndroidEntryPoint
class GalleryActivity : BaseActivity() {
    private lateinit var binding: ActivityGalleryBinding

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor =
            ResourcesCompat.getColor(resources, R.color.background, null)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor =  ResourcesCompat.getColor(resources, R.color.statusBarColor, null)
    }
}