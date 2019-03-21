(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
                {
                    key: 'allowPartial', // message catalog suffix resolves to 'workflow.<key>.parameters'
                    type: 'boolean' // string, numeric, boolean
                },
                {
                    key: 'parameters', // message catalog suffix resolves to 'workflow.<key>.parameters'
                    type: 'string', // string, numeric, boolean
                    multiValue: true // multiple values
                }
            ]
        },
        // execution side of the script..
        execute: function (log, agent, data) {
            // log the data we're attempting to get
            log.debug("DELETE data: {}", data);
            var resp = yield Delete(data.parameters, data.allowPartial);
            log.debug("DELETE Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
