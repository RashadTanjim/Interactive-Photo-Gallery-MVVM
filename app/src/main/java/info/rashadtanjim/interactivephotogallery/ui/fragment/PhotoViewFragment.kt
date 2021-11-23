package info.rashadtanjim.interactivephotogallery.ui.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import info.rashadtanjim.core.utlis.showToast
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

        binding.zoomViewPhoto.load(selectedPhoto).also {
            showToast(getString(R.string.zoom_in_out))
        }

        binding.imageButtonSave.setOnClickListener {

        }

        binding.imageButtonShare.setOnClickListener {

        }

        binding.imageButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}