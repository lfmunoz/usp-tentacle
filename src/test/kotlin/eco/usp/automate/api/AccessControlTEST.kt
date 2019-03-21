package eco.usp.automate.api

import eco.usp.automate.USPControllerAPI
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
    lateinit var api : USPControllerAPI

    // init
    @BeforeAll
    internal fun beforeAll() {
        api = USPControllerAPI()
    }

    ////////////////////////////////////////////////////////////////////////
    // Tests
    ////////////////////////////////////////////////////////////////////////
    @Test
    fun `35tofu`() {

    }

} // end of AccessControlTEST