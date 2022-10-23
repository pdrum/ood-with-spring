package es.pedram.parcel_tracking

import es.pedram.parcel_tracking.domain.FlightDistanceCalculator
import es.pedram.parcel_tracking.domain.Location
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FlightDistanceCalculatorTest {
    @Test
    fun `flight distances should be calculated correctly`() {
        val distance = FlightDistanceCalculator()
            .calculateDistanceInMeters(
                Location(52.49949733775253, 13.38189741270594),
                Location(52.521228385036814, 13.410743814697753),
            )
        Assertions.assertEquals(3110.0, distance, 10.0)
    }
}
