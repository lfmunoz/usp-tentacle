package eco.usp.automate.api

import eco.usp.automate.AddObject
import eco.usp.automate.DeleteObject
import eco.usp.automate.Parameter
import eco.usp.automate.USPControllerUtils
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
class CRUDOperationsTEST{

  lateinit var restTemplate: RestTemplate
  lateinit var token: String

  @BeforeAll
  internal fun beforeAll() {
    restTemplate = RestTemplate()
    token = USPControllerUtils.getKeyCloakToken(restTemplate)
  }


  @Test
  fun `4 - Add Message - Allow Partial True`() {
    var objData = AddObject("Device.LocalAgent.Subscription.")
    objData.add(Parameter("ID", "SUBS1", true))
    objData.add(Parameter("Enable", "false", true))
    objData.add(Parameter("NotifType", "ValueChange", true))
    objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))

    var result = USPControllerUtils.Add(restTemplate, token, objData)
    println(result.second)
    assertThat(result.first).isEqualTo(200)

    objData = AddObject("Device.LocalAgent.Subscription.")
    objData.add(Parameter("Enable", "false", true))
    objData.add(Parameter("ID", "SUBS2", true))
    objData.add(Parameter("NotifType", "ValueChange", true))
    objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))

    result = USPControllerUtils.Add(restTemplate, token, objData)
    println(result.second)
    assertThat(result.first).isEqualTo(200)

    objData = AddObject("Device.LocalAgent.Subscription.")
    objData.add(Parameter("Enable", "false", true))
    objData.add(Parameter("ID", "SUBS1", true))
    objData.add(Parameter("NotifType", "ValueChange", true))
    objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
    objData.add(Parameter("InvalidParameter", "true", true))

    result = USPControllerUtils.Add(restTemplate, token, objData)
    println(result.second)
    assertThat(result.first).isEqualTo(200)
  }

  @Test
  fun `9 - Delete Message - Allow Partial True`() {
    var deleteObject = DeleteObject()
    deleteObject.add("Device.LocalAgent.Subscription.[ID==\"SUBS1\"].")
    deleteObject.add("Device.LocalAgent.Subscription.10000.")
    var result = USPControllerUtils.Delete(restTemplate, token, deleteObject)
    println(result.second)
    assertThat(result.first).isEqualTo(200)
  }

}