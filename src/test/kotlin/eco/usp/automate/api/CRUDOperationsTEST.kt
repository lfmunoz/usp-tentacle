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



    //val referenceList = "Device.Time.NTPServer1"
    val referenceList =  "Device.LocalAgent.EndpointID"
    //val trueValue = "true"
    val trueValue = "1"
    val falseValue = "0"

    /*
    @Test
    fun `greenwave`() {
        var objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("ID", "SUBS1", true))
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", "Device.ProxiedDevice.5.bistate", true))
        var response = api.Add(objData)
        println(response.body)

    }
    */


    // 4 - Add Message - Allow Partial True
    // 9 - Delete Message - Allow Partial True
    @Test
    fun `4 - Add Message - Allow Partial True`() {


        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }
        path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }

        path = "Device.LocalAgent.Subscription.[ID==\"SUBS3\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }


        val wrapper = AddObjectWrapper(allowPartial = true)

        var objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("ID", "SUBS1", true))
        objData.add(Parameter("Enable", falseValue, true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        //objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("ReferenceList", referenceList, true))
        wrapper.add(objData)


        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", falseValue, true))
        objData.add(Parameter("ID", "SUBS2", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        //objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("ReferenceList", referenceList, true))
        wrapper.add(objData)


        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", falseValue, true))
        objData.add(Parameter("ID", "SUBS3", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        //objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("ReferenceList", referenceList, true))
        objData.add(Parameter("InvalidParameter", "true", true))
        wrapper.add(objData)



            var response = api.Add(wrapper)
            println(response.toJson(true))
            assertThat(response.code).isEqualTo(200)
            assertThat(response.body?.errors?.size).isEqualTo(1)
            assertThat(response.body?.parameters?.size).isGreaterThan(4)


            path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
            assertThat(api.pathExists(path)).isTrue()
            api.deletePath(path)
            path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
            assertThat(api.pathExists(path)).isTrue()
            api.deletePath(path)
            path = "Device.LocalAgent.Subscription.[ID==\"SUBS3\"]."
            assertThat(api.pathExists(path)).isFalse()
            api.deletePath(path)



    }


    // 5 - Add Message - Allow Partial False
    // 10 - Delete Message - Allow Partial False
    @Test
    fun `5 - Add Message - Allow Partial False`() {


        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }
        path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }

        path = "Device.LocalAgent.Subscription.[ID==\"SUBS3\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }


        val wrapper = AddObjectWrapper(allowPartial = false)

        var objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("ID", "SUBS1", true))
        objData.add(Parameter("Enable", falseValue, true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        //objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("ReferenceList", referenceList, true))
        wrapper.add(objData)


        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", "false", true))
        objData.add(Parameter("ID", "SUBS2", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        //objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("ReferenceList", referenceList, true))
        wrapper.add(objData)


        objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("Enable", falseValue, true))
        objData.add(Parameter("ID", "SUBS3", true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        //objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        objData.add(Parameter("ReferenceList", referenceList, true))
        objData.add(Parameter("InvalidParameter", "true", true))
        wrapper.add(objData)



        // API is not consistent in how it returns errors

        try {
            var response = api.Add(wrapper)
        } catch (e: Exception) {

        } finally {

            path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
            assertThat(api.pathExists(path)).isFalse()
            api.deletePath(path)
            path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
            assertThat(api.pathExists(path)).isFalse()
            api.deletePath(path)
            path = "Device.LocalAgent.Subscription.[ID==\"SUBS3\"]."
            assertThat(api.pathExists(path)).isFalse()
            api.deletePath(path)

        }

    }



    @Test
    fun `6- Set Message - Allow Partial True`() {

        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (!api.pathExists(path)) {
            val addObjectWrapper = AddObjectWrapper(allowPartial = false)
            val objData = AddObject("Device.LocalAgent.Subscription.")
            objData.add(Parameter("ID", "SUBS1", true))
            objData.add(Parameter("Enable", falseValue, true))
            objData.add(Parameter("NotifType", "ValueChange", true))
            objData.add(Parameter("ReferenceList", referenceList, true))
            addObjectWrapper.add(objData)
            var addObjectResponse = api.Add(addObjectWrapper)
            println(addObjectResponse.toJson(true))
        }
        path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
        if (!api.pathExists(path)) {
            val addObjectWrapper = AddObjectWrapper(allowPartial = false)
            val objData = AddObject("Device.LocalAgent.Subscription.")
            objData.add(Parameter("Enable", falseValue, true))
            objData.add(Parameter("ID", "SUBS2", true))
            objData.add(Parameter("NotifType", "ValueChange", true))
            objData.add(Parameter("ReferenceList", referenceList, true))
            addObjectWrapper.add(objData)
            var addObjectResponse = api.Add(addObjectWrapper)
            println(addObjectResponse.toJson(true))
        }


        var wrapper = SetObjectWrapper(allowPartial = true)
        var setObject = SetObject("Device.LocalAgent.Subscription.[ID==\"SUBS1\"]" )
        setObject.add(Parameter("Enable", falseValue, true))
        wrapper.add(setObject)

        setObject = SetObject(basePath = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"].")
        setObject.add(Parameter("Enable", trueValue, false))
        setObject.add(Parameter("InvalidPathEnable", "true", false))
        wrapper.add(setObject)

        var response = api.Set(wrapper)

        println(response.toJson(true))

        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.parameters?.size).isEqualTo(2)
        assertThat(response.body?.errors?.size).isEqualTo(1)

        path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        assertThat(api.pathExists(path)).isTrue()
        api.deletePath(path)
        path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
        assertThat(api.pathExists(path)).isTrue()
        api.deletePath(path)


    }


    @Test
    fun `7 - Set Message - Allow Partial False`() {

        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (!api.pathExists(path)) {
            val addObjectWrapper = AddObjectWrapper(allowPartial = false)
            val objData = AddObject("Device.LocalAgent.Subscription.")
            objData.add(Parameter("ID", "SUBS1", true))
            objData.add(Parameter("Enable", falseValue, true))
            objData.add(Parameter("NotifType", "ValueChange", true))
            objData.add(Parameter("ReferenceList", referenceList, true))
            addObjectWrapper.add(objData)
            var addObjectResponse = api.Add(addObjectWrapper)
            println(addObjectResponse.toJson(true))
        }
        path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
        if (!api.pathExists(path)) {
            val addObjectWrapper = AddObjectWrapper(allowPartial = false)
            val objData = AddObject("Device.LocalAgent.Subscription.")
            objData.add(Parameter("Enable", falseValue, true))
            objData.add(Parameter("ID", "SUBS2", true))
            objData.add(Parameter("NotifType", "ValueChange", true))
            objData.add(Parameter("ReferenceList", referenceList, true))
            addObjectWrapper.add(objData)
            var addObjectResponse = api.Add(addObjectWrapper)
            println(addObjectResponse.toJson(true))
        }


        var wrapper = SetObjectWrapper(allowPartial = false)
        var setObject = SetObject("Device.LocalAgent.Subscription.[ID==\"SUBS1\"]" )
        setObject.add(Parameter("Enable", trueValue, true))
        wrapper.add(setObject)

        setObject = SetObject(basePath = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"].")
        setObject.add(Parameter("Enable", trueValue, false))
        setObject.add(Parameter("InvalidPathEnable", "true", true))
        wrapper.add(setObject)

        try {
            var response = api.Set(wrapper)
        } catch (e: Exception) {

        } finally {
            path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
            assertThat(api.pathExists(path)).isTrue()
            api.deletePath(path)
            path = "Device.LocalAgent.Subscription.[ID==\"SUBS2\"]."
            assertThat(api.pathExists(path)).isTrue()
            api.deletePath(path)
        }
    }


    @Test
    fun `8 - Set Message - Search Paths`() {
        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }
        val addObjectWrapper = AddObjectWrapper(allowPartial = false)
        val objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("ID", "SUBS1", true))
        objData.add(Parameter("Enable", trueValue, true))
        objData.add(Parameter("NotifType", "ValueChange", true))
        objData.add(Parameter("ReferenceList", referenceList, true))
        addObjectWrapper.add(objData)
        var addObjectResponse = api.Add(addObjectWrapper)
        println(addObjectResponse.toJson(true))

        var wrapper = SetObjectWrapper(allowPartial = false)
        var setObject = SetObject("Device.LocalAgent.Subscription.[Enable==$trueValue&&NotifType==\"ValueChange\"].")
        setObject.add(Parameter("Enable", "true", true))
        wrapper.add(setObject)


        var response = api.Set(wrapper)

        println(response.toJson(true))
        assertThat(api.pathExists(path)).isTrue()
        api.deletePath(path)

    }

    @Test
    fun `11 - Get Message - Parameter Path Only`() {
        val obj = GpvObject()
        obj.add("Device.DeviceInfo.SerialNumber")
        obj.add("Device.DeviceInfo.Manufacturer")
        var response = api.Get(obj)
        println(response.toJson(true))
        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.parameters?.size).isEqualTo(2)
    }

    @Test
    fun `12 - Get Message - Object Path Only`() {
        val obj = GpvObject()
        obj.add("Device.DeviceInfo.")
        obj.add("Device.LocalAgent.Subscription.")

        var response = api.Get(obj)
        println(response.toJson(true))
        assertThat(response.code).isEqualTo(200)
    }

    @Test
    fun `13 - Get Message - Mixed Paths`() {
        val obj = GpvObject()
        obj.add("Device.DeviceInfo.SerialNumber")
        obj.add("Device.LocalAgent.Controller.")

        var response = api.Get(obj)
        println(response.toJson(true))
        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.parameters?.get("Device.DeviceInfo.SerialNumber")).isNotBlank()
    }

    @Test
    fun `14 - Get Message - Search Paths`() {
        var path = "Device.LocalAgent.Subscription.[ID==\"SUBS1\"]."
        if (api.pathExists(path)) {
            api.deletePath(path)
        }
        val addObjectWrapper = AddObjectWrapper(allowPartial = false)
        val objData = AddObject("Device.LocalAgent.Subscription.")
        objData.add(Parameter("ID", "SUBS1", true))
        objData.add(Parameter("Enable", trueValue, true))
      //  objData.add(Parameter("NotifType", "ValueChange", true))
        //objData.add(Parameter("ReferenceList", "Device.LocalAgent.EndpointID", true))
        addObjectWrapper.add(objData)
        var addObjectResponse = api.Add(addObjectWrapper)
        println(addObjectResponse.toJson(true))


        var obj = GpvObject()
        obj.add("Device.LocalAgent.Subscription.[Enable==\"true\"].ID")

        var response = api.Get(obj)
        println(response.toJson(true))
        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.parameters?.size).isGreaterThan(0)

        obj = GpvObject()
        obj.add("Device.LocalAgent.Subscription.[Enable==\"1\"].ID")
        response = api.Get(obj)
        println(response.toJson(true))
        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.parameters?.size).isGreaterThan(0)
    }

    @Test
    fun `15 - Get Message - Valid and Invalid Paths`() {
        var obj = GpvObject()
        obj.add("Device.DeviceInfo.SerialNumber")
        obj.add("Device.LocalAgent.Subscription.999.ID")
        obj.add("Device.InvalidPath.")

        var response = api.Get(obj)
        println(response.toJson(true))
        assertThat(response.code).isEqualTo(200)
        assertThat(response.body?.parameters?.size).isEqualTo(1)
        assertThat(response.body?.errors?.size).isEqualTo(1)

    }

}