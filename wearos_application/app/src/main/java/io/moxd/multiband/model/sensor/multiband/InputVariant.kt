package io.moxd.multiband.model.sensor.multiband

sealed class InputVariant() {
    abstract val name: String
    abstract val enableFling: Boolean

    override fun toString(): String {
        return name
    }

    companion object {
        fun fromString(string: String): InputVariant? =
            when (string) {
                MultiBand.name -> MultiBand
                else -> null
            }
    }

    object MultiBand : InputVariant() {
        override val name = "Band-VariantA"
        override val enableFling = false
    }
}

fun InputVariant.getProcessor() =
    when (this) {
        InputVariant.MultiBand -> MultiBandSensorProcessor(EventIdentifierMultiBand())
    }