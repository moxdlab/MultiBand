package io.moxd.multiband.model.sensor.core

import io.moxd.multiband.model.sensor.multiband.MultiBandData
import kotlin.math.absoluteValue

interface PaceCalculationStrategy {
    fun calculatePace(newData: MultiBandData, oldData: MultiBandData): Float
}

class HighestDeltaStrategy : PaceCalculationStrategy {
    override fun calculatePace(newData: MultiBandData, oldData: MultiBandData): Float {
        val locationDifferencesToPreviousData =
            newData.getLocationDifferencesToOtherData(oldData)
        return if (locationDifferencesToPreviousData.isEmpty()) 0F
        else
            locationDifferencesToPreviousData.fold(initial = 0F) { highest, cur ->
                if (highest.absoluteValue > cur.absoluteValue)
                    highest
                else
                    cur.toFloat()
            }
    }
}

class IndexFingerStrategy : PaceCalculationStrategy {
    override fun calculatePace(newData: MultiBandData, oldData: MultiBandData): Float {
        val locationDifferencesToPreviousData =
            newData.getLocationDifferencesToOtherData(oldData).asReversed()
        return if (locationDifferencesToPreviousData.isEmpty()) 0F
        else locationDifferencesToPreviousData.first().toFloat()
    }
}