package info.rashadtanjim.interactivephotogallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import info.rashadtanjim.core.utlis.showToast
import info.rashadtanjim.core.utlis.snakbar
import info.rashadtanjim.interactivephotogallery.App
import info.rashadtanjim.interactivephotogallery.R
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

        viewModel.photoList.observe(viewLifecycleOwner, {

            when (it) {
                is DataState.Success -> {
                    //success update result
                    binding.progressBar.isVisible = false
                    showToast("Success!")
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