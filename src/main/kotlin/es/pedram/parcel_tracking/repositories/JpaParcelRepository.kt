package es.pedram.parcel_tracking.repositories

import es.pedram.parcel_tracking.entities.ParcelEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaParcelRepository: JpaRepository<ParcelEntity, Long> {
    @Modifying
    @Query("update ParcelEntity p set p.locationLat=:locationLat, p.locationLng=:locationLng where p.id=:id")
    fun updateLocation(
        @Param("id") id: Long,
        @Param("locationLat") locationLat: Double,
        @Param("locationLng") locationLng: Double,
    )
}
