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
 *  CRUDOperationsTEST
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CRUDOperationsTEST{

  lateinit var restTemplate: RestTemplate
  lateinit var token: String
  lateinit var workflowTable: Map<String, String>

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


  @Test
  fun `4AddMessageAllowPartialTrue`() {
    val workflowId = workflowTable["4AddMessageAllowPartialTrue"]!!
    val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
    println(result.second)
    assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `5AddMessageAllowPartialFalse`() {
      val workflowId = workflowTable["5AddMessageAllowPartialFalse"]!!
      val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `6SetMessageAllowPartialTrue`() {
      val workflowId = workflowTable["6SetMessageAllowPartialTrue"]!!
      val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `7SetMessageAllowPartialFalse`() {
      val workflowId = workflowTable["7SetMessageAllowPartialFalse"]!!
      val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `8SetMessageSearchPaths`() {
      val workflowId = workflowTable["8SetMessageSearchPaths"]!!
      val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `9DeleteMessageAllowPartialTrue`() {
      val workflowId = workflowTable["9DeleteMessageAllowPartialTrue"]!!
      val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

    @Test
    fun `10DeleteMessageAllowPartialFalse`() {
        val workflowId = workflowTable["10DeleteMessageAllowPartialFalse"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `11GetMessageParameterPathOnly`() {
        val workflowId = workflowTable["11GetMessageParameterPathOnly"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `12GetMessageObjectPathsOnly`() {
        val workflowId = workflowTable["12GetMessageObjectPathsOnly"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `13GetMessageMixedPaths`() {
        val workflowId = workflowTable["13GetMessageMixedPaths"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `14GetMessageSearchPaths`() {
        val workflowId = workflowTable["14GetMessageSearchPaths"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `15GetMessageValidandInvalidPaths`() {
        val workflowId = workflowTable["15GetMessageValidandInvalidPaths"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }



}