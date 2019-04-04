
(function () {
    return {

        // execution side of the script..
        execute: function (log, agent, data) {
            var objData = {};
            objData["allowPartial"] = data.allowPartial;
            objData["updateObjs"] = [];

            for(let i = 0; i < data["updateObjs"].length; i++) {
                var obj = {}
                obj["basePath"] = data["updateObjs"][i].basePath
                obj["updateParameters"] = data["updateObjs"][i].updateParameters
                objData["updateObjs"].push(obj)
            }


            log.debug("SET data: {}", data);
            var resp = yield Set(objData);
            log.debug("SET Resp: \n{}", resp);

            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();