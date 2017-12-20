var opcua = require("node-opcua");
var uaServiceMethod = require("./comm/uaServiceMethod");
var uaBuildSpace = require("./comm/uaBuildSpace");
var httpClient = require('./comm/httpClient');
var async = require("async");
var interface = require('./interface');
var Redis = require('ioredis');
var config = require('./config/config');
var redisClient_sub = new Redis();
var redisClient_set = new Redis();

var the_session = {},                               //ua会话句柄
    the_httpClient = {};                            //datahub链接句柄
var requestArgs = {
    path: { "path": config.path },                  //区分restful接口
    parameters: { uaServer: config.uaServer },      //序列化到url中的parameters
    headers: { "Content-Type": "application/json" } //request headers
};

function init(done) {
    async.auto({
        //链接ua数据库并创建会话句柄
        uaConnect: function (callback) {
            uaServiceMethod.createConnection("127.0.0.1", 4841, "admin", "admin", function (err, session) {
                if (err) {
                    callback(err);
                } else {
                    the_session = session;
                    callback(null, "create session success!");
                }
            });
        },
        //注册访问datahub方法并获得句柄
        httpClient: function (callback) {
            httpClient.getHttpClient(function (err, result) {
                if (err) {
                    callback(err);
                } else {
                    the_httpClient = result[0]
                    callback(null, "get httpclient success!");
                }
            });
        },
        //往ua数据库中添加驱动
        addDriver: ['uaConnect', 'httpClient', function (result, callback) {
            interface.addDriver(the_session, the_httpClient, requestArgs, callback);
        }],
        //往ua数据库中添加通道
        addChannel: ['httpClient', 'addDriver', function (result, callback) {
            interface.addChannel(the_session, the_httpClient, requestArgs, callback);
        }],
        //往ua数据库中添加设备
        addDevice: ['httpClient', 'addChannel', function (result, callback) {
            interface.addDevice(the_session, the_httpClient, requestArgs, callback);
        }],
        //往ua数据库中添加变量
        addVar: ['httpClient', 'addDevice', function (result, callback) {
            interface.addVar(the_session, the_httpClient, requestArgs, callback);
        }],
        //往ua数据库中添加报警对象
        addAlarmObj: ['addVar', function (result, callback) {
            interface.addAlarmObj(the_session, the_httpClient, requestArgs, callback);
        }]/* ,
        //为ua数据库中变量配置报警
        varAlarmConf: ['addAlarmObj', function (result, callback) {
            interface.varAlarmConf(the_session,the_httpClient,requestArgs,callback);
        }] */
    }, function (err, results) {
        if (err) {
            console.log(err);
            setTimeout(init, 3000);
        } else {
            console.log(results);
            done();
        }
    });
}

function subUaRealData(done) {
    var the_subscription = new opcua.ClientSubscription(the_session, {
        requestedPublishingInterval: 1000,
        requestedLifetimeCount: 100,
        requestedMaxKeepAliveCount: 2,
        maxNotificationsPerPublish: 10,
        publishingEnabled: true,
        priority: 10
    });
    var requestedParameters = {
        samplingInterval: 100,
        discardOldest: true,
        queueSize: 10
    };
    var itemToMonitor = {
        nodeId: {},
        attributeId: opcua.AttributeIds.Value
    };
    uaBuildSpace.browseAllDevices(the_session, function (err, varsNodeId) {
            varsNodeId.forEach(function (varNodeId) {
                itemToMonitor.nodeId = varNodeId;
                the_subscription.monitor(itemToMonitor, requestedParameters, opcua.read_service.TimestampsToReturn.Neither)
                    .on("changed", function (dataValue) {
                        console.log(varNodeId.value, dataValue.value.value);
                        redisClient_set.set(varNodeId.value, dataValue.value.value);
                    });
            });
    });
}

function subUaRealAlarm(done) {
}

function subDataHubConfig(done) {
    redisClient_sub.subscribe('adam');
    redisClient_sub.on('message', function (channel, message) {
        console.log(message);
        switch (message) {
            case 'Driver_Add'://增加驱动
                {
                    var para = {};
                    para.uaServer = config.uaServer;
                    requestArgs.parameters = para;
                    interface.addDriver(the_session, the_httpClient, requestArgs, function () { });
                    break;
                }
            case 'Driver_Del'://删除驱动
                {
                    var para = {};
                    para.uaServer = config.uaServer;
                    requestArgs.parameters = para;
                    interface.delDriver(the_session, the_httpClient, requestArgs, function () { });
                    break;
                }
            case 'Channel_Add'://增加通道
                {
                    interface.addChannel(the_session, the_httpClient, requestArgs, done);
                    break;
                }
            case 'Channel_Del'://删除通道
            {
                interface.delChannel(the_session, the_httpClient, requestArgs, done);
                break;
            }
            case 'Device_Add'://增加设备
                {
                    interface.addDevice(the_session, the_httpClient, requestArgs, done);
                    break;
                }
            case 'Device_Del'://删除设备
                break;
            case 'Variable_Add'://增加变量
                {
                    interface.addVar(the_session, the_httpClient, requestArgs, done);
                    break;
                }
            case 'Variable_Del'://删除变量
                break;
            default:
        }
    });
}

async.auto({
    init: function (done) {
        init(done);
    },
    subDataHubConfig: ['init', function (done) {
        subDataHubConfig(done);
    }],
    subUaRealData: ['init', function (done) {
        subUaRealData(done);
    }],
    subUaRealAlarm: ['init', function (done) {
        subUaRealAlarm(done);
    }]
}, function (err) {
    console.log(err);
});
