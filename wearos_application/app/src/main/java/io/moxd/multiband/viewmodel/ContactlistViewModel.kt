package io.moxd.multiband.viewmodel

import android.app.Application
import androidx.lifecycle.*
import io.moxd.multiband.DEBUG_SENSOR
import io.moxd.multiband.model.connectivity.UDPReceiver
import io.moxd.multiband.model.screen.Screen
import io.moxd.multiband.model.sensor.multiband.InputVariant
import io.moxd.multiband.model.sensor.multiband.getProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ContactlistViewModel(app: Application) : AndroidViewModel(app) {
    private val screen = Screen()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _debugData: MutableLiveData<List<Int>> = MutableLiveData(listOf(1))
    val debugData: LiveData<List<Int>> = _debugData

    val debugEnabled = DEBUG_SENSOR
    private val incomingData =
        if (debugEnabled) _debugData.asFlow() else UDPReceiver().receiveData()

    lateinit var incomingDelta: Flow<Float>

    init {
        _isLoading.postValue(true)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val inputVariant = InputVariant.MultiBand
            incomingDelta = inputVariant.getProcessor()
                .handleIncomingData(incomingData)
                .map {
                    screen.handleEvent(it)
                }
            _isLoading.postValue(false)
        }
    }

    fun changeDebugData() {
        when (_debugData.value?.last()) {
            0, 1 -> _debugData.postValue(listOf(2))
            2 -> _debugData.postValue(listOf(1))
        }
    }
}