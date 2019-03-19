// Asynchronous Operation Test Using Device.Firmware.Download - 3 Operate 2


(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
                // {
                //     key: 'allowPartialPath', // message catalog suffix resolves to 'workflow.<key>.parameters'
                //     type: 'boolean' // string, numeric, boolean
                // },
                // {
                //     key: 'basePath', // message catalog suffix resolves to 'workflow.<key>.parameters'
                //     type: 'string'   // string, numeric, boolean
                // },
                // {
                //     key: 'parameters', // message catalog suffix resolves to 'workflow.<key>.parameters'
                //     type: 'map', // string, numeric, boolean
                //     multiValue: true // multiple values
                // }
            ]
        },
        // execution side of the script..
        execute: function (log, agent, data) {
            data = {};
            data["command"] = "Device.DeviceInfo.FirmwareImage.2.Activate()";
            data["commandKey"] = "firmware_activate_command_key";
            data["sendResp"] = true;

            data["arguments"] = {};
            data["arguments"].push({"key":"TimeWindow.1.Start", "value":1});
            data["arguments"].push({"key":"TimeWindow.1.End", "value":10});
            data["arguments"].push({"key":"TimeWindow.1.Mode", "value":"Immediately"});
            data["arguments"].push({"key":"TimeWindow.1.MaxRetries", "value":3});

            log.debug("OPERATE data: {}", data);
            var resp = yield Operate(data.command, data.commandKey, data.sendResp, data.arguments);
            log.debug("OPERATE Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
