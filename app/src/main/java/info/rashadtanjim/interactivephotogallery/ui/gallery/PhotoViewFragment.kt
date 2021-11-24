package info.rashadtanjim.interactivephotogallery.ui.gallery

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink.*
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import dagger.hilt.android.scopes.FragmentScoped
import info.rashadtanjim.core.utlis.*
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.databinding.FragmentPhotoViewBinding
import java.io.ByteArrayOutputStream

@FragmentScoped
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
                    binding.imageButtonShare.isVisible = true
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
         * The Task is done using two ways: I am using normal sharing as Firebase domain TXT addition is time taking
         * 1. Photo normal sharing through [Intent]
         * 2. firebase's [FirebaseDynamicLinks]
         * Before sharing [hasStoragePermission] checks write permission
         */

        binding.imageButtonShare.setOnClickListener {

            val mBitmap = binding.zoomViewPhoto.drawable.toBitmap()

            if (requireContext().hasStoragePermission()) {

                val share = Intent(Intent.ACTION_SEND)
                share.type = "image/*"
                share.putExtra(Intent.EXTRA_STREAM, getImageUri(requireContext(), mBitmap))

                startActivity(Intent.createChooser(share, "Share via"))

            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri? {
        val byte = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byte)
        val path = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver, bitmap,
            "Picsum Image Description", null
        )
        return Uri.parse(path)
    }

    private fun shareDynamicLink(mBitmap: Bitmap) {
        val uri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requireContext().saveImageInQ(mBitmap)
        } else {
            val path = MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver, mBitmap,
                "Image Description", null
            )
            Uri.parse(path)
        }

        FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLink(Uri.parse("https://rashadtanjim.info/picsum/$selectedPhoto"))
            .setDomainUriPrefix("https://rashadtanjim.info")
            .setAndroidParameters(
                AndroidParameters.Builder("info.rashadtanjim.interactivephotogallery")
                    .setMinimumVersion(0)
                    .build()
            )
            .setIosParameters(
                IosParameters.Builder("info.rashadtanjim.interactivephotogallery")
                    .setAppStoreId("0000000000")
                    .setMinimumVersion("0.0.1")
                    .build()
            )
            .setGoogleAnalyticsParameters(
                GoogleAnalyticsParameters.Builder()
                    .setSource("picsum")
                    .setMedium("social")
                    .setCampaign("picsum-images")
                    .build()
            )
            .setSocialMetaTagParameters(
                SocialMetaTagParameters.Builder()
                    .setTitle("rashadtanjim.info")
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

                    intent.putExtra(Intent.EXTRA_TEXT, "&$shortLink")
                    intent.putExtra(Intent.EXTRA_STREAM, uri)

                    //                            intent.setDataAndType(uri, "image/*")
                    intent.type = "image/*"
                    val shareIntent = Intent.createChooser(intent, "Share Photo!")
                    startActivity(shareIntent, null)
                } else {
                    showToast(getString(R.string.something_went_wrong))
                }
            }
    }

    companion object {
        // write permission request
        const val PERMISSION_REQUEST_CODE = 1001
    }
}