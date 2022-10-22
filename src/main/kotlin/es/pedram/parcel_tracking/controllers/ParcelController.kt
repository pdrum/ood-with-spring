package es.pedram.parcel_tracking.controllers

import es.pedram.parcel_tracking.domain.FlightDistanceCalculator
import es.pedram.parcel_tracking.domain.Location
import es.pedram.parcel_tracking.domain.Parcel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

interface ParcelRepository {
    fun create(parcel: Parcel): Parcel
    fun findAll(): List<Parcel>
    fun findById(id: Long): Parcel?
    fun updateLocation(id: Long, location: Location)
    fun delete(id: Long)
}

@RestController
class ParcelController(
    private val repository: ParcelRepository,
    private val distanceCalculator: FlightDistanceCalculator,
) {
    @PostMapping("/api/parcels")
    fun create(@RequestBody payload: ParcelCreationRequestPayload): ParcelOutputPayload {
        val createdParcel = repository.create(
            Parcel(
                id = null,
                name = payload.name,
                location = Location(payload.location.lat, payload.location.lng),
                destination = Location(payload.destination.lat, payload.destination.lng),
                distanceCalculator = distanceCalculator,
            )
        )
        return mapToPayload(createdParcel)
    }

    @GetMapping("/api/parcels")
    fun getAllParcels(): List<ParcelOutputPayload> {
        return repository.findAll().map { mapToPayload(it) }
    }

    @GetMapping("/api/parcels/{id}")
    fun getSingleParcel(@PathVariable("id") id: Long): ResponseEntity<ParcelOutputPayload> {
        val foundParcel = repository.findById(id) ?: return ResponseEntity.notFound().build()
        val outputPayload = mapToPayload(foundParcel)
        return ResponseEntity.ok(outputPayload)
    }

    @DeleteMapping("/api/parcels/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: Long) {
        repository.delete(id)
    }

    @PatchMapping("/api/parcels/{id}")
    fun updateLocation(@PathVariable("id") id: Long, @RequestBody locationPayload: LocationPayload) {
        repository.updateLocation(id, Location(locationPayload.lat, locationPayload.lng))
    }

    private fun mapToPayload(parcel: Parcel) = ParcelOutputPayload(
        id = parcel.id!!,
        name = parcel.name,
        location = LocationPayload(parcel.location.lat, parcel.location.lng),
        destination = LocationPayload(parcel.location.lat, parcel.location.lng),
        distanceToDestination = parcel.distanceToDestination
    )
}
