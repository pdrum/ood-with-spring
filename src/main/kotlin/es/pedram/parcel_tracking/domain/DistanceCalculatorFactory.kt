package es.pedram.parcel_tracking.domain

import org.springframework.stereotype.Component

@Component
class DistanceCalculatorFactory(
    val flightDistanceCalculator: FlightDistanceCalculator,
    val osmDistanceCalculator: OsmDistanceCalculator,
) {
    fun create(distanceCalculationMethod: DistanceCalculationMethod): DistanceCalculator =
        when(distanceCalculationMethod) {
            DistanceCalculationMethod.FLIGHT_DISTANCE -> flightDistanceCalculator
            DistanceCalculationMethod.OSM_DISTANCE -> osmDistanceCalculator
        }
}
