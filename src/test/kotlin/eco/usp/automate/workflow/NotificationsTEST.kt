package eco.usp.automate.workflow

import eco.usp.automate.USPControllerUtils
import eco.usp.automate.USPControllerUtils.Companion.workflowTable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.web.client.RestTemplate

/**
 *  NotificationsTEST
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationsTEST {

    // fields
    lateinit var restTemplate: RestTemplate
    lateinit var token: String

    // init
    @BeforeAll
    internal fun beforeAll() {
        restTemplate = RestTemplate()
        token = USPControllerUtils.getKeyCloakToken(restTemplate)
    }

    ////////////////////////////////////////////////////////////////////////
    // Tests
    ////////////////////////////////////////////////////////////////////////
    @Test
    fun `21SubscriptionCreationUsingValueChange`() {
        val workflowId = workflowTable["21SubscriptionCreationUsingValueChange"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `22SubscriptionDisableUsingValueChange`() {
        val workflowId = workflowTable["22SubscriptionDisableUsingValueChange"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `23SubscriptionDeletionUsingValueChange`() {
        val workflowId = workflowTable["23SubscriptionDeletionUsingValueChange"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `24SubscriptiononObjectCreation`() {
        val workflowId = workflowTable["24SubscriptiononObjectCreation"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `25SubscriptiononObjectDeletion`() {
        val workflowId = workflowTable["25SubscriptiononObjectDeletion"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `26OnBoardRequest`() {
        val workflowId = workflowTable["26OnBoardRequest"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `27SubscriptiononNotificationRetry`() {
        val workflowId = workflowTable["27SubscriptiononNotificationRetry"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `28SubscriptiononNotificationRetryRetryExpiration`() {
        val workflowId = workflowTable["28SubscriptiononNotificationRetryRetryExpiration"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `29SubscriptionExpiration`() {
        val workflowId = workflowTable["29SubscriptionExpiration"]!!
        val result = USPControllerUtils.runWorkflow(restTemplate, token, workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

} // end of NotificationsTEST