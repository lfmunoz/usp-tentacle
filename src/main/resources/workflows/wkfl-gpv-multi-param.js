(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
                {
                    key: 'parameters', // message catalog suffix resolves to 'workflow.<key>.parameters'
                    type: 'string', // string, numeric, boolean
                    multiValue: true // multiple values
                }
            ]
        },
        // execution side of the script..
        execute: function (log, agent, data) {

            var objData = {};
            objData["parameters"] = ["Device.DeviceInfo.SoftwareVersion", "Device.DeviceInfo.HardwareVersion"];

            // log the data we're attempting to get
            log.debug("GPV data: {}", objData);

            var resp = yield Get(objData.parameters);
            log.debug("GPV Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
