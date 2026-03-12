package io.moxd.multiband.model.sensor.core

import kotlinx.coroutines.flow.Flow

interface SensorProcessor {
    fun handleIncomingData(incomingRawSensorData: Flow<List<Int>>): Flow<SensorEvent>
}