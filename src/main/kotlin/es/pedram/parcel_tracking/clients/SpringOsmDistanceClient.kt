package es.pedram.parcel_tracking.clients

import es.pedram.parcel_tracking.domain.Location
import es.pedram.parcel_tracking.domain.OsmDistanceClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

data class GraphhopperResponse(val paths: List<Path>)
data class Path(val distance: Double)

@Component
class SpringOsmDistanceClient(@Value("osmBaseUrl") private val osmBaseUrl: String): OsmDistanceClient {
    override fun calculateDistanceInMeters(source: Location, destination: Location): Double {
        val webClient = WebClient.create(osmBaseUrl)
        val response = webClient.get()
            .uri("/route?point=${source.lat},${source.lng}&point=${destination.lat},${destination.lng}&type=json&locale=en-US&key=&elevation=false&profile=car")
            .retrieve()
            .bodyToMono(GraphhopperResponse::class.java)
            .block()
            ?: error("Couldn't get a response")
        return response.paths.first().distance
    }
}
