package info.rashadtanjim.interactivephotogallery.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import info.rashadtanjim.interactivephotogallery.App
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.data.source.remote.PicsumApi
import info.rashadtanjim.interactivephotogallery.databinding.FragmentSettingsBinding
import info.rashadtanjim.interactivephotogallery.ui.base.BaseFragment

class SettingsFragment :
    BaseFragment<SharedViewModel, FragmentSettingsBinding, UserRepository>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardViewAboutApp.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_aboutAppFragment)
        }

//        val isNightMode = runBlocking { userPreferences.nightMode.first() }
//
//        binding.dayNightMode.isChecked = isNightMode ?: false
//
//        binding.dayNightMode.setOnClickListener {
//            lifecycleScope.launch {
//                userPreferences.toggleNightMode(binding.dayNightMode.isChecked)
//            }
//        }
//
//        lifecycleScope.launchWhenCreated {
//            userPreferences.nightMode.collectLatest {
//                if (it == true) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                }
//            }
//        }

    }

    override fun getViewModel() = SharedViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val api = remoteDataSource.buildApi(PicsumApi::class.java)
        val picsumDao = (requireActivity().applicationContext as App).database.PicsumDao()
        return UserRepository(api, picsumDao)
    }
}