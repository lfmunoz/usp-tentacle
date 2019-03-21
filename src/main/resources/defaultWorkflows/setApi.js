(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
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
        execute: function (log, agent, data) {
            log.debug("SET data: {}", data);
            var ALLOW_PARTIAL_DEFAULTED_UI_VALUE = true;
            var resp = yield Set(data.updateParameters, data.basePath, ALLOW_PARTIAL_DEFAULTED_UI_VALUE);
            log.debug("SET Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
