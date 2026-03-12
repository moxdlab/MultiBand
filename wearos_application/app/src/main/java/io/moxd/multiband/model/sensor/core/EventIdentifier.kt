package io.moxd.multiband.model.sensor.core

interface EventIdentifier<T : SensorEvent> {
    fun identifyNewEvent(
        newSensorData: Any,
        prevEvent: T
    ): T
}