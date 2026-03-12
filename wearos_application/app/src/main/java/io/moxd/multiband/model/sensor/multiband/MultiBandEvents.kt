package io.moxd.multiband.model.sensor.multiband

import io.moxd.multiband.model.sensor.core.EventType
import io.moxd.multiband.model.sensor.core.JumpDirection
import io.moxd.multiband.model.sensor.core.SensorEvent

sealed class MultiBandEvent(val multiBandData: MultiBandData) : SensorEvent() {
    override val type: EventType = EventType.Scroll(0F) // Default für MultiBandEvents
}

class MultiBandScroll(multiBandData: MultiBandData, val pace: Float) : MultiBandEvent(multiBandData) {
    override val type: EventType = EventType.Scroll(pace)
    override fun toString(): String {
        return "TrillScroll, Fingers: ${multiBandData.numberOfTouches}, Pace: $pace"
    }
}

class MultiBandJump(multiBandData: MultiBandData, val direction: JumpDirection) : MultiBandEvent(multiBandData) {
    override val type: EventType = EventType.Jump(direction)
    override fun toString(): String {
        return "TrillJump, Fingers: ${multiBandData.numberOfTouches}, Direction: $direction"
    }
}

class MultiBandSwipe(multiBandData: MultiBandData, val pace: Float) : MultiBandEvent(multiBandData) {
    override val type: EventType = EventType.Swipe
    override fun toString(): String {
        return "TrillSwipe, Fingers: ${multiBandData.numberOfTouches}, Pace: $pace"
    }
}

class NoEvent : MultiBandEvent(MultiBandData(emptyList())) {
    override val type: EventType = EventType.NoEvent
    override fun toString(): String {
        return "No Trill Event!"
    }
}