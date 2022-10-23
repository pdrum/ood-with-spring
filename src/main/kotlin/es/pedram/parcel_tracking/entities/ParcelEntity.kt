package es.pedram.parcel_tracking.entities

import es.pedram.parcel_tracking.domain.DistanceCalculationMethod
import es.pedram.parcel_tracking.domain.Location
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "parcels")
class ParcelEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    @Column(name = "location_lat")
    var locationLat: Double? = null,
    @Column(name = "location_lng")
    var locationLng: Double? = null,
    @Column(name = "destination_lat")
    var destinationLat: Double? = null,
    @Column(name = "destination_lng")
    var destinationLng: Double? = null,
    @Column(name = "distance_calculation_method")
    @Enumerated(EnumType.STRING)
    var distanceCalculationMethod: DistanceCalculationMethod? = null,
)
