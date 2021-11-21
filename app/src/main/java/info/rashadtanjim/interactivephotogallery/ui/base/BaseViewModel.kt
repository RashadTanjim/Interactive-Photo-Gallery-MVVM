package info.rashadtanjim.interactivephotogallery.ui.base

import androidx.lifecycle.ViewModel
import info.rashadtanjim.interactivephotogallery.data.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

}