package eco.usp.automate


// -------------------------------------------------------
//  Data classes
// -------------------------------------------------------
data class WorkflowDefinition(
        var key: String?,
        var name: String?,
        var script: String?,
        var createdDate: String? = null,
        var lastModifiedDate: String? = null,
        var id: Long? = null,
        var createdBy: String? = "kotlin",
        var lastModifiedBy: String = "kotlin",
        var type: String? = "JS_V1",
        var systemWorkflow: Boolean = false,
        var description: String? = null,
        var eventTrigger: String? = "NONE"
) {}

///////////////////////////////////////////////////////////////
// Workflow Response
///////////////////////////////////////////////////////////////
data class APIResponse(
        var code: Int,
        var body: WorkflowResponse?
)


data class APIResponse2(
        var code: Int,
        var body: WorkflowResponse2?
)

data class WorkflowResponse2(
        var errors: List<Errors>,
        var parameters: List<String>
)

data class WorkflowResponse(
        var errors: List<Errors>?,
        var parameters: Map<String, String>?
)

data class Errors(
        var parameter: String?,
        var errorCode: Int?,
        var errorMessage: String?
) {}

data class Parameter(
        var param: String,
        var value: String,
        var required: Boolean = false
) {}

///////////////////////////////////////////////////////////////
// Post Objects for USP API
///////////////////////////////////////////////////////////////
data class AddObjectWrapper(
    var allowPartial: Boolean = true,
    var parameters: ArrayList<AddObject> = ArrayList<AddObject>()
) {
    fun add(addObject: AddObject) {
        parameters.add(addObject)
    }
}


data class AddObject(
        var basePath: String,
        var parameters: ArrayList<Parameter> = ArrayList<Parameter>()
     //   var allowPartial: Boolean = true
) {
    fun add(parameter: Parameter) {
        parameters.add(parameter)
    }
}


data class SetObjectWrapper(
        var allowPartial: Boolean = true,
        var updateObjs: ArrayList<SetObject> = ArrayList<SetObject>()
) {
    fun add(setObject: SetObject) {
        updateObjs.add(setObject)
    }
}


data class SetObject(
        var basePath: String,
        var updateParameters: ArrayList<Parameter> = ArrayList<Parameter>()
) {
    fun add(parameter: Parameter) {
        updateParameters.add(parameter)
    }
}




data class GpvObject(
        var parameters: ArrayList<String> = ArrayList<String>()
) {
    fun add(parameter: String) {
        parameters.add(parameter)
    }
}

data class DeleteObject(
        var allowPartial: Boolean = false,
        var parameters: ArrayList<String> = ArrayList<String>()
) {
    fun add(parameter: String) {
        parameters.add(parameter)
    }
}

data class OperateObject(
        var command: String,
        var sendResp: Boolean = false,
        var arguments: HashMap<String, String> = HashMap<String, String>()
) {
    fun put(key: String, value: String) {
        arguments[key] =  value
    }
}
