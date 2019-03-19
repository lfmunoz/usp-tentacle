package com.attendcall.genesis.rest


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.*
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.*
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.io.File
import java.time.Instant
import java.util.*
import java.text.SimpleDateFormat
import java.text.DateFormat
import java.util.TimeZone
import java.time.ZoneId
import java.util.Locale
import java.time.format.FormatStyle
import java.time.format.DateTimeFormatter






/*

curl -X GET --header 'Accept: application/json' --header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWa0V3MlVDZ0FBc0s4cEowQ01nY1JhbFc0TmlqZmw4MXBhNnRCYzlabWQwIn0.eyJqdGkiOiI1M2MzM2JkOS05NTliLTRkNGMtYTVmOS0wNzkwOWVjMmM3MzQiLCJleHAiOjE1NTI2MDA4MDYsIm5iZiI6MCwiaWF0IjoxNTUyNTk5OTA2LCJpc3MiOiJodHRwOi8vb2MxMTItMjIubWFhcy5hdXNsYWIuMndpcmUuY29tOjkwODAvYXV0aC9yZWFsbXMvRUNPIiwiYXVkIjoidXNwLWNvbnRyb2wiLCJzdWIiOiJmYzZjMWI0Ny0zY2Q0LTQxOTgtODRmNS02YzU4ZWI4YTU4MzciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ1c3AtY29udHJvbCIsIm5vbmNlIjoiMjkxZjQ2MDctMmE2NS00MzkxLThlZTktYWMyNTVlYTgyMTZhIiwiYXV0aF90aW1lIjoxNTUyNTk5OTA1LCJzZXNzaW9uX3N0YXRlIjoiNGM4YWI3Y2ItNTFhZS00ZTQ0LTg4MmItYmNiOTQwNmYxZTlmIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX0FDVFVBVE9SIiwiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJ2aWV3LXJlYWxtIiwidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJtYW5hZ2UtaWRlbnRpdHktcHJvdmlkZXJzIiwiaW1wZXJzb25hdGlvbiIsInJlYWxtLWFkbWluIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJuYW1lIjoiQWRtaW4gQWRtaW5pc3RyYXRvciIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIiwiZ2l2ZW5fbmFtZSI6IkFkbWluIiwiZmFtaWx5X25hbWUiOiJBZG1pbmlzdHJhdG9yIiwiZW1haWwiOiJhZG1pbkBsb2NhbGhvc3QifQ.Xao0v90cXyNt0TmNn05WavLKvnQaajry520a8uRVXzy1JRY84GG_uGZXuplQDrkKwoAcIjKzRQxksshzgB9qqMOx9G9x7ZgbgaZUuP5bIM8h07MpAbh6-ZWZ43SXsKefa7kn9o5SXBmYAiMrY6pOmOeJK9gcMHf0alM2XBtw1mUfSviF6GUdQfjSPhP7bWpF9g2pTRlMQWbo0kLH-0DeAd2QofMD22QR7luG9hO7DtRRZg0M-A4jajQV4e1g3dCLvrKWNUNYgqr9yUsIb0aAXK-djNBYCadmIHeY0__v49lTEf8yy-O--2GAIKo4jL_v5_hwazCXXg30ILeVXUFP5g' 'http://oc112-22.maas.auslab.2wire.com:8080/api/workflows'

GET /api/workflows HTTP/1.1
Host: localhost:1999
User-Agent: curl/7.52.1
Accept: application/json
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJWa0V3MlVDZ0FBc0s4cEowQ01nY1JhbFc0TmlqZmw4MXBhNnRCYzlabWQwIn0.eyJqdGkiOiI1M2MzM2JkOS05NTliLTRkNGMtYTVmOS0wNzkwOWVjMmM3MzQiLCJleHAiOjE1NTI2MDA4MDYsIm5iZiI6MCwiaWF0IjoxNTUyNTk5OTA2LCJpc3MiOiJodHRwOi8vb2MxMTItMjIubWFhcy5hdXNsYWIuMndpcmUuY29tOjkwODAvYXV0aC9yZWFsbXMvRUNPIiwiYXVkIjoidXNwLWNvbnRyb2wiLCJzdWIiOiJmYzZjMWI0Ny0zY2Q0LTQxOTgtODRmNS02YzU4ZWI4YTU4MzciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ1c3AtY29udHJvbCIsIm5vbmNlIjoiMjkxZjQ2MDctMmE2NS00MzkxLThlZTktYWMyNTVlYTgyMTZhIiwiYXV0aF90aW1lIjoxNTUyNTk5OTA1LCJzZXNzaW9uX3N0YXRlIjoiNGM4YWI3Y2ItNTFhZS00ZTQ0LTg4MmItYmNiOTQwNmYxZTlmIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX0FDVFVBVE9SIiwiUk9MRV9VU0VSIiwiUk9MRV9BRE1JTiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJ2aWV3LXJlYWxtIiwidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJtYW5hZ2UtaWRlbnRpdHktcHJvdmlkZXJzIiwiaW1wZXJzb25hdGlvbiIsInJlYWxtLWFkbWluIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJuYW1lIjoiQWRtaW4gQWRtaW5pc3RyYXRvciIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIiwiZ2l2ZW5fbmFtZSI6IkFkbWluIiwiZmFtaWx5X25hbWUiOiJBZG1pbmlzdHJhdG9yIiwiZW1haWwiOiJhZG1pbkBsb2NhbGhvc3QifQ.Xao0v90cXyNt0TmNn05WavLKvnQaajry520a8uRVXzy1JRY84GG_uGZXuplQDrkKwoAcIjKzRQxksshzgB9qqMOx9G9x7ZgbgaZUuP5bIM8h07MpAbh6-ZWZ43SXsKefa7kn9o5SXBmYAiMrY6pOmOeJK9gcMHf0alM2XBtw1mUfSviF6GUdQfjSPhP7bWpF9g2pTRlMQWbo0kLH-0DeAd2QofMD22QR7luG9hO7DtRRZg0M-A4jajQV4e1g3dCLvrKWNUNYgqr9yUsIb0aAXK-djNBYCadmIHeY0__v49lTEf8yy-O--2GAIKo4jL_v5_hwazCXXg30ILeVXUFP5g

    GET /api/workflows HTTP/1.1
Accept: application/json
Content-Type: application/x-www-form-urlencoded;charset=UTF-8
User-Agent: Java/1.8.0_131
Host: localhost:1999
Connection: keep-alive

 */


/**
 * Unit Test - LoadWorkflows
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ExtendWith(SpringExtension::class)
class LoadWorkflows {

    //================================================================================
    // Dependencies
    //================================================================================


    //================================================================================
    // Test Data
    //================================================================================
   // val USP_HOST = "http://oc112-22.maas.auslab.2wire.com:8080"
  //  val USP_HOST = "http://localhost:1999"
  //  val KEYCLOAK_HOST = "http://oc112-22.maas.auslab.2wire.com:9080/"


    val KEYCLOAK_USERNAME= "admin"
    val KEYCLOAK_PASSWORD= "admin"
    val CLIENT_SECRET = "414fcf10-6fa7-45c9-895f-e94314f11676"

    val USP_HOST = "http://localhost:8080"
    val KEYCLOAK_HOST = "http://localhost:9080/"

    lateinit var restTemplate: RestTemplate
    lateinit var token: String

    @BeforeAll
    internal fun beforeAll() {
        restTemplate = RestTemplate()
        token = getKeyCloakToken()
    }

    //================================================================================
    // Setup
    //================================================================================
    @BeforeEach
    fun `before each test`() {
        assertThat(token.length).isGreaterThan(100)
    }

    @AfterEach
    fun `after each test`() {
    }

    //================================================================================
    // Test Cases
    //================================================================================
    @Test
    fun `get list of workflows`() {
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.APPLICATION_JSON)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.set("Authorization", "Bearer ${token}")
        val request = HttpEntity<String>(reqHeaders)
        val response = restTemplate.exchange("$USP_HOST/api/workflows", HttpMethod.GET,
                request, String::class.java)

        val result = JSONArray(response.body)

        println("------------- Workflows (${result.length()}) -------------")
        for (i in 0 until result.length()) {
            println(result.getJSONObject(i).getString("name"))
        }
    }



    @Test
    fun `import workflows`() {

        val stream = LoadWorkflows::class.java.getResourceAsStream("/workflows")
        val files = stream.bufferedReader().use { it.readLines() }

        /*
        files.forEach {
            println(it)
        }
        */
        val name = files.first()
        val key = name.replace(" ", "").replace("-", "").replace(".js", "")

        val fileName= "/workflows/$name"
        println(fileName)
        val script = LoadWorkflows::class.java.getResource(fileName).readText()

        // = File().readText(Charsets.UTF_8)
       // println(name)
       // println(key)
       // println(script)


        val tz = TimeZone.getTimeZone("UTC")
        //val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        df.timeZone = tz
        val nowAsISO = df.format(Date())


        val workflowDefinition = WorkflowDefinition("string", "string", "script", nowAsISO, nowAsISO, null)
        println(workflowDefinition)


        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")
        val request = HttpEntity<WorkflowDefinition>(workflowDefinition, reqHeaders)
        //val response = restTemplate.exchange("$USP_HOST/api/workflow-definitions", HttpMethod.POST,
        //        request, String::class.java)
        val response = restTemplate.exchange("$USP_HOST/api/workflow-definitions",  HttpMethod.POST,request, String::class.java)

        val result = JSONArray(response)


    }

    //
    fun getKeyCloakToken() : String {
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.APPLICATION_JSON)

        val dataMap = LinkedMultiValueMap<String, String>()
        dataMap.add("client_id", "usp-control")
        dataMap.add("client_secret", CLIENT_SECRET)
        dataMap.add("username", KEYCLOAK_USERNAME)
        dataMap.add("password", KEYCLOAK_PASSWORD)
        dataMap.add("grant_type", "password")

        val request = HttpEntity(dataMap, reqHeaders)

        val response = restTemplate.exchange("${KEYCLOAK_HOST}/auth/realms/ECO/protocol/openid-connect/token",
                HttpMethod.POST, request, HashMap<String, String>()::class.java).body!!

        println(response["access_token"])
        return response["access_token"]!!
    }

} // end of class


data class WorkflowDefinition(
        var key: String?,
        var name: String?,
        var script: String?,
        var createdDate: String? = null,
        var lastModifiedDate: String? = null,
        var id: Long? = null,
        var createdBy: String? = "system",
        var lastModifiedBy: String = "system",
        var type: String? = "JS_V1",
        var systemWorkflow: Boolean = false,
        var description: String? = null,
        var eventTrigger: String? = "NONE"
) {}


