package eco.usp.automate

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

/**
 * Integraton Test  - USPControllerAPITest
 */
class USPControllerAPITest {

    // Unit Under Test
     lateinit var api : USPControllerAPI

    //================================================================================
    // Setup
    //================================================================================
    @BeforeEach
    fun `before each test`() {
       api = USPControllerAPI()
    }

    //================================================================================
    // Tests
    //================================================================================
    // used for created the workflowTable.txt
    @Test
    fun `get list of workflows`() {
        val workflows = api.getListOfWorkflows()
        workflows.toSortedMap().forEach {
            //println("key = ${it.key}, id = ${it.value}")
            println("${it.key},${it.value}")
        }
    }

    @Test fun`import default workflows`() {
        api.importDefaultWorkflows()
    }

    @Test fun`import workflows`() {
        api.importWorkflows()
    }

    @Test
    fun `run 10DeleteMessageAllowPartialFalse workflow`() {
        val id = api.workflowTable["10DeleteMessageAllowPartialFalse"] ?: throw RuntimeException("key not found")
        api.runWorkflow(id)
    }

    //================================================================================
    // API Tests
    //================================================================================
    @Test
    fun `add object`() {
        val addObject = AddObject("Device.LocalAgent.Subscription.")
        addObject.add(Parameter("ID", "SUBS1", true))
        addObject.add(Parameter("Enable", "false", true))
        addObject.add(Parameter("NotifType", "ValueChange", true))
        addObject.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        val result = api.Add(addObject)
        Assertions.assertThat(result.first).isEqualTo(200)
        println(result.second)

    }

    @Test
    fun `gpv on Device dot DeviceInfo`() {
        val parameters = GpvObject()
        parameters.add("Device.DeviceInfo.")
        val result = api.Get(parameters)
        Assertions.assertThat(result.first).isEqualTo(200)
        println(result.second)
    }

    @Test
    fun `delete Object`() {
        val deleteObject = DeleteObject(true)
        deleteObject.add("Device.LocalAgent.Subscription.[ID==\"SUBS1\"].")
        deleteObject.add("Device.LocalAgent.Subscription.10000.")
        val result = api.Delete(deleteObject)
        Assertions.assertThat(result.first).isEqualTo(200)
        println(result.second)
    }

}