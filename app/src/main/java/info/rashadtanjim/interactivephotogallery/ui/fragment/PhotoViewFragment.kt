package info.rashadtanjim.interactivephotogallery.ui.fragment

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink.*
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import info.rashadtanjim.core.utlis.*
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.databinding.FragmentPhotoViewBinding

class PhotoViewFragment : DialogFragment() {
    private lateinit var binding: FragmentPhotoViewBinding

    private var selectedPhoto: String? = null

    private val args: PhotoViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_DialogWhenLarge_NoActionBar)
        dialog?.window?.navigationBarColor =
            ResourcesCompat.getColor(resources, R.color.black, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoViewBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.selectedPhoto?.let {
            selectedPhoto = it
        }

        binding.imageButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        /**
         * Case 1: [Glide] will load images from Picsum API - main case
         *
         * ### Exception on saving photo -
         * 1. If device has write permission then save it through bitmap otherwise ask for [hasStoragePermission]
         * 2. If Glide takes time to load or failed to load photo then the photo can not be save,
         * the photo is converting to bitmap which will through null object exception. To handle this,
         * [GlideListenerImpl] Object class is created to handle Glide event listener
         * 3. The photo offline caching [DiskCacheStrategy] enable, if the photo is not loaded and
         * device has not connected the the image download and share option is set [isVisible] false.
         */

        Glide.with(requireContext()).load(selectedPhoto).diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(GlideListenerImpl.OnCompleted {

                if (requireContext().isConnected()) {
                    binding.imageButtonSave.isVisible = true
                }
                binding.progressBar.isVisible = false
                showToast(getString(R.string.zoom_in_out))

            }).into(binding.zoomViewPhoto)

        binding.imageButtonSave.setOnClickListener {

            if (requireContext().hasStoragePermission()) {
                requireContext().saveToSdCard(binding.zoomViewPhoto.drawable.toBitmap())
                showToast(getString(R.string.photo_save))

            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }

        /**
         * Event: The action listen will trigger devices' default sharing option to share photo
         * The Task will be done using firebase's [FirebaseDynamicLinks]
         */

        binding.imageButtonShare.setOnClickListener {

            FirebaseDynamicLinks.getInstance()
                .createDynamicLink()
                .setLink(Uri.parse("https://interactivephotogallery.page.link/picsum/$selectedPhoto"))
                .setDomainUriPrefix("https://rashadtanjim.info")
                .setAndroidParameters(
                    AndroidParameters.Builder("info.rashadtanjim.interactivephotogallery")
                        .setMinimumVersion(1)
                        .build()
                )
                .setSocialMetaTagParameters(
                    SocialMetaTagParameters.Builder()
                        .setTitle("https://interactivephotogallery.page.link/picsum")
                        .setDescription(getString(R.string.about_app))
                        .setTitle(getString(R.string.app_name))
                        .build()
                )
                .buildShortDynamicLink()
                .addOnCompleteListener { task: Task<ShortDynamicLink> ->
                    if (task.isSuccessful) {
                        val shortLink = task.result.shortLink
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        intent.putExtra(Intent.EXTRA_STREAM, shortLink)
                        intent.type = "image/*"
                        val shareIntent = Intent.createChooser(intent, "Share Photo!")
                        startActivity(shareIntent, null)
                    } else {
                        showToast(getString(R.string.something_went_wrong))
                    }
                }

        }
    }

    companion object {
        // write permission request
        const val PERMISSION_REQUEST_CODE = 1001
    }
}