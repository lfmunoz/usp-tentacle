//Add Message - Allow Partial True

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
            objData1["parameters"].push({"param":"ID", "value":"SUBS1", "required":true});
            objData1["parameters"].push({"param":"Enable", "value":false, "required":true});
            objData1["parameters"].push({"param":"NotifType", "value":"ValueChange", "required":true});
            objData1["parameters"].push({"param":"ReferenceList", "value":"Device.LocalAgent.EndpointID", "required":true});

            var objData2 = {};
            objData2["basePath"] = "Device.LocalAgent.Subscription.";
            objData2["parameters"] = [];
            objData2["parameters"].push({"param":"ID", "value":"SUBS2", "required":true});
            objData2["parameters"].push({"param":"Enable", "value":false, "required":true});
            objData2["parameters"].push({"param":"NotifType", "value":"ValueChange", "required":true});
            objData2["parameters"].push({"param":"ReferenceList", "value":"Device.LocalAgent.EndpointID", "required":true});

            var objData3 = {};
            objData3["basePath"] = "Device.LocalAgent.Subscription.";
            objData3["parameters"] = [];
            objData3["parameters"].push({"param":"ID", "value":"SUBS3", "required":true});
            objData3["parameters"].push({"param":"Enable", "value":false, "required":true});
            objData3["parameters"].push({"param":"NotifType", "value":"ValueChange", "required":true});
            objData3["parameters"].push({"param":"ReferenceList", "value":"Device.LocalAgent.EndpointID", "required":true});
            objData3["parameters"].push({"param":"InvalidParameter", "value":true, "required":true});

            objData["parameters"].push(objData1);
            objData["parameters"].push(objData2);
            objData["parameters"].push(objData3);

            log.debug("ADD data: {}", objData);
            var resp = yield new Add(objData);
            log.debug("ADD Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
