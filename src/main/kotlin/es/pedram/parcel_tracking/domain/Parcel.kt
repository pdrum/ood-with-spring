package es.pedram.parcel_tracking.domain

data class Parcel(
    val id: Long?,
    val name: String,
    val location: Location,
    val destination: Location,
    val distanceCalculator: DistanceCalculator,
) {
    val distanceToDestination: Double = distanceCalculator.calculateDistanceInMeters(location, destination)
}
