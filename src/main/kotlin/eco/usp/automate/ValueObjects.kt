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

data class WorkflowResponse(
        var errors: List<Errors>,
        var parameters: Map<String, String>
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

data class AddObject(
        var basePath: String,
        var parameters: ArrayList<Parameter> = ArrayList<Parameter>()
) {
    fun add(parameter: Parameter) {
        parameters.add(parameter)
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

