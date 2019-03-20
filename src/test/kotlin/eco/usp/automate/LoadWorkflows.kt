package com.attendcall.genesis.rest


import eco.usp.automate.USPControllerUtils.Companion.getKeyCloakToken
import eco.usp.automate.WorkflowDefinition
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONArray
import org.junit.jupiter.api.*

import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.io.File
import java.lang.RuntimeException
import java.net.URLEncoder
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
        token = getKeyCloakToken(restTemplate)
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
    fun `import workflows`() {
        val stream = LoadWorkflows::class.java.getResourceAsStream("/workflows")
        val files = stream.bufferedReader().use { it.readLines() }
        files.forEach {
            val name = it
            val key = name.replace(" ", "").replace("-", "").replace(".js", "")
            val fileName= "/workflows/$name"
            val script = LoadWorkflows::class.java.getResource(fileName).readText()
            val nowAsISO = getCurrentTime()
            val workflowDefinition = WorkflowDefinition(key, name, script, nowAsISO, nowAsISO, null)
            //println(workflowDefinition)
            try {
                postWorkflowDefinition(workflowDefinition)
            } catch (e: Exception) {
                println("Unable to push workflow $name")
            }
        }
    }

    @Test
    fun `show workflows`() {
        val workflows = getListOfWorkflows()
        workflows.toSortedMap().forEach {
           //println("key = ${it.key}, id = ${it.value}")
           println("${it.key},${it.value}")
        }
    }

    @Test
    fun `run 10DeleteMessageAllowPartialFalse`() {
        val agent = "os::002456-testing0"
        val workflows = getListOfWorkflows()
        val id = workflows["10DeleteMessageAllowPartialFalse"] ?: throw RuntimeException("key not found")
        runWorkflow(id, agent)
    }


    @Test
    fun `run `() {
        val agent = "os::002456-testing0"
        val workflows = getListOfWorkflows()
        /*
        val id = workflows["10DeleteMessageAllowPartialFalse"]!!
        val id = workflows["11GetMessageParameterPathOnly"]!!
        val id = workflows["12GetMessageObjectPathsOnly"]!!
        val id = workflows["13GetMessageMixedPaths"]!!
        val id = workflows["14GetMessageSearchPaths"]!!
        val id = workflows["15GetMessageValidandInvalidPaths"]!!
        val id = workflows["21SubscriptionCreationUsingValueChange"]!!
        val id = workflows["22SubscriptionDisableUsingValueChange"]!!
        val id = workflows["23SubscriptionDeletionUsingValueChange"]!!
        val id = workflows["24SubscriptiononObjectCreation"]!!
        val id = workflows["25SubscriptiononObjectDeletion"]!!
        */
        val id = workflows["23SubscriptionDeletionUsingValueChange"]!!
        runWorkflow(id, agent)
    }


    ////////////////////////////////////////////////////////////////////////////////
    // Helper functions
    ////////////////////////////////////////////////////////////////////////////////
    private fun getCurrentTime() : String {
        val tz = TimeZone.getTimeZone("UTC")
        //val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        df.timeZone = tz
        return df.format(Date())
    }

    private fun postWorkflowDefinition(workflowDefinition: WorkflowDefinition) {
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")
        val request = HttpEntity<WorkflowDefinition>(workflowDefinition, reqHeaders)
        val response = restTemplate.exchange("$USP_HOST/api/workflow-definitions",  HttpMethod.POST,request, String::class.java)
        println(response)
    }

    private fun runWorkflow(id: String, endpointId: String) {
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")

        val dataMap = LinkedMultiValueMap<String, String>()
        val request = HttpEntity(dataMap, reqHeaders)
        try {
            val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
            println(response)
        } catch (e : HttpStatusCodeException) {
            println(e.getStatusCode().value())
            println(e.responseBodyAsString)
        }
    }



    fun getListOfWorkflows() : Map<String, String>{
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.APPLICATION_JSON)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.set("Authorization", "Bearer ${token}")
        val request = HttpEntity<String>(reqHeaders)
        val response = restTemplate.exchange("$USP_HOST/api/workflows?size=100", HttpMethod.GET,
                request, String::class.java)

        val result = JSONArray(response.body)

        val map = HashMap<String, String>()
        //println("------------- Workflows (${result.length()}) -------------")
        for (i in 0 until result.length()) {
            //println(result.getJSONObject(i).getString("name"))
            val key =  result.getJSONObject(i).getString("key")
            val id = result.getJSONObject(i).getString("id")
            map[key]  = id
        }
        return map
    }

} // end of class



