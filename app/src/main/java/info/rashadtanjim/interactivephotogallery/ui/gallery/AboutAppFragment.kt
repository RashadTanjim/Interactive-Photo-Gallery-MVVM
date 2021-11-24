package info.rashadtanjim.interactivephotogallery.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.scopes.FragmentScoped
import info.rashadtanjim.interactivephotogallery.App
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.data.source.remote.PicsumApi
import info.rashadtanjim.interactivephotogallery.databinding.FragmentAboutAppBinding
import info.rashadtanjim.interactivephotogallery.ui.base.BaseFragment

@FragmentScoped
class AboutAppFragment :
    BaseFragment<SharedViewModel, FragmentAboutAppBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun getViewModel() = SharedViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAboutAppBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val api = remoteDataSource.buildApi(PicsumApi::class.java)
        val picsumDao = (requireActivity().applicationContext as App).database.PicsumDao()
        return UserRepository(api, picsumDao)
    }
}