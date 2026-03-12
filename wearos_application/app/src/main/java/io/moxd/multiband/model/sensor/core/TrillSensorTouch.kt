package io.moxd.multiband.model.sensor.core


data class TrillSensorTouch(val location: Int, val size: Int) {
    override fun toString(): String {
        return "location:$location, size:$size"
    }
}