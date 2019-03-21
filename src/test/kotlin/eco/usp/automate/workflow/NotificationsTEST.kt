package eco.usp.automate.workflow

import eco.usp.automate.USPControllerAPI
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

/**
 *  NotificationsTEST
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationsTEST {

    // fields
    lateinit var api : USPControllerAPI

    @BeforeAll
    internal fun beforeAll() {
        api = USPControllerAPI()
    }

    ////////////////////////////////////////////////////////////////////////
    // Tests
    ////////////////////////////////////////////////////////////////////////
    @Test
    fun `21SubscriptionCreationUsingValueChange`() {
        val workflowId = api.workflowTable["21SubscriptionCreationUsingValueChange"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `22SubscriptionDisableUsingValueChange`() {
        val workflowId = api.workflowTable["22SubscriptionDisableUsingValueChange"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `23SubscriptionDeletionUsingValueChange`() {
        val workflowId = api.workflowTable["23SubscriptionDeletionUsingValueChange"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `24SubscriptiononObjectCreation`() {
        val workflowId = api.workflowTable["24SubscriptiononObjectCreation"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `25SubscriptiononObjectDeletion`() {
        val workflowId = api.workflowTable["25SubscriptiononObjectDeletion"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `26OnBoardRequest`() {
        val workflowId = api.workflowTable["26OnBoardRequest"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

    @Test
    fun `27SubscriptiononNotificationRetry`() {
        val workflowId = api.workflowTable["27SubscriptiononNotificationRetry"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `28SubscriptiononNotificationRetryRetryExpiration`() {
        val workflowId = api.workflowTable["28SubscriptiononNotificationRetryRetryExpiration"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }
    @Test
    fun `29SubscriptionExpiration`() {
        val workflowId = api.workflowTable["29SubscriptionExpiration"]!!
        val result = api.runWorkflow(workflowId)
        println(result.second)
        assertThat(result.first).isEqualTo(200)
    }

} // end of NotificationsTEST