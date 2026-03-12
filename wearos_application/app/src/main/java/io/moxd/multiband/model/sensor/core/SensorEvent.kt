package io.moxd.multiband.model.sensor.core

enum class JumpDirection {
    PREVIOUS, NEXT, NEUTRAL
}

abstract class SensorEvent {
    abstract val type: EventType
}

sealed class EventType {
    object NoEvent : EventType()
    data class Scroll(val delta: Float) : EventType()
    data class Jump(val direction: JumpDirection) : EventType()
    object Swipe : EventType()
}