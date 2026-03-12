package io.moxd.multiband.model.sensor.multiband

import io.moxd.multiband.model.sensor.core.EventIdentifier
import io.moxd.multiband.model.sensor.core.IndexFingerStrategy
import io.moxd.multiband.model.sensor.core.JumpDirection
import kotlin.math.absoluteValue


interface MultiBandEventIdentifier : EventIdentifier<MultiBandEvent> {
    override fun identifyNewEvent(
        newSensorData: Any,
        prevEvent: MultiBandEvent
    ): MultiBandEvent {
        if (newSensorData !is MultiBandData) {
            throw IllegalArgumentException("Expected TrillSensorData but got ${newSensorData.javaClass}")
        }
        return identifyNewEvent(newSensorData, prevEvent)
    }

    fun identifyNewEvent(
        newMultiBandData: MultiBandData,
        prevEvent: MultiBandEvent
    ): MultiBandEvent
}

//1-Touch -> Scroll, 2-Touch -> Jump
class EventIdentifierMultiBand : MultiBandEventIdentifier {
    private var curEventPaceSum = 0f
    override fun identifyNewEvent(
        newMultiBandData: MultiBandData,
        prevEvent: MultiBandEvent
    ): MultiBandEvent {
        val pace =
            newMultiBandData.calculatePace(prevEvent.multiBandData, IndexFingerStrategy())
        return when (newMultiBandData.numberOfTouches) {
            0 -> NoEvent()
            1 -> identifyScroll(newMultiBandData, pace)

            2 -> {
                if (prevEvent is MultiBandJump && prevEvent.direction == JumpDirection.NEUTRAL)
                    curEventPaceSum += pace
                else
                    curEventPaceSum = pace
                identifyJump(newMultiBandData, curEventPaceSum)
            }

            else -> NoEvent()
        }
    }
}

private fun identifyScroll(newMultiBandData: MultiBandData, pace: Float): MultiBandScroll {
    val scrollThreshold = 15F
    return if (pace.absoluteValue < scrollThreshold) MultiBandScroll(
        newMultiBandData,
        0f
    ) else MultiBandScroll(
        newMultiBandData,
        pace
    )
}

private fun identifyJump(newMultiBandData: MultiBandData, pace: Float): MultiBandJump {
    val jumpThreshold = 80F
    return if (pace.absoluteValue < jumpThreshold) MultiBandJump(
        newMultiBandData,
        JumpDirection.NEUTRAL
    ) else if (pace > 0F) MultiBandJump(
        newMultiBandData,
        JumpDirection.NEXT
    ) else MultiBandJump(
        newMultiBandData,
        JumpDirection.PREVIOUS
    )
}