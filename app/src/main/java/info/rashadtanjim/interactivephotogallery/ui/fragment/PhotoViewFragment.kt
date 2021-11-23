package info.rashadtanjim.interactivephotogallery.ui.fragment

import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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

        binding.imageButtonShare.setOnClickListener {


        }
    }

    companion object {
        // write permission request
        const val PERMISSION_REQUEST_CODE = 1001
    }
}