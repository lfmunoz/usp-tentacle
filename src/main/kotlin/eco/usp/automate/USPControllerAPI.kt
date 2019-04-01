package eco.usp.automate

import org.json.JSONArray

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.text.SimpleDateFormat
import java.util.*

class USPControllerAPI {

    // Constants
    val FILE_NAME = "/workflowTable.txt"

    // Settings
    val ENDPOINT_ID = "os::002456-testing0"
    val KEYCLOAK_USERNAME = "admin"
    val KEYCLOAK_PASSWORD = "admin"
    val CLIENT_SECRET = "414fcf10-6fa7-45c9-895f-e94314f11676"
    val USP_HOST = "http://localhost:8080"
    val KEYCLOAK_HOST = "http://localhost:9080/"

    // Fields
    val workflowTable: Map<String, String>
    private val restTemplate: RestTemplate
    private val token: String

    // Constructor
    init {
        workflowTable = USPControllerAPI::class.java.getResource(FILE_NAME).readText().trim()
                .split("\n").asSequence().map {
                    val list = it.split(",")
                    Pair(list[0], list[1])
                }.toMap()
        restTemplate = RestTemplate()
        token = getKeyCloakToken()
    }


    fun importWorkflows() {
        val stream = USPControllerAPI::class.java.getResourceAsStream("/workflows")
        val files = stream.bufferedReader().use { it.readLines() }
        files.forEach {
            val fileName= "/workflows/$it"
            importWorkflow(fileName)
        }
    }

    fun importDefaultWorkflows() {
        val stream = USPControllerAPI::class.java.getResourceAsStream("/defaultWorkflows")
        val files = stream.bufferedReader().use { it.readLines() }
        files.forEach {
            val fileName= "/defaultWorkflows/$it"
            importWorkflow(fileName)
        }
    }

    private fun getKeyCloakToken(): String {
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

    /*********************************************************************
     *  Manage Workflows
     */
    fun runWorkflow(id: String, endpointId: String = ENDPOINT_ID): Pair<Int, String?> {
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
        } catch (e: HttpStatusCodeException) {
            //println(e.getStatusCode().value())
            //println(e.responseBodyAsString)
            return Pair(e.statusCode.value(), e.responseBodyAsString)
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

        val map = java.util.HashMap<String, String>()
        //println("------------- Workflows (${result.length()}) -------------")
        for (i in 0 until result.length()) {
            //println(result.getJSONObject(i).getString("name"))
            val key =  result.getJSONObject(i).getString("key")
            val id = result.getJSONObject(i).getString("id")
            map[key]  = id
        }
        return map
    }

    fun postWorkflowDefinition(workflowDefinition: WorkflowDefinition) {
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")
        val request = HttpEntity<WorkflowDefinition>(workflowDefinition, reqHeaders)
        val response = restTemplate.exchange("$USP_HOST/api/workflow-definitions",  HttpMethod.POST,request, String::class.java)
        //println(response)
    }

    /*********************************************************************
     *  USP Low level Operations
     *    Add
     *    Delete
     *    Get
     *    Set
     *    Operate
     */
    // allow partials is set to true by default in the workflow
    fun Add(addObject: AddObject, endpointId: String = ENDPOINT_ID): APIResponse {
        val id = workflowTable["addApi"]!!
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")

        val request = HttpEntity(addObject, reqHeaders)
        try {
            val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
            //println(response)
            return APIResponse(response.statusCodeValue, response.body?.fromJson())
        } catch (e: HttpStatusCodeException) {
            //println(e.getStatusCode().value())
            println(e.responseBodyAsString)
            //return Pair(e.statusCode.value(), e.responseBodyAsString)
            return APIResponse(e.statusCode.value(), e.responseBodyAsString?.fromJson())
        }
    }

    fun Delete(deleteObject: DeleteObject, endpointId: String = ENDPOINT_ID): APIResponse2 {
        val id = workflowTable["deleteApi"]!!
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")

        val request = HttpEntity(deleteObject, reqHeaders)
        try {
            val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
            //println(response)
            return APIResponse2(response.statusCodeValue, response.body?.fromJson())
        } catch (e: HttpStatusCodeException) {
            //println(e.getStatusCode().value())
            //println(e.responseBodyAsString)
            return APIResponse2(e.statusCode.value(), e.responseBodyAsString?.fromJson())
        }
    }

    fun Get(gpvObject: GpvObject, endpointId: String = ENDPOINT_ID): APIResponse {
        val id = workflowTable["getApi"]!!
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")

        val request = HttpEntity(gpvObject, reqHeaders)
        try {
            val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
            //println(response)
            return APIResponse(response.statusCodeValue, response.body?.fromJson())
        } catch (e: HttpStatusCodeException) {
            //println(e.getStatusCode().value())
            //println(e.responseBodyAsString)
            return APIResponse(e.statusCode.value(), e.responseBodyAsString?.fromJson())
        }
    }

    fun Set(setObject: SetObject, endpointId: String = ENDPOINT_ID): APIResponse {
        val id = workflowTable["setApi"]!!
        return post(setObject, id, endpointId, token, restTemplate)
    }

    fun Operate(operateObject: OperateObject, endpointId: String = ENDPOINT_ID): APIResponse {
        val id = workflowTable["operateApi"]!!
        return post(operateObject, id, endpointId, token, restTemplate)
    }

    inline fun <reified T> post(postObj: T, id: String, endpointId: String, token: String, restTemplate: RestTemplate): APIResponse {
        val reqHeaders = HttpHeaders()
        reqHeaders.accept = listOf(MediaType.ALL)
        reqHeaders.contentType = MediaType.APPLICATION_JSON
        reqHeaders.connection = listOf("close")
        reqHeaders.set("Authorization", "Bearer ${token}")

        val request = HttpEntity(postObj, reqHeaders)
        try {
            val response = restTemplate.exchange("$USP_HOST/api/workflows/$id/agent/$endpointId", HttpMethod.POST, request, String::class.java)
            //println(response)
            return APIResponse(response.statusCodeValue, response.body?.fromJson())
        } catch (e: HttpStatusCodeException) {
            //println(e.getStatusCode().value())
            //println(e.responseBodyAsString)
            return APIResponse(e.statusCode.value(), e.responseBodyAsString?.fromJson())
        }
    }


    //==========================================================================
    //
    //==========================================================================
    fun pathExists(path: String): Boolean {
        var gpv = GpvObject()
        gpv.add(path)
        var response = Get(gpv)
        return (response.body?.parameters?.size != 0)
    }

    fun deletePath(path: String) : Boolean {
        val deleteObject = DeleteObject()
        deleteObject.add(path)
        val response = Delete(deleteObject)
        return (response.code == 200)
    }


    //==========================================================================
    // Helper Methods
    //==========================================================================
    private fun getCurrentTime() : String {
        val tz = TimeZone.getTimeZone("UTC")
        //val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss'Z'") // Quoted "Z" to indicate UTC, no timezone offset
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        df.timeZone = tz
        return df.format(Date())
    }

    // Read a workflow from a file and post it
    fun importWorkflow(filename: String) {
        val name = filename.substringAfterLast("/")
        val key = genereateKey(name)
        val script = USPControllerAPI::class.java.getResource(filename).readText()
        val nowAsISO = getCurrentTime()
        val workflowDefinition = WorkflowDefinition(key, name, script, nowAsISO, nowAsISO, null)
        try {
            postWorkflowDefinition(workflowDefinition)
            println("imported $key")
        } catch (e: Exception) {
            println("Unable to push workflow $name")
        }
    }

    fun genereateKey(name: String) : String {
        return name.replace(" ", "").replace("-", "").replace(".js", "")
    }

} // end of USPControllerAPI


