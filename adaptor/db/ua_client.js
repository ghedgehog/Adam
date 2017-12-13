﻿var opcua = require('node-opcua');
var async = require("async");

/* var options = {
    securityMode: opcua.MessageSecurityMode.SIGNANDENCRYPT,
    securityPolicy: opcua.SecurityPolicy.Basic128Rsa15,
    connectionStrategy: {
        maxRetry: 1,
        initialDelay: 2000,
        maxDelay: 10 * 1000
    }
}; */

//建立连接并创建会话
exports.createConnection = (ip, port,user,password,options, callback) => {
    var endpointUrl = "opc.tcp://" + ip + ":" + port;//服务器地址
    var client = new opcua.OPCUAClient(options);
    client.connect(endpointUrl, function (err) {
        if (err) {
            callback("connect to " + endpointUrl + "err:" + err);
        } else {
            client.createSession({ userName: user, password: password }, function (err, session) {
                if (err) {
                    callback("create session failed:" + err);
                } else {
                    callback(null, session);
                }
            });
        }
    });
};

/* var InputArguments = [
    { ParentNodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400001010,2)},
    { NodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC,33335,2)},
    { TypeDefinitionId:new opcua.NodeId(opcua.NodeIdType.NUMERIC,400000301,2)},
    { BrowseName: "wzj_BrowseName" },
    { DisplayName: "wzj_displayname" },
    { Description: "wzj_description" }]; */
//创建对象
exports.AddObject = (session, InputArguments,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.AddObject",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400001010,2) }),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC,33335,2) }),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400000301,2) }),
            new opcua.Variant({ dataType: opcua.DataType.String, value: "wzj_BrowseName" }),
            new opcua.Variant({ dataType: opcua.DataType.String, value: "wzj_BrowseName" }),
            new opcua.Variant({ dataType: opcua.DataType.String, value: "wzj_BrowseName" })]
    }));

    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,"call result:" + result);
        }
    });
}

