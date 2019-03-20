package eco.usp.automate

import com.attendcall.genesis.rest.LoadWorkflows
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.HashMap

class USPControllerUtils {

    companion object {
        val ENDPOINT_ID = "os::002456-testing0"
        val FILE_NAME = "/workflowTable.txt"

        val KEYCLOAK_USERNAME= "admin"
        val KEYCLOAK_PASSWORD= "admin"
        val CLIENT_SECRET = "414fcf10-6fa7-45c9-895f-e94314f11676"

        val USP_HOST = "http://localhost:8080"
        val KEYCLOAK_HOST = "http://localhost:9080/"

        val workflowTable: Map<String, String>

        init {
            workflowTable = LoadWorkflows::class.java.getResource(USPControllerUtils.FILE_NAME).readText().trim()
                    .split("\n").asSequence().map {
                        val list = it.split(",")
                    Pair(list[0], list[1])
                }.toMap()
        }

        fun getKeyCloakToken(restTemplate: RestTemplate): String {
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

        fun runWorkflow(restTemplate: RestTemplate, token: String, id: String, endpointId: String = ENDPOINT_ID) : Pair<Int, String?> {
            val reqHeaders = HttpHeaders()
            reqHeaders.accept = listOf(MediaType.ALL)
            reqHeaders.contentType = MediaType.APPLICATION_JSON
            reqHeaders.connection = listOf("close")
            reqHeaders.set("Authorization", "Bearer ${token}")

            val dataMap = LinkedMultiValueMap<String, String>()
            val request = HttpEntity(dataMap, reqHeaders)
            try {
                val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
                //println(response)
                return Pair(response.statusCodeValue, response.body)
            } catch (e : HttpStatusCodeException) {
                //println(e.getStatusCode().value())
                //println(e.responseBodyAsString)
                return Pair(e.statusCode.value(), e.responseBodyAsString)
            }
        }

        fun Add(restTemplate: RestTemplate, token: String, addObject: AddObject, endpointId: String = ENDPOINT_ID) : Pair<Int, String?> {
            val id = workflowTable["add"]!!
            val reqHeaders = HttpHeaders()
            reqHeaders.accept = listOf(MediaType.ALL)
            reqHeaders.contentType = MediaType.APPLICATION_JSON
            reqHeaders.connection = listOf("close")
            reqHeaders.set("Authorization", "Bearer ${token}")

            val request = HttpEntity(addObject, reqHeaders)
            try {
                val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
                //println(response)
                return Pair(response.statusCodeValue, response.body)
            } catch (e : HttpStatusCodeException) {
                //println(e.getStatusCode().value())
                //println(e.responseBodyAsString)
                return Pair(e.statusCode.value(), e.responseBodyAsString)
            }
        }

        fun Gpv(restTemplate: RestTemplate, token: String, parameters: GpvObject, endpointId: String = ENDPOINT_ID) : Pair<Int, String?> {
            val id = workflowTable["gpv"]!!
            val reqHeaders = HttpHeaders()
            reqHeaders.accept = listOf(MediaType.ALL)
            reqHeaders.contentType = MediaType.APPLICATION_JSON
            reqHeaders.connection = listOf("close")
            reqHeaders.set("Authorization", "Bearer ${token}")

            val request = HttpEntity(parameters, reqHeaders)
            try {
                val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
                //println(response)
                return Pair(response.statusCodeValue, response.body)
            } catch (e : HttpStatusCodeException) {
                //println(e.getStatusCode().value())
                //println(e.responseBodyAsString)
                return Pair(e.statusCode.value(), e.responseBodyAsString)
            }
        }

    }
}


// -------------------------------------------------------
//  Data classes
// -------------------------------------------------------
data class WorkflowDefinition(
        var key: String?,
        var name: String?,
        var script: String?,
        var createdDate: String? = null,
        var lastModifiedDate: String? = null,
        var id: Long? = null,
        var createdBy: String? = "kotlin",
        var lastModifiedBy: String = "kotlin",
        var type: String? = "JS_V1",
        var systemWorkflow: Boolean = false,
        var description: String? = null,
        var eventTrigger: String? = "NONE"
) {}

data class Parameter(
        var param: String,
        var value: String,
        var required: Boolean = false
) {}

data class AddObject(
        var basePath: String,
        var parameters: ArrayList<Parameter> = ArrayList<Parameter>()
) {
    fun add(parameter: Parameter) {
        parameters.add(parameter)
    }
}

data class GpvObject(
        var parameters: ArrayList<String> = ArrayList<String>()
) {
    fun add(parameter: String) {
        parameters.add(parameter)
    }
}

// -------------------------------------------------------
//  Global serialization (using Jackson)
// -------------------------------------------------------
val mapper= jacksonObjectMapper()

fun Any.toJson(pretty: Boolean = false): String = if (pretty) mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this) else mapper.writeValueAsString(this)

inline fun <reified T : Any> String.fromJson(): T = mapper.readValue(this, T::class.java)

inline fun <reified T : Any> String.fromJsonList(): List<T>  = mapper.readValue(this, mapper.typeFactory.constructCollectionType(List::class.java, T::class.java))