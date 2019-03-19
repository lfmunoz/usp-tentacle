(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
                {
                    key: 'allowPartialPath', // message catalog suffix resolves to 'workflow.<key>.parameters'
                    type: 'boolean' // string, numeric, boolean
                },
                {
                    key: 'basePath', // message catalog suffix resolves to 'workflow.<key>.parameters'
                    type: 'string'   // string, numeric, boolean
                },
                {
                    key: 'updateParameters', // message catalog suffix resolves to 'workflow.<key>.parameters'
                    type: 'map', // string, numeric, boolean
                    multiValue: true // multiple values
                }
            ]
        },
        // execution side of the script..

        var objData = {};
        objData["allowPartialPath"] = true;
        objData["basePath"] = "Device.DeviceInfo.";

        var objUpdateParams = {};
        objUpdateParams["updateParameters"] = {};
        objUpdateParams["param"] = "SoftwareVersion";
        objUpdateParams["value"] = "123";
        objUpdateParams["param"] = "HardwareVersion";
        objUpdateParams["value"] = "789";

        objData["updateParameters"] = objUpdateParams;

        execute: function (log, agent, data) {
            log.debug("SET data: {}", data);
            var resp = yield Set(objData.updateParameters, objData.basePath, objData.allowPartialPath);
            log.debug("SET Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
