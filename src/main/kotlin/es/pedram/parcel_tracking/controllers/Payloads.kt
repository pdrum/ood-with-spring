package es.pedram.parcel_tracking.controllers

import es.pedram.parcel_tracking.domain.DistanceCalculationMethod

class ParcelCreationRequestPayload(
    val name: String,
    val location: LocationPayload,
    val destination: LocationPayload,
    val distanceCalculationMethod: DistanceCalculationMethod
)

class LocationPayload(val lat: Double, val lng: Double)

class ParcelOutputPayload(
    val id: Long,
    val name: String,
    val location: LocationPayload,
    val destination: LocationPayload,
    val distanceToDestination: Double,
)
