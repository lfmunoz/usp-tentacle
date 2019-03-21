package eco.usp.automate.api

import eco.usp.automate.*
import eco.usp.automate.USPControllerUtils.Companion.workflowTable
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

    lateinit var restTemplate: RestTemplate
    lateinit var token: String

    @BeforeAll
    internal fun beforeAll() {
        restTemplate = RestTemplate()
        token = USPControllerUtils.getKeyCloakToken(restTemplate)
    }

    // 4 - Add Message - Allow Partial True
    // 9 - Delete Message - Allow Partial True
    @Test
    fun `4 - Add Message - Allow Partial True`() {

        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (pathExists(path)) {
            deletePath(path)
        }

        var objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("ID", "SUBS1", true))
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))

        var result = USPControllerUtils.Add(restTemplate, token, objData)
        println(result.second)
        assertThat(result.first).isEqualTo(200)

        path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
        if (pathExists(path)) {
            deletePath(path)
        }

        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("ID", "SUBS2", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))

        result = USPControllerUtils.Add(restTemplate, token, objData)
        println(result.second)
        assertThat(result.first).isEqualTo(200)

        path = "Device.LocalAgent.Subscription.[ID==\"SUBS3\"]."
        if (pathExists(path)) {
            deletePath(path)
        }

        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("ID", "SUBS3", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("InvalidParameter", "true", true))

        result = USPControllerUtils.Add(restTemplate, token, objData)
        println(result.second)
        assertThat(result.first).isEqualTo(200)

    }

    ////////////////////////////////////////////////////////////////////////////////
    // Helper Methods
    ////////////////////////////////////////////////////////////////////////////////
    fun pathExists(path: String): Boolean {
        var gpv = GpvObject()
        gpv.add(path)
        var gpvResult = USPControllerUtils.Gpv(restTemplate, token, gpv)
        if (gpvResult.second?.parameters?.size != 0) {
            return true
        }
        return false
    }

    fun deletePath(path: String) {
        val deleteObject = DeleteObject()
        deleteObject.add(path)
        val result = USPControllerUtils.Delete(restTemplate, token, deleteObject)
        assertThat(result.first).isEqualTo(200)
    }

}