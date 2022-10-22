package es.pedram.parcel_tracking.domain

import org.springframework.stereotype.Component

@Component
class DistanceCalculatorFactory(private val distanceCalculators: List<DistanceCalculator>) {
    fun create(distanceCalculationMethod: DistanceCalculationMethod): DistanceCalculator {
        return distanceCalculators.find { it.calculationMethod == distanceCalculationMethod }
            ?: error("Couldn't find calculator for $distanceCalculationMethod")
    }
}
