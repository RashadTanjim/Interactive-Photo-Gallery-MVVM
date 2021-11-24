package info.rashadtanjim.interactivephotogallery.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.scopes.ViewModelScoped
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.data.util.DataState
import info.rashadtanjim.interactivephotogallery.domain.model.PicsumPhotoList
import info.rashadtanjim.interactivephotogallery.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@ViewModelScoped
class SharedViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _photoList: MutableLiveData<DataState<PicsumPhotoList>> = MutableLiveData()
    val photoList: LiveData<DataState<PicsumPhotoList>>
        get() = _photoList

    fun photoData() = viewModelScope.launch {
        _photoList.value = DataState.Loading
        _photoList.value = repository.getPicsumPhotos()
    }
}