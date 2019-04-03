package eco.usp.automate.workflow

import eco.usp.automate.USPControllerAPI
import eco.usp.automate.WorkflowResponse
import eco.usp.automate.mapper
import eco.usp.automate.toJson
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

    // fields
    lateinit var api : USPControllerAPI

    @BeforeAll
    internal fun beforeAll() {
        api = USPControllerAPI()
    }

  @Test
  fun `4AddMessageAllowPartialTrue`() {
    val workflowId = api.workflowTable["4AddMessageAllowPartialTrue"]!!
    val result = api.runWorkflow(workflowId)
    //println(result.second)
    //val response = mapper.readValue(result.second, WorkflowResponse::class.java)
    //println(response)
    assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `5AddMessageAllowPartialFalse`() {
      val workflowId = api.workflowTable["5AddMessageAllowPartialFalse"]!!
      val result = api.runWorkflow(workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `6SetMessageAllowPartialTrue`() {
      val workflowId = api.workflowTable["6SetMessageAllowPartialTrue"]!!
      val result = api.runWorkflow(workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `7SetMessageAllowPartialFalse`() {
      val workflowId = api.workflowTable["7SetMessageAllowPartialFalse"]!!
      val result = api.runWorkflow(workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `8SetMessageSearchPaths`() {
      val workflowId = api.workflowTable["8SetMessageSearchPaths"]!!
      val result = api.runWorkflow(workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `9DeleteMessageAllowPartialTrue`() {
      val workflowId = api.workflowTable["9DeleteMessageAllowPartialTrue"]!!
      val result = api.runWorkflow(workflowId)
      println(result.second)
      assertThat(result.first).isEqualTo(200)
  }

    @Test
    fun `10DeleteMessageAllowPartialFalse`() {
        val workflowId = api.workflowTable["10DeleteMessageAllowPartialFalse"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `11GetMessageParameterPathOnly`() {
        val workflowId = api.workflowTable["11GetMessageParameterPathOnly"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `12GetMessageObjectPathsOnly`() {
        val workflowId = api.workflowTable["12GetMessageObjectPathsOnly"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `13GetMessageMixedPaths`() {
        val workflowId = api.workflowTable["13GetMessageMixedPaths"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `14GetMessageSearchPaths`() {
        val workflowId = api.workflowTable["14GetMessageSearchPaths"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `15GetMessageValidandInvalidPaths`() {
        val workflowId = api.workflowTable["15GetMessageValidandInvalidPaths"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }



}