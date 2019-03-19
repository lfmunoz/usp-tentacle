// Asynchronous Operation Test Using Device.Firmware.Download - 2 Operate


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
            data["command"] = "Device.DeviceInfo.FirmwareImage.2.Download()";
            data["commandKey"] = "firmware_download_command_key";
            data["sendResp"] = true;

            data["arguments"] = {};
            data["arguments"].push({"key":"URL", "value":"<URL of the firmware determined in test setup>"});
            data["arguments"].push({"key":"Username", "value":""});
            data["arguments"].push({"key":"Password", "value":""});

            log.debug("OPERATE data: {}", data);
            var resp = yield Operate(data.command, data.commandKey, data.sendResp, data.arguments);
            log.debug("OPERATE Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
