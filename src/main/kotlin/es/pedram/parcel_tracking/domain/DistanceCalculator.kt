package es.pedram.parcel_tracking.domain

interface DistanceCalculator {
    fun calculateDistanceInMeters(source: Location, destination: Location): Double
}
