package io.moxd.multiband.model.screen

open class ItemGroup(val height: Float, val width: Float) {

    var verticalSpan = VerticalSpan(0F, height)
        private set

    override fun toString(): String {
        return "$verticalSpan"
    }

    fun setVerticalStartPosition(startPosition: Float) {
        verticalSpan = VerticalSpan(startPosition, startPosition + height)
    }
}