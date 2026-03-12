package io.moxd.multiband.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.moxd.multiband.model.persistence.studyContactGroups
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel(app: Application) : AndroidViewModel(app) {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            studyContactGroups
            _isLoading.emit(false)
        }
    }
}