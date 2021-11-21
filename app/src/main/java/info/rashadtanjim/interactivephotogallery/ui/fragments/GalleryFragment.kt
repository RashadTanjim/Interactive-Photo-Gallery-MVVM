package info.rashadtanjim.interactivephotogallery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.scopes.FragmentScoped
import info.rashadtanjim.core.utlis.showToast
import info.rashadtanjim.core.utlis.snakbar
import info.rashadtanjim.interactivephotogallery.App
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.data.source.remote.PicsumApi
import info.rashadtanjim.interactivephotogallery.data.util.DataState
import info.rashadtanjim.interactivephotogallery.databinding.FragmentGalleryBinding
import info.rashadtanjim.interactivephotogallery.ui.SharedViewModel
import info.rashadtanjim.interactivephotogallery.ui.base.BaseFragment

class GalleryFragment :
    BaseFragment<SharedViewModel, FragmentGalleryBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.isVisible = false

        viewModel.photoList.observe(viewLifecycleOwner, {

            when (it) {
                is DataState.Success -> {
                    //success update result
                    showToast("Success!")
                    binding.progressBar.isVisible = false
                }
                is DataState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is DataState.Failure -> {
                    binding.progressBar.isVisible = false
                    binding.root.snakbar("Please Check your internet connection!")
                }
            }
        })

        viewModel.photoData()
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