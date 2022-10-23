package es.pedram.parcel_tracking.repositories

import es.pedram.parcel_tracking.controllers.ParcelRepository
import es.pedram.parcel_tracking.domain.DistanceCalculatorFactory
import es.pedram.parcel_tracking.domain.Location
import es.pedram.parcel_tracking.domain.Parcel
import es.pedram.parcel_tracking.entities.ParcelEntity
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
class ParcelRepositoryImpl(
    private val distanceCalculatorFactory: DistanceCalculatorFactory,
    private val jpaRepository: JpaParcelRepository,
) : ParcelRepository {
    override fun create(parcel: Parcel): Parcel {
        val entity = parcel.toEntity()
        jpaRepository.save(entity)
        return parcel.copy(id = entity.id)
    }

    override fun findAll(): List<Parcel> = jpaRepository.findAll().map { it.toModel() }

    override fun findById(id: Long): Parcel? = jpaRepository.findById(id).map { it.toModel() }.orElse(null)

    @Transactional
    override fun updateLocation(id: Long, location: Location) {
        jpaRepository.updateLocation(id, location.lat, location.lng)
    }

    @Transactional
    override fun delete(id: Long) {
        val found = jpaRepository.findById(id).orElse(null)
        jpaRepository.delete(found)
    }

    private fun ParcelEntity.toModel() = Parcel(
        id = id!!,
        name = name!!,
        location = Location(locationLat!!, locationLng!!),
        destination = Location(destinationLat!!, destinationLng!!),
        distanceCalculator = distanceCalculatorFactory.create(distanceCalculationMethod!!)
    )

    private fun Parcel.toEntity() = ParcelEntity(
        id = id,
        name = name,
        locationLat = location.lat,
        locationLng = location.lng,
        destinationLat = destination.lat,
        destinationLng = destination.lng,
        distanceCalculationMethod = distanceCalculator.calculationMethod,
    )
}
