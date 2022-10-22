package es.pedram.parcel_tracking.domain

interface DistanceCalculator {
    val calculationMethod: DistanceCalculationMethod
    fun calculateDistanceInMeters(source: Location, destination: Location): Double
}
