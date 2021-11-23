package info.rashadtanjim.interactivephotogallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import info.rashadtanjim.core.utlis.snakbar
import info.rashadtanjim.interactivephotogallery.App
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.data.source.remote.PicsumApi
import info.rashadtanjim.interactivephotogallery.data.util.DataState
import info.rashadtanjim.interactivephotogallery.databinding.FragmentGalleryBinding
import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotosItem
import info.rashadtanjim.interactivephotogallery.ui.SharedViewModel
import info.rashadtanjim.interactivephotogallery.ui.adapter.GalleryAdapter
import info.rashadtanjim.interactivephotogallery.ui.base.BaseFragment

class GalleryFragment :
    BaseFragment<SharedViewModel, FragmentGalleryBinding, UserRepository>() {

    private lateinit var galleryAdapter: GalleryAdapter

    private var photoListItem: List<PicsumPhotosItem>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryAdapter = GalleryAdapter {
            val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoViewFragment()

            action.selectedPhoto = it.download_url
            findNavController().navigate(action)
        }

        binding.recycleViewPhotoList.adapter = galleryAdapter
        binding.recycleViewPhotoList.setHasFixedSize(true)

        viewModel.photoList.observe(viewLifecycleOwner, {

            when (it) {
                is DataState.Success -> {
                    updateUI(it.value)  //success update result

                    photoListItem = it.value
                    binding.progressBar.isVisible = false
                }
                is DataState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is DataState.Failure -> {
                    binding.progressBar.isVisible = false
                    binding.root.snakbar(getString(R.string.no_internet_connection))
                }
            }
        })

        viewModel.photoData()

        binding.imageButtonSettings.setOnClickListener {
            findNavController().navigate(R.id.action_galleryFragment_to_settingsFragment)
        }
    }

    private fun updateUI(photoListItem: List<PicsumPhotosItem>?) {
        galleryAdapter.submitList(photoListItem?.toMutableList())
    }

    override fun getViewModel() = SharedViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGalleryBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val api = remoteDataSource.buildApi(PicsumApi::class.java)
        val picsumDao = (requireActivity().applicationContext as App).database.PicsumDao()
        return UserRepository(api, picsumDao)
    }
}