package es.pedram.parcel_tracking.repositories

import es.pedram.parcel_tracking.controllers.ParcelRepository
import es.pedram.parcel_tracking.domain.Location
import es.pedram.parcel_tracking.domain.Parcel
import org.springframework.stereotype.Repository
import java.util.concurrent.atomic.AtomicLong

@Repository
class InMemoryParcelRepository: ParcelRepository {
    private val data = mutableMapOf<Long, Parcel>()
    private val idCounter: AtomicLong = AtomicLong(0)

    override fun create(parcel: Parcel): Parcel {
        if (parcel.id != null) error("Parcel already has an id")
        val createdParcel = parcel.copy(id = idCounter.incrementAndGet())
        data[createdParcel.id!!] = createdParcel
        return createdParcel
    }

    override fun findAll(): List<Parcel> = data.values.toList()

    override fun findById(id: Long): Parcel? = data[id]

    override fun updateLocation(id: Long, location: Location) {
        val parcel = data[id] ?: return
        val updatedParcel = parcel.copy(location = location)
        data[id] = updatedParcel
    }

    override fun delete(id: Long) {
        data.remove(id)
    }
}
