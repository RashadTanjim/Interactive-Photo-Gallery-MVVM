package info.rashadtanjim.interactivephotogallery.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.rashadtanjim.interactivephotogallery.data.repository.BaseRepository
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.ui.gallery.SharedViewModel

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SharedViewModel::class.java) -> SharedViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}