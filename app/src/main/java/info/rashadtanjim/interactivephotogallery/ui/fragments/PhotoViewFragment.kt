package info.rashadtanjim.interactivephotogallery.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.scopes.FragmentScoped
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.databinding.FragmentPhotoViewBinding
import info.rashadtanjim.interactivephotogallery.ui.SharedViewModel
import info.rashadtanjim.interactivephotogallery.ui.base.BaseFragment

@FragmentScoped
class PhotoViewFragment :
    BaseFragment<SharedViewModel, FragmentPhotoViewBinding, UserRepository>() {


    override fun getViewModel(): Class<SharedViewModel> {
        TODO("Not yet implemented")
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPhotoViewBinding {
        TODO("Not yet implemented")
    }

    override fun getFragmentRepository(): UserRepository {
        TODO("Not yet implemented")
    }
}