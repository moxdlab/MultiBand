package io.moxd.multiband.model.screen

import io.moxd.multiband.model.screen.UIElementSizes.screenHeight
import io.moxd.multiband.model.screen.UIElementSizes.studyItemGroups
import io.moxd.multiband.model.sensor.core.EventType
import io.moxd.multiband.model.sensor.core.JumpDirection
import io.moxd.multiband.model.sensor.core.SensorEvent
import kotlin.math.absoluteValue

class Screen {
    private var visibleVerticalScreenSpan = VerticalSpan(0F, screenHeight.toFloat())
    private val height
        get() = studyItemGroups.lastOrNull()?.verticalSpan?.endPixel ?: 0F

    private val visibleGroups: List<ItemGroup>
        get() = studyItemGroups.filter { isVerticalSpanInside(it.verticalSpan) }
    private val nextGroup: ItemGroup?
        get() {
            val nextIndex = if (visibleGroups.size == 1) studyItemGroups.indexOf(
                visibleGroups.firstOrNull() ?: return null
            ) + 1 else studyItemGroups.indexOf(visibleGroups.lastOrNull() ?: return null)
            return if (nextIndex < studyItemGroups.size) studyItemGroups[nextIndex] else null
        }
    private val previousGroup: ItemGroup?
        get() {
            val prevIndex = if (isPositionInside(
                    (visibleGroups.firstOrNull()?.verticalSpan?.startPixel ?: return null) + 10f
                )) studyItemGroups.indexOf(visibleGroups.firstOrNull() ?: 0) - 1 else
                    studyItemGroups.indexOf(visibleGroups.firstOrNull() ?: 0)
            return if (prevIndex >= 0) studyItemGroups[prevIndex] else studyItemGroups.firstOrNull()
        }

    override fun toString(): String {
        return "$visibleVerticalScreenSpan, \nitem groups: $studyItemGroups, \nvisible: $visibleGroups"
    }

    fun handleEvent(event: SensorEvent): Float {
        return when (event.type) {
            is EventType.NoEvent -> {
                0F
            }

            is EventType.Scroll -> {
                val delta = move((event.type as EventType.Scroll).delta)
                delta
            }

            is EventType.Swipe -> {
                0F
            }

            is EventType.Jump -> {
                when ((event.type as EventType.Jump).direction) {
                    JumpDirection.PREVIOUS ->
                        if (previousGroup != null) {
                            moveToPosition(previousGroup!!.verticalSpan.startPixel)
                        } else 0F

                    JumpDirection.NEXT ->
                        if (nextGroup != null) {
                            moveToPosition(nextGroup!!.verticalSpan.startPixel)
                        } else 0F

                    JumpDirection.NEUTRAL -> 0F
                }
            }
        }
    }

    private fun move(pixel: Float): Float {
        var validPixelsToScroll = pixel
        if (pixel < 0 && visibleVerticalScreenSpan.startPixel - pixel.absoluteValue < 0f)
            validPixelsToScroll = -visibleVerticalScreenSpan.startPixel
        if (pixel > 0 && visibleVerticalScreenSpan.endPixel + pixel > height)
            validPixelsToScroll = height - visibleVerticalScreenSpan.endPixel
        visibleVerticalScreenSpan = visibleVerticalScreenSpan.move(validPixelsToScroll)
        return validPixelsToScroll
    }

    fun moveToPosition(verticalPosition: Float): Float {
        val distance = verticalPosition - visibleVerticalScreenSpan.startPixel
        return move(distance)
    }

    private fun isPositionInside(position: Float) =
        position > visibleVerticalScreenSpan.startPixel &&
                position < visibleVerticalScreenSpan.endPixel

    private fun isVerticalSpanInside(verticalSpan: VerticalSpan) =
        isPositionInside(verticalSpan.startPixel) || isPositionInside(verticalSpan.endPixel) ||
                (verticalSpan.startPixel <= visibleVerticalScreenSpan.startPixel && verticalSpan.endPixel >= visibleVerticalScreenSpan.endPixel)
}