package io.moxd.multiband.model.screen

data class VerticalSpan(val startPixel: Float, val endPixel: Float) {
    override fun toString(): String {
        return "Start: ${startPixel}px, End: ${endPixel}px"
    }

    fun move(pixel: Float): VerticalSpan = VerticalSpan(startPixel + pixel, endPixel + pixel)
}

fun List<VerticalSpan>.sortByStartPixel() = this.sortedBy { it.startPixel }