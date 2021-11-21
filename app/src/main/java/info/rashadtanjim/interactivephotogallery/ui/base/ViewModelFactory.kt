package info.rashadtanjim.interactivephotogallery.ui.base

import androidx.lifecycle.ViewModelProvider
import info.rashadtanjim.interactivephotogallery.data.repository.BaseRepository

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {



}