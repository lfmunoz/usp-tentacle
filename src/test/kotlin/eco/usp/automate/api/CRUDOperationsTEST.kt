package eco.usp.automate.api

import eco.usp.automate.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.web.client.RestTemplate

/**
 *  CRUDOperationsTEST
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CRUDOperationsTEST {

    // fields
    lateinit var api : USPControllerAPI

    @BeforeAll
    internal fun beforeAll() {
        api = USPControllerAPI()
    }

    // 4 - Add Message - Allow Partial True
    // 9 - Delete Message - Allow Partial True
    @Test
    fun `Add Message - Allow Partial True`() {

        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }

        var objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("ID", "SUBS1", true))
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))

        var response = api.Add(objData)
        println(response.body)
        assertThat(response.code).isEqualTo(200)

        path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }

        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("ID", "SUBS2", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))

        response = api.Add(objData)
        println(response.body)
        assertThat(response.code).isEqualTo(200)

        path = "Device.LocalAgent.Subscription.[ID==\"SUBS3\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }

        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("ID", "SUBS3", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("InvalidParameter", "true", true))

        response = api.Add(objData)
        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.errors?.get(0)?.errorCode).isEqualTo(7010)

    }

    @Test
    fun `Add Message - Allow Partial False`() {

    }


}