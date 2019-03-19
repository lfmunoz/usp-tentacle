// TOFU
//
// 1. Controller does GET message(s) to read everything it is allowed to Get.
// 2. If GET (Step 1) succeeded, Controller issues SET for something no-one in their right mind would allow an untrusted Controller to Set, but that is allowed for other Role.
// 3. If GET (Step 1) succeeded, Controller issues ADD for something no-one in their right mind would allow an untrusted Controller to Add, but that is allowed for other Role.
// 4. If GET (Step 1) succeeded Controller issues DELETE for something no-one in their right mind would allow an untrusted Controller to Delete, but that is allowed for other Role.
// If RequestChallenge() is supported (i.e., returned by Step 1 GET and supported by the Controller), have the Controller invoke RequestChallenge() (ideally to achieve the other Role mentioned in Steps 2-4).
// Controller prompts user to provide appropriate response and sends ChallengeResponse().
// If successful, repeat Steps 1-4.
// If some other means of changing a Controller's permissions (Role) is supported, use that to set the Controller's permissions to something other than an "untrusted" Role.
// Repeat Steps 1-4.


// Set Message - Allow Partial True

(function () {
    return {
        // meta-data for the UI/API about this workflow.
        metaData: {
            // defined arguments for this workflow to be utilized by the UI/API
            arguments: [
            ]
        },
        // execution side of the script..
        execute: function (log, agent, data) {
            // 1. Controller does GET message(s) to read everything it is allowed to Get.
            data = {};
            data.parameters = ["Device.DeviceInfo.", "Device.LocalAgent.Subscription."];

            // log the data we're attempting to get
            log.debug("GPV data: {}", data);
            var resp = yield Get(data.parameters);
            log.debug("GPV Resp: \n{}", resp);

            // If GET (Step 1) succeeded, Controller issues SET for something 
            // no-one in their right mind would allow an untrusted Controller to Set, 
            // but that is allowed for other Role.

            data = {};
            data["allowPartialPath"] = true;
            data["basePath"] = "Device.DeviceInfo.SoftwareVersion.";

            data["updateParameters"] = [];
            data["updateParameters"].push({"param":"ID", "value":"TEST", "required":true});
            data["updateParameters"].push({"param":"Enable", "value":"false", "required":true});

            log.debug("SET data: {}", data);
            var resp = yield Set(data.updateParameters, data.basePath, data.allowPartialPath);
            log.debug("SET Resp: \n{}", resp);

            // If GET (Step 1) succeeded, Controller issues ADD for something no-one
            // in their right mind would allow an untrusted Controller to Add, 
            // but that is allowed for other Role.

            data = {};
            data["allowPartialPath"] = true;
            data["basePath"] = "Device.LocalAgent.Subscription.";

            data["parameters"] = [];
            data["parameters"].push({"param":"ID", "value":"TEST", "required":true});
            data["parameters"].push({"param":"Enable", "value":"false", "required":true});

            log.debug("ADD data: {}", data);
            var resp = yield new Add(data.parameters, data.basePath, data.allowPartialPath);
            log.debug("ADD Resp: \n{}", resp);

            // If GET (Step 1) succeeded Controller issues DELETE for something no-one
            // in their right mind would allow an untrusted Controller to Delete, 
            // but that is allowed for other Role.

            data = {};
            data["allowPartialPath"] = true;
            data["parameters"] = ["Device.LocalAgent.Subscription."];

            // log the data we're attempting to get
            log.debug("DELETE data: {}", data);

            var resp = yield Delete(data.parameters, data.allowPartialPath);
            log.debug("DELETE Resp: \n{}", resp);

            // ================================ RequestChallenge() ================================
            // If RequestChallenge() is supported (i.e., returned by Step 1 GET and supported by the Controller),
            // have the Controller invoke RequestChallenge() (ideally to achieve the other Role mentioned in Steps 2-4).
            
            data = {};
            data["command"] = "Device.LocalAgent.ControllerTrust.RequestChallenge()";
            data["commandKey"] = "RequestChallenge";
            data["sendResp"] = true;

            data["arguments"] = {};

            var parameters = {};
            parameters["ChallengeRef"] = "Device.LocalAgent.ControllerTrust.Challenge.<PARAMETER SELECTED DURING TEST SETUP>.Value";
            parameters["RequestExpiration"] = 90;

            data["arguments"] = parameters;

            log.debug("OPERATE data: {}", data);
            var resp = yield Operate(data.command, data.commandKey, data.sendResp, data.arguments);
            log.debug("OPERATE Resp: \n{}", resp);

            // ================================ ChallengeResponse() ================================
            // LOOK AT THE OPERATE RESPONSE TO COMPLETE THE CHALLENGE RESPONSE
            // Controller prompts user to provide appropriate response and sends ChallengeResponse().

            data = {};
            data["command"] = "Device.LocalAgent.ControllerTrust.ChallengeResponse()";
            data["commandKey"] = "ChallengeResponse";
            data["sendResp"] = true;

            data["arguments"] = {};

            var parameters = {};
            parameters["ChallengeID"] = "<PARAMETER SELECTED DURING TEST SETUP>";
            parameters["Value"] = "<PARAMETER SELECTED DURING TEST SETUP>";

            data["arguments"] = parameters;

            log.debug("OPERATE data: {}", data);
            var resp = yield Operate(data.command, data.commandKey, data.sendResp, data.arguments);
            log.debug("OPERATE Resp: \n{}", resp);


// ========================================================================================================
            // REPEAST STEPS 1-4
            // 1. Controller does GET message(s) to read everything it is allowed to Get.
            data = {};
            data.parameters = ["Device.DeviceInfo.", "Device.LocalAgent.Subscription."];

            // log the data we're attempting to get
            log.debug("GPV data: {}", data);
            var resp = yield Get(data.parameters);
            log.debug("GPV Resp: \n{}", resp);

            // If GET (Step 1) succeeded, Controller issues SET for something 
            // no-one in their right mind would allow an untrusted Controller to Set, 
            // but that is allowed for other Role.

            data = {};
            data["allowPartialPath"] = true;
            data["basePath"] = "Device.DeviceInfo.SoftwareVersion.";

            data["updateParameters"] = [];
            data["updateParameters"].push({"param":"ID", "value":"TEST", "required":true});
            data["updateParameters"].push({"param":"Enable", "value":"false", "required":true});

            log.debug("SET data: {}", data);
            var resp = yield Set(data.updateParameters, data.basePath, data.allowPartialPath);
            log.debug("SET Resp: \n{}", resp);

            // If GET (Step 1) succeeded, Controller issues ADD for something no-one
            // in their right mind would allow an untrusted Controller to Add, 
            // but that is allowed for other Role.

            data = {};
            data["allowPartialPath"] = true;
            data["basePath"] = "Device.LocalAgent.Subscription.";

            data["parameters"] = [];
            data["parameters"].push({"param":"ID", "value":"TEST", "required":true});
            data["parameters"].push({"param":"Enable", "value":"false", "required":true});

            log.debug("ADD data: {}", data);
            var resp = yield new Add(data.parameters, data.basePath, data.allowPartialPath);
            log.debug("ADD Resp: \n{}", resp);

            // If GET (Step 1) succeeded Controller issues DELETE for something no-one
            // in their right mind would allow an untrusted Controller to Delete, 
            // but that is allowed for other Role.

            data = {};
            data["allowPartialPath"] = true;
            data["parameters"] = ["Device.LocalAgent.Subscription."];

            // log the data we're attempting to get
            log.debug("DELETE data: {}", data);

            var resp = yield Delete(data.parameters, data.allowPartialPath);
            log.debug("DELETE Resp: \n{}", resp);
// ========================================================================================================


            // return 'null' or instance of WorkflowResult to finish
            yield resp;
        }
    }
})();
