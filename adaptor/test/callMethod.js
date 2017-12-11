var opcua = require('node-opcua');

var the_session;
var options = {
    securityMode: opcua.MessageSecurityMode.SIGNANDENCRYPT,
    securityPolicy: opcua.SecurityPolicy.Basic128Rsa15,
    connectionStrategy: {
        maxRetry: 1,
        initialDelay: 2000,
        maxDelay: 10 * 1000
    }
};

//建立连接并创建会话session
function createConnection(ipAddress, port, user, password, callback) {
    var endpointUrl = "opc.tcp://" + ipAddress + ":" + port;//服务器地址
    var client = new opcua.OPCUAClient(options);
    client.connect(endpointUrl, function (err) {
        if (err) {
            callback("create connect failed:");
        } else {
            client.createSession({ userName: user, password: password }, function (err, session) {
                if (!err) {
                    the_session = session;
                    callback(null, "create session success");
                } else {
                    callback("create session failed:", null);
                }
            });
        }
    });
}

function call_GetPreStatisticsValues() {
    var methodToCalls = [];
    methodToCalls.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.GetPreStatisticsValues",
        inputArguments: [new opcua.Variant({ dataType: "XmlElement", value: "<InputArguments><StatisticsConfig><startTime>2017-06-11T09:00:00+08:00</startTime><endTime>2017-06-20T09:00:00+08:00</endTime><EN_StatisticsType>21</EN_StatisticsType><EN_StatisticsFun>4</EN_StatisticsFun><EN_RateType>12</EN_RateType><RateValue>1</RateValue><EN_TimeType>13</EN_TimeType><startValue>1</startValue><endValue>23</endValue></StatisticsConfig><nodesIdToRead><nodeId><NamespaceIndex>2</NamespaceIndex><IdentifierType>0</IdentifierType><Identifier>400001070</Identifier></nodeId><nodeId><NamespaceIndex>2</NamespaceIndex><IdentifierType>0</IdentifierType><Identifier>1116</Identifier></nodeId></nodesIdToRead><nodesIdToGroup><nodeId><NamespaceIndex>2</NamespaceIndex><IdentifierType>0</IdentifierType><Identifier>1119</Identifier></nodeId></nodesIdToGroup></InputArguments>" })]
    }));
    the_session.call(methodToCalls, function (err, result) {
        if (err) {
            console.log(JSON.stringify(err));
        } else {
            console.log(JSON.stringify(result));
        }
    });
}

function call_AddObject() {
    var methodToCalls = [];
    methodToCalls.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.AddObject",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400001010, 2) }),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 333335, 2) }),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000301, 2) }),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: "wzj_browsename" }),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: "wzj_displayname" }),
            new opcua.Variant({ dataType: opcua.DataType.NodeId, value: "wzj_description" })]
    }));
    the_session.call(methodToCalls, function (err, result) {
        if (err) {
            console.log(JSON.stringify(err));
        } else {
            console.log(JSON.stringify(result));
        }
    });
}

function call_GetFreeNodeIds() {
    var methodToCalls = [];
    methodToCalls.push(new opcua.call_service.CallMethodRequest({
        objectId: "ns=2;i=400001511",
        methodId: "ns=2;s=400001511.GetFreeNodeIds",
        inputArguments: [
            new opcua.Variant({ dataType: opcua.DataType.UInt32, value: 2 })]
    }));
    the_session.call(methodToCalls, function (err, result) {
        if (err) {
            console.log(JSON.stringify(err));
        } else {
            console.log(JSON.stringify(result));
        }
    });
}

createConnection("127.0.0.1", 4841, "admin", "admin", function (err, result) {
    if (result) {
        console.log(result);
        call_GetFreeNodeIds();
    } else {
        console.log(err);
    }
});