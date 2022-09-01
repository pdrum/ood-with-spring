package es.pedram.parcel_tracking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParcelTrackingApplication

fun main(args: Array<String>) {
	runApplication<ParcelTrackingApplication>(*args)
}
