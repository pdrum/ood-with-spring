package es.pedram.parcel_tracking.domain

import org.springframework.stereotype.Component


@Component
class FlightDistanceCalculator: DistanceCalculator {
    override fun calculateDistanceInMeters(source: Location, destination: Location): Double {
        val R = 6371000

        val lat1: Double = source.lat
        val lng1: Double = source.lng
        val lat2: Double = destination.lat
        val lng2: Double = destination.lng
        val latDistance: Double = toRad(lat2 - lat1)
        val lonDistance: Double = toRad(lng2 - lng1)
        val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
            Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }

    private fun toRad(value: Double): Double {
        return value * Math.PI / 180
    }
}