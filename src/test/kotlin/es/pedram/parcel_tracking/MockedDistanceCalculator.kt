package es.pedram.parcel_tracking

import es.pedram.parcel_tracking.domain.DistanceCalculationMethod
import es.pedram.parcel_tracking.domain.DistanceCalculator
import es.pedram.parcel_tracking.domain.Location
import org.springframework.stereotype.Component

@Component
class MockedDistanceCalculator: DistanceCalculator {
    override val calculationMethod = DistanceCalculationMethod.TEST

    override fun calculateDistanceInMeters(source: Location, destination: Location): Double = 1000.0
}
