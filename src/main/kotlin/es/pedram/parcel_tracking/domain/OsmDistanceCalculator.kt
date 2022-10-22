package es.pedram.parcel_tracking.domain

import org.springframework.stereotype.Component

interface OsmDistanceClient {
    fun calculateDistanceInMeters(source: Location, destination: Location): Double
}

@Component
class OsmDistanceCalculator(private val osmDistanceClient: OsmDistanceClient): DistanceCalculator {
    override val calculationMethod = DistanceCalculationMethod.OSM_DISTANCE

    override fun calculateDistanceInMeters(source: Location, destination: Location): Double {
        return osmDistanceClient.calculateDistanceInMeters(source, destination)
    }
}
