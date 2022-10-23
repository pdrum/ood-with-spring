package es.pedram.parcel_tracking

import es.pedram.parcel_tracking.clients.SpringOsmDistanceClient
import es.pedram.parcel_tracking.domain.Location
import es.pedram.parcel_tracking.domain.OsmDistanceCalculator
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException


class OsmDistanceCalculatorTest {
    private val mockedServer: MockWebServer = MockWebServer()
    private val sampleResponse = """
        {
          "hints": {
            "visited_nodes.sum": 144,
            "visited_nodes.average": 144.0
          },
          "info": {
            "copyrights": [
              "GraphHopper",
              "OpenStreetMap contributors"
            ],
            "took": 7
          },
          "paths": [
            {
              "distance": 5257.577,
              "weight": 494.308438,
              "time": 494310,
              "transfers": 0,
              "points_encoded": true,
              "bbox": [
                13.376705,
                52.506002,
                13.417166,
                52.534368
              ],
              "points": "oan_I{btpAaBw@Um@sKtNUZc@XUJsAFcCTaM_BeCEwHq@?c@UoHyAoMI}Bs@sWsFl@a@wQ[wJ_@cMAQO_AU_Ju@uVKkAOcA_@yAKq@w@qC}BaI_BuEiG}NeKgWeDmGsA{BaBqBmGmI_@c@_AsAoBcCu@y@yGsFSf@m@z@iFnGMXgA~DcD~KMO_@UaB]iBi@k@UqA[kAImDb@EqA",
              "instructions": [
                {
                  "distance": 78.027,
                  "heading": 18.71,
                  "sign": 0,
                  "interval": [
                    0,
                    2
                  ],
                  "text": "Continue onto Dessauer Straße",
                  "time": 10638,
                  "street_name": "Dessauer Straße"
                },
                {
                  "distance": 281.832,
                  "sign": -2,
                  "interval": [
                    2,
                    3
                  ],
                  "text": "Turn left onto Stresemannstraße",
                  "time": 22547,
                  "street_name": "Stresemannstraße"
                },
                {
                  "distance": 170.071,
                  "sign": 0,
                  "interval": [
                    3,
                    8
                  ],
                  "text": "Continue onto Potsdamer Platz",
                  "time": 13605,
                  "street_name": "Potsdamer Platz"
                },
                {
                  "distance": 501.575,
                  "sign": 1,
                  "interval": [
                    8,
                    11
                  ],
                  "text": "Turn slight right onto Ebertstraße",
                  "time": 40126,
                  "street_name": "Ebertstraße"
                },
                {
                  "ref": "B 5",
                  "distance": 591.696,
                  "sign": 2,
                  "interval": [
                    11,
                    16
                  ],
                  "text": "Turn right onto Behrenstraße",
                  "time": 85205,
                  "street_name": "Behrenstraße"
                },
                {
                  "ref": "B 5",
                  "distance": 136.664,
                  "sign": -2,
                  "interval": [
                    16,
                    17
                  ],
                  "text": "Turn left onto Glinkastraße",
                  "time": 10933,
                  "street_name": "Glinkastraße"
                },
                {
                  "ref": "B 2, B 5",
                  "distance": 2648.685,
                  "sign": 2,
                  "interval": [
                    17,
                    42
                  ],
                  "text": "Turn right onto Unter den Linden",
                  "time": 211895,
                  "street_name": "Unter den Linden"
                },
                {
                  "distance": 465.036,
                  "sign": -2,
                  "interval": [
                    42,
                    48
                  ],
                  "text": "Turn left onto Saarbrücker Straße",
                  "time": 65839,
                  "street_name": "Saarbrücker Straße"
                },
                {
                  "distance": 355.967,
                  "sign": 2,
                  "interval": [
                    48,
                    56
                  ],
                  "text": "Turn right onto Schönhauser Allee",
                  "time": 28478,
                  "street_name": "Schönhauser Allee"
                },
                {
                  "distance": 28.024,
                  "sign": 2,
                  "interval": [
                    56,
                    57
                  ],
                  "text": "Turn right",
                  "time": 5044,
                  "street_name": ""
                },
                {
                  "distance": 0.0,
                  "sign": 4,
                  "last_heading": 81.7209703195093,
                  "interval": [
                    57,
                    57
                  ],
                  "text": "Arrive at destination",
                  "time": 0,
                  "street_name": ""
                }
              ],
              "legs": [],
              "details": {},
              "ascend": 0.0,
              "descend": 0.0,
              "snapped_waypoints": "oan_I{btpAgpDusE"
            }
          ]
        }
    """.trimIndent()

    @BeforeEach
    @Throws(IOException::class)
    fun setUp() {
        mockedServer.start()
    }

    @AfterEach
    @Throws(IOException::class)
    fun tearDown() {
        mockedServer.shutdown()
    }

    @Test
    fun `osm distances should be calculated correctly`() {
        val baseUrl = "http://localhost:${mockedServer.port}"
        mockedServer.enqueue(
            MockResponse()
                .setBody(sampleResponse)
                .setHeader("Content-Type", "application/json")
        )
        val distance = OsmDistanceCalculator(SpringOsmDistanceClient(baseUrl))
            .calculateDistanceInMeters(
                Location(52.49949733775253, 13.38189741270594),
                Location(52.521228385036814, 13.410743814697753),
            )
        Assertions.assertEquals(5257.577, distance, 0.01)
        val request = mockedServer.takeRequest()
        Assertions.assertEquals(
            "${baseUrl}/route?point=52.49949733775253,13.38189741270594&point=52.521228385036814,13.410743814697753&type=json&locale=en-US&key=&elevation=false&profile=car",
            request.requestUrl.toString(),
        )
    }
}
