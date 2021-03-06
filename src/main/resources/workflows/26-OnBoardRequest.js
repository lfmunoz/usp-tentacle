// OnBoardRequest

(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
                // {
                //     key: 'command', // the command to execute
                //     type: 'string' // string, numeric, boolean
                // },
                // {
                //     key: 'commandKey', // a reference for the command
                //     type: 'string', // string, numeric, boolean
                // },
                // {
                //     key: 'sendResp', // a reference for the command
                //     type: 'boolean', // string, numeric, boolean
                // },
                // {
                //     key: 'arguments',
                //     type: 'map'
                // }
            ]
        },
        // execution side of the script..
        execute: function (log, agent, data) {
            data = {};
            data["command"] = "Device.LocalAgent.Controller.[EndpointID==\"dev_endpoint\"].SendOnBoardRequest()";
            data["commandKey"] = "onboard_command_key";
            data["sendResp"] = false;

            data["arguments"] = {};

            log.debug("OPERATE data: {}", data);
            var resp = yield Operate(data.command, data.commandKey, data.sendResp, data.arguments);
            log.debug("OPERATE Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
