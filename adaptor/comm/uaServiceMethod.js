﻿var opcua = require('node-opcua');
var async = require("async");

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
};

//设置对象属性
exports.SetObjectProperty = (session, objNodeId,conf,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.SetObjectProperty",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: objNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.XmlElement, value: conf})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};

//删除对象 
exports.DeleteObject = (session, DeleteObjectNodeId,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.DeleteObject",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: DeleteObjectNodeId})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};

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
//添加变量
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
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddVariableArgs.BrowseName}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddVariableArgs.DisplayName}),
            new opcua.Variant({ dataType: opcua.DataType.String, value: AddVariableArgs.Description})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};

//设置变量属性 
exports.SetVariableProperty = (session, varNodeId,propConf,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.SetVariableProperty",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: varNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.XmlElement, value: propConf})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};

//删除变量 
exports.DeleteVariable = (session, DeleteVariableArgs,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.DeleteVariable",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: DeleteVariableArgs.NodeId})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};

//添加引用关系
exports.AddReference = (session, AddReferenceArgs,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.AddReference",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddReferenceArgs.SourceNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddReferenceArgs.TargetNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: AddReferenceArgs.ReferenceType})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};

//删除引用关系 
exports.DeleteReference = (session, DeleteReferenceArgs,callback) => {
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.DeleteReference",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: DeleteReferenceArgs.SourceNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: DeleteReferenceArgs.TargetNodeId}),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: DeleteReferenceArgs.ReferenceType})]
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};

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

exports.Commit = (session,driverName,callback)=>{
    var methodsToCall = [];
    methodsToCall.push(new opcua.call_service.CallMethodRequest({
        objectId: new opcua.NodeId(opcua.NodeIdType.STRING, driverName,2),
        methodId: new opcua.NodeId(opcua.NodeIdType.STRING, driverName+'.Commit',2)
    }));
    session.call(methodsToCall, function (err, result) {
        if (err) {
            callback("err:" + err);
        } else {
            callback(null,result);
        }
    });
};
