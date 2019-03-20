package eco.usp.automate

import com.attendcall.genesis.rest.LoadWorkflows
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.web.client.RestTemplate




/**
 *
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
    fun `add object`() {
        val addObject = AddObject("Device.LocalAgent.Subscription.")
        addObject.add(Parameter("ID", "SUBS1", true))
        addObject.add(Parameter("Enable", "false", true))
        addObject.add(Parameter("NotifType", "ValueChange", true))
        addObject.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        val result = USPControllerUtils.Add(restTemplate, token, addObject)
        assertThat(result.first).isEqualTo(200)
        println(result.second)

    }

    @Test
    fun `gpv on Device dot DeviceInfo`() {
        val parameters = GpvObject()
        parameters.add("Device.DeviceInfo.")
        val result = USPControllerUtils.Gpv(restTemplate, token, parameters)
        assertThat(result.first).isEqualTo(200)
        println(result.second)
    }

    @Test
    fun `delete Object`() {
        val deleteObject = DeleteObject(true)
        deleteObject.add("Device.LocalAgent.Subscription.[ID==\"SUBS1\"].")
        deleteObject.add("Device.LocalAgent.Subscription.10000.")
        val result = USPControllerUtils.Delete(restTemplate, token, deleteObject)
        assertThat(result.first).isEqualTo(200)
        println(result.second)
    }

} // end of
