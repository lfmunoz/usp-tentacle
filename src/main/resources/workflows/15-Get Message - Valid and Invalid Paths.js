// Get Message - Valid and Invalid Paths

(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
                // {
                //     // key: 'parameters', // message catalog suffix resolves to 'workflow.<key>.parameters'
                //     // type: 'string', // string, numeric, boolean
                //     // multiValue: true // multiple values
                // }
            ]
        },
        // execution side of the script..
        execute: function (log, agent, data) {
            data = {};
            data["parameters"] = ["Device.DeviceInfo.SerialNumber", "Device.LocalAgent.Subscription.999.ID", "Device.InvalidPath."];

            // log the data we're attempting to get
            log.debug("GPV data: {}", data);
            var resp = yield Get(data.parameters);
            log.debug("GPV Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
