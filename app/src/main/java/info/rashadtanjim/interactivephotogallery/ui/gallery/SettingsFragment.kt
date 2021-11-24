package info.rashadtanjim.interactivephotogallery.ui.gallery

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.scopes.FragmentScoped
import info.rashadtanjim.interactivephotogallery.App
import info.rashadtanjim.interactivephotogallery.R
import info.rashadtanjim.interactivephotogallery.data.UserPreferences
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.data.source.remote.PicsumApi
import info.rashadtanjim.interactivephotogallery.databinding.FragmentSettingsBinding
import info.rashadtanjim.interactivephotogallery.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@FragmentScoped
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

        binding.dayNightMode.setOnClickListener {

            // Get new mode.
            val mode =
                if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                }

            // Change UI Mode
            AppCompatDelegate.setDefaultNightMode(mode)
        }
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