/*global require,console,setTimeout */
var opcua = require("node-opcua");
var subscription_service = require("node-opcua").subscription_service;
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
    // step 1 : connect to
    function (callback) {
        client.connect(endpointUrl, function (err) {
            if (err) {
                console.log(" cannot connect to endpoint :", endpointUrl);
            } else {
                console.log("connected !");
            }
            callback(err);
        });
    },
    // step 2 : createSession
    function (callback) {
        client.createSession({ userName: 'admin', password: 'admin' }, function (err, session) {
            if (!err) {
                the_session = session;
            }
            callback(err);
        });
    },
    // step 3: install a subscription and install a monitored item for 10 seconds
    function (callback) {
        the_subscription = new opcua.ClientSubscription(the_session, {
            requestedPublishingInterval: 1000,
            requestedLifetimeCount: 100,
            requestedMaxKeepAliveCount: 2,
            maxNotificationsPerPublish: 10,
            publishingEnabled: true,
            priority: 10
        });

        the_subscription.on("started", function () {
            console.log("subscription started for 2 seconds - subscriptionId=", the_subscription.subscriptionId);
        }).on("keepalive", function () {
            console.log("keepalive");
        }).on("terminated", function () {
            callback();
        });

        // install monitored item
        /* var monitoredItem1 = the_subscription.monitor({
            nodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 151, 2),
            attributeId: opcua.AttributeIds.Value
        },
        {
            samplingInterval: 100,
            discardOldest: false,
            queueSize: 10
        },
        opcua.read_service.TimestampsToReturn.Both
        );
        monitoredItem1.on("changed", function (dataValue) {
            console.log(monitoredItem1.itemToMonitor.nodeId.value+":"+dataValue.value.value);
        });

        var test = opcua.itemToMonitor(the_subscription,{
            nodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 151, 2),
            attributeId: opcua.AttributeIds.Value
        },{
            samplingInterval: 100,
            discardOldest: false,
            queueSize: 10
        },
        opcua.read_service.TimestampsToReturn.Both);

         setTimeout(function () {
            console.log("aaaaaaaaaaaa");
            test.terminate();
        }, 20000); */

    the_session.deleteMonitoredItems(new subscription_service.DeleteMonitoredItemsRequest(), 
    function (err, response) {
        err.message.should.match(/BadSubscriptionIdInvalid/);
        done();
    });
    
    
    },
    // close session
    function (callback) {
        the_session.close(function (err) {
            if (err) {
                console.log("session closed failed ?");
            }
            callback();
        });
    }
],
    function (err) {
        if (err) {
            console.log(" failure ", err);
        } else {
            console.log("done!");
        }
        client.disconnect(function () { });
    });


