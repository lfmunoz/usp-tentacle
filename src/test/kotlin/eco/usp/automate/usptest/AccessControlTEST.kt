package eco.usp.automate.usptest

import com.attendcall.genesis.rest.LoadWorkflows
import eco.usp.automate.USPControllerUtils
import eco.usp.automate.USPControllerUtils.Companion.FILE_NAME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.web.client.RestTemplate

/**
 *  AccessControlTEST
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccessControlTEST {

    // fields
    lateinit var restTemplate: RestTemplate
    lateinit var token: String
    lateinit var workflowTable: Map<String, String>

    // init
    @BeforeAll
    internal fun beforeAll() {
        restTemplate = RestTemplate()
        token = USPControllerUtils.getKeyCloakToken(restTemplate)

        val inputText = LoadWorkflows::class.java.getResource(FILE_NAME).readText().trim()
        workflowTable = inputText.split("\n").asSequence().map {
            val list = it.split(",")
            Pair(list[0], list[1])
        }.toMap()

    }

    ////////////////////////////////////////////////////////////////////////
    // Tests
    ////////////////////////////////////////////////////////////////////////
    @Test
    fun `35tofu`() {
        val workflowId = workflowTable["35tofu"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

} // end of AccessControlTEST