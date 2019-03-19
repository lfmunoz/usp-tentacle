// Subscription on Notification Retry

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
            var objData = {};
            objData["allowPartial"] = true;
            objData["parameters"] = [];

            var objData1 = {};
            objData1["basePath"] = "Device.LocalAgent.Subscription.";
            objData1["parameters"] = [];
            objData1["parameters"].push({"param":"ID", "value":"SUBS6", "required":true});
            objData1["parameters"].push({"param":"Enable", "value":true, "required":true});
            objData1["parameters"].push({"param":"NotifType", "value":"ValueChange", "required":true});
            objData1["parameters"].push({"param":"ReferenceList", "value":"Device.LocalAgent.Subscription.[ID==\"SUBS2\"].Enable", "required":true});
            objData1["parameters"].push({"param":"NotifRetry", "value":true, "required":true});

            objData["parameters"].push(objData1);

            log.debug("ADD data: {}", objData);
            var resp = yield new Add(objData);
            log.debug("ADD Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
