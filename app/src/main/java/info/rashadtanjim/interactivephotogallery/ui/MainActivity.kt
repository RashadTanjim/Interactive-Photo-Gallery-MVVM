package info.rashadtanjim.interactivephotogallery.ui

import android.animation.Animator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.data.UserPreferences
import info.rashadtanjim.interactivephotogallery.databinding.ActivityMainBinding
import info.rashadtanjim.interactivephotogallery.ui.base.BaseActivity
import info.rashadtanjim.interactivephotogallery.ui.gallery.GalleryActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor =
            ResourcesCompat.getColor(resources, R.color.background, null)

        binding.lottie.setAnimation(R.raw.gallary_lottie)
        binding.lottie.playAnimation()
        binding.lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                startActivity(Intent(this@MainActivity, GalleryActivity::class.java))
                finishAffinity()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

    }
}