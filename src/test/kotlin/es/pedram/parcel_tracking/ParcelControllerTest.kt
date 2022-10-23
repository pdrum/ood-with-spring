package es.pedram.parcel_tracking

import es.pedram.parcel_tracking.controllers.ParcelOutputPayload
import es.pedram.parcel_tracking.repositories.JpaParcelRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParcelControllerTest {
    @Autowired
    private lateinit var repository: JpaParcelRepository
    @LocalServerPort
    private var port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate
    private val baseUrl
        get() = "http://localhost:$port"
    private val parcelsUrl
        get() = "$baseUrl/api/parcels"
    private fun singleParcelUrl(id: Long) = "$parcelsUrl/$id"


    private val creationRequestBody = """
        {
          "name": "keyboard",
          "location": {
            "lat": 1.0,
            "lng": 2.0
          },
          "destination": {
            "lat": 1.001,
            "lng": 2.001
          },
          "distanceCalculationMethod": "TEST"
        }
    """.trimIndent()

    private val updateLocationBody = """
        {
            "lat": 1.004,
            "lng": 2.004
        }
    """.trimIndent()

    @BeforeEach
    fun beforeEach() {
        repository.deleteAll()
    }

    @Test
    fun `The controller methods should work properly`() {
        val creationResponse = createParcel()
        assertEquals(201, creationResponse.statusCode.value())
        val updateLocationResponse = updateLocation(creationResponse.body!!.id)
        assertEquals(200, updateLocationResponse.statusCode.value())
        assertEquals(200, getAll().statusCode.value())
        val getParcelResponse = getParcel(creationResponse.body!!.id)
        assertEquals(200, getParcelResponse.statusCode.value())
        assertEquals(1.004, getParcelResponse.body!!.location.lat)
        assertEquals(2.004, getParcelResponse.body!!.location.lng)
        assertEquals(204, deleteParcel(creationResponse.body!!.id).statusCode.value())
        assertEquals(404, getParcel(creationResponse.body!!.id).statusCode.value())
    }

    private fun deleteParcel(id: Long): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity = HttpEntity<Any>(headers)
        return restTemplate.exchange(
            singleParcelUrl(id),
            HttpMethod.DELETE,
            requestEntity,
            Any::class.java
        )
    }

    private fun getParcel(id: Long): ResponseEntity<ParcelOutputPayload> {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity = HttpEntity<Any>(headers)
        return restTemplate.exchange(
            singleParcelUrl(id),
            HttpMethod.GET,
            requestEntity,
            ParcelOutputPayload::class.java
        )
    }

    private fun getAll(): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity = HttpEntity<Any>(headers)
        return restTemplate.exchange(
            parcelsUrl,
            HttpMethod.GET,
            requestEntity,
            String::class.java
        )
    }

    private fun updateLocation(parcelId: Long): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity = HttpEntity(updateLocationBody, headers)
        return restTemplate.exchange(
            singleParcelUrl(parcelId),
            HttpMethod.PATCH,
            requestEntity,
            Any::class.java
        )
    }

    private fun createParcel(): ResponseEntity<ParcelOutputPayload> {
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        val requestEntity = HttpEntity(creationRequestBody, headers)
        return restTemplate.exchange(
            parcelsUrl,
            HttpMethod.POST,
            requestEntity,
            ParcelOutputPayload::class.java
        )
    }
}
