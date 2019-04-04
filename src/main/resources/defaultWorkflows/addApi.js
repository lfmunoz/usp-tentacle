(function () {
    return {

        // execution side of the script..
        execute: function (log, agent, data) {
            log.debug("ADD data: {}", data);

            var objData = {};
            objData["allowPartial"] = data.allowPartial;
            objData["parameters"] = [];

            for(let i = 0; i < data["parameters"].length; i++) {
                var obj = {}
                obj["basePath"] = data["parameters"][i].basePath
                obj["paramters"] = data["parameters"][i].parameters
                objData["parameters"].push(obj)
            }

            var resp = yield new Add(objData);
            log.debug("ADD Resp: \n{}", resp);
            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
