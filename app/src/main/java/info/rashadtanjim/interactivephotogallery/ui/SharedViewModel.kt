package info.rashadtanjim.interactivephotogallery.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import info.rashadtanjim.interactivephotogallery.data.repository.UserRepository
import info.rashadtanjim.interactivephotogallery.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    repository: UserRepository
) : BaseViewModel(repository) {




}