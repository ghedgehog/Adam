var opcua = require('node-opcua');
var async = require("async");

var options = {
    securityMode: opcua.MessageSecurityMode.SIGNANDENCRYPT,
    securityPolicy: opcua.SecurityPolicy.Basic128Rsa15,
    connectionStrategy: {
        maxRetry: 1,
        initialDelay: 2000,
        maxDelay: 10 * 1000
    }
}; 

//建立连接并创建会话
exports.createConnection = (ip, port,user,password,callback) => {
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

/* var AddObjectArgs = {
    ParentNodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400001010,2),
    NodeId: new opcua.NodeId(opcua.NodeIdType.STRING,"IEC104TCP",2)
    TypeDefinitionId:new opcua.NodeId(opcua.NodeIdType.NUMERIC,400000301,2),
    BrowseName: "wzj_BrowseName",
    DisplayName: "wzj_displayname",
    Description: "wzj_description"
};*/
//添加对象
exports.AddObject = (session, AddObjectArgs,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.AddObject",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddObjectArgs.ParentNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddObjectArgs.NodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddObjectArgs.TypeDefinitionId}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddObjectArgs.BrowseName}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddObjectArgs.DisplayName}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddObjectArgs.Description})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
}

/* var AddVariableArgs = {
    ParentNodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400001010,2),
    NodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC,33335,2)
    TypeDefinitionId:new opcua.NodeId(opcua.NodeIdType.NUMERIC,400000301,2),
    DataType:new opcua.NodeId(opcua.NodeIdType.NUMERIC,400000301,2),
    ValueRank:-1,
    AccessLevel:15,
    BrowseName: "wzj_BrowseName",
    DisplayName: "wzj_displayname",
    Description: "wzj_description"
};*/
//添加对象
exports.AddVariable = (session, AddVariableArgs,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.AddVariable",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddVariableArgs.ParentNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddVariableArgs.NodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddVariableArgs.TypeDefinitionId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddVariableArgs.DataType}),
            new opcua.Variant({ dataType: opcua.DataType.Int32,  value: AddVariableArgs.ValueRank}),
            new opcua.Variant({ dataType: opcua.DataType.Byte,   value: AddVariableArgs.AccessLevel}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddObjectArgs.BrowseName}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddObjectArgs.DisplayName}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddObjectArgs.Description})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
}

//获取服务端未被占用的NodeId
exports.GetFreeNodeIds = (session,GetFreeNodeIdsArgs,callback)=>{
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.GetFreeNodeIds",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.UInt32, value:GetFreeNodeIdsArgs})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result[0].outputArguments[0].value);
        }
    });
}

