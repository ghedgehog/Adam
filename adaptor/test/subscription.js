var opcua = require('node-opcua');
var assert = require("node-opcua-assert");
var subscription = require('node-opcua-service-subscription');
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
var client = new opcua.OPCUAClient(options);
var endpointUrl = "opc.tcp://127.0.0.1:4841";
var the_session, the_subscription;

async.series([
    function (callback) {//连接uaServer
        client.connect(endpointUrl, function (err) {
            if (err) console.log(" cannot connect to endpoint :", endpointUrl);
            else console.log("connected !");
            callback(err);
        });
    },
    function (callback) {//创建会话层
        client.createSession({ userName: 'admin', password: 'admin' }, function (err, session) {
            if (!err) the_session = session;
            callback(err);
        });
    },
    function (callback) {
        the_subscription = new opcua.ClientSubscription(the_session, {
            requestedPublishingInterval: 1000,
            requestedLifetimeCount: 100,
            requestedMaxKeepAliveCount: 2,
            maxNotificationsPerPublish: 10,
            publishingEnabled: true,
            priority: 10
        });

        var filter =  new subscription.EventFilter({
            selectClauses: [{ typeId: new opcua.NodeId(opcua.NodeIdType.STRING,'ExclusiveLevelAlarmType',2), 
                             browsePath: [ {name: 'ActiveState'}, {name: 'id'},{namespaceIndex:2,name:'ExclusiveLevelAlarmType'} ]},
                     { browsePath: [ {name: 'ConditionName'}               ]}
            ],     whereClause: [] });
          the_subscription.monitor(
             {
               nodeId: "ns=2;i=ModbusTcpClient.channel1.Blower",
               attributeId: AttributeIds.EventNotifier,
               indexRange: null,
               dataEncoding: { namespaceIndex: 0, name: null }
             },
             {
                samplingInterval: 100,
                discardOldest: true,
                queueSize: 10
             },
             TimestampToReturn.sourceTimestamp
           ).on('ExclusiveLevelAlarmType', function (dataValue) {
            console.log( dataValue);
        });
    },
    function (callback) {
        the_session.close(function (err) {
            if (err) console.log("session closed failed ?");
            callback();
        });
    }],
    function (err) {
        if (err) console.log(" failure ", err);
        else console.log("done!");
        client.disconnect(function () { });
    });


