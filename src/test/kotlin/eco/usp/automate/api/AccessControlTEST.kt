package eco.usp.automate.api

import eco.usp.automate.USPControllerUtils
import eco.usp.automate.USPControllerUtils.Companion.workflowTable
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
    fun `35tofu`() {

    }

} // end of AccessControlTEST