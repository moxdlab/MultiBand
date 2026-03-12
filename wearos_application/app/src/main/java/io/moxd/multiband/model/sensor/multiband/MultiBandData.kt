package io.moxd.multiband.model.sensor.multiband

import io.moxd.multiband.model.sensor.core.TrillSensorTouch
import io.moxd.multiband.model.sensor.core.PaceCalculationStrategy

data class MultiBandData(val trillSensorTouches: List<TrillSensorTouch>) {
    val numberOfTouches: Int = trillSensorTouches.size

    fun getLocationDifferencesToOtherData(otherMultiBandData: MultiBandData): List<Int> {
        val differences = mutableListOf<Int>()
        val maxIndex =
            if (this.numberOfTouches <= otherMultiBandData.numberOfTouches) this.numberOfTouches
            else otherMultiBandData.numberOfTouches

        trillSensorTouches.forEachIndexed { index, touchA ->
            if (maxIndex > index) {
                val touchB = otherMultiBandData.trillSensorTouches[index]
                differences.add(touchB.location - touchA.location)
            }
        }

        return differences
    }

    private fun toJsonString(): String {
        val jsonString = mutableListOf<String>().also { list ->
            trillSensorTouches.forEach {
                list.add("{\"location\": ${it.location}, \"size\": ${it.size}}")
            }
        }.reduceOrNull { prev, curr ->
            "$prev , $curr"
        }
        return if (jsonString != null) "[$jsonString]" else "[]"
    }

    override fun toString(): String {
        return this.toJsonString()
    }
}

fun List<Int>.toSensorData(): MultiBandData {
    val list = mutableListOf<TrillSensorTouch>()
    this.forEach {
        list.add(TrillSensorTouch(it, 0))
    }
    return MultiBandData(list)
}

fun MultiBandData.calculatePace(
    prevData: MultiBandData,
    paceCalculationStrategy: PaceCalculationStrategy
): Float =
    if (this.numberOfTouches == prevData.numberOfTouches) {
        paceCalculationStrategy.calculatePace(this, prevData)
    } else 0F
