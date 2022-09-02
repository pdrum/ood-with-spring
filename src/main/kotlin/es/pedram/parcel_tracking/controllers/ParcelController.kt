package es.pedram.parcel_tracking.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

class ParcelCreationInput(
    val name: String,
    val location: Location,
    val destination: Location,
    val distanceCalculationMethod: DistanceCalculationMethod
)

enum class DistanceCalculationMethod{ FLIGHT_DISTANCE, OSM_DISTANCE }

class Location(val lat: Double, val lng: Double)

class ParcelOutputPayload(
    val id: Long,
    val name: String,
    val location: Location,
    val destination: Location,
    val distanceToDestination: Double,
)

@RestController
class ParcelController {
    @PostMapping("/api/parcels")
    fun create(@RequestBody payload: ParcelCreationInput): ParcelOutputPayload {
        return ParcelOutputPayload(
            id = 1,
            name = payload.name,
            location = payload.location,
            destination = payload.destination,
            distanceToDestination = 1000.0
        )
    }

    @GetMapping("/api/parcels")
    fun getAllParcels(): List<ParcelOutputPayload> {
        return listOf(
            ParcelOutputPayload(
                id = 1,
                name = "monitor",
                location = Location(0.0, 0.0),
                destination = Location(1.0, 1.0),
                distanceToDestination = 1000.0
            ),
            ParcelOutputPayload(
                id = 2,
                name = "keyboard",
                location = Location(0.0, 0.0),
                destination = Location(1.0, 1.0),
                distanceToDestination = 1000.0
            )
        )
    }

    @GetMapping("/api/parcels/{id}")
    fun getSingleParcel(@PathVariable("id") id: Long): ParcelOutputPayload {
        return ParcelOutputPayload(
            id = id,
            name = "keyboard",
            location = Location(0.0, 0.0),
            destination = Location(1.0, 1.0),
            distanceToDestination = 1000.0
        )
    }

    @DeleteMapping("/api/parcels/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) {}

    @PatchMapping("/api/parcels/{id}")
    fun updateLocation(@RequestBody location: Location): ParcelOutputPayload {
        return ParcelOutputPayload(
            id = 2,
            name = "keyboard",
            location = location,
            destination = Location(1.0, 1.0),
            distanceToDestination = 1000.0
        )
    }
}
