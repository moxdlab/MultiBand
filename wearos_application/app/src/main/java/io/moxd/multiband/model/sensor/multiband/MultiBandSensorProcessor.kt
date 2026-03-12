package io.moxd.multiband.model.sensor.multiband

import io.moxd.multiband.model.sensor.core.SensorProcessor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MultiBandSensorProcessor(private val eventIdentifier: MultiBandEventIdentifier) :
    SensorProcessor {

    override fun handleIncomingData(
        incomingRawSensorData: Flow<List<Int>>
    ): Flow<MultiBandEvent> {
        var prevEvent: MultiBandEvent = NoEvent()
        return incomingRawSensorData.map {
            val sensorData = it.toSensorData()
            val event = eventIdentifier.identifyNewEvent(sensorData, prevEvent)
            prevEvent = event
            event
        }.flowOn(Dispatchers.IO)
    }
}