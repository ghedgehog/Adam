var opcua = require("node-opcua");
var async = require("async");
var log = require('./log/log4js_config.js').getLogger();
var uaServiceMethod = require("./comm/uaServiceMethod");
var uaBuildSpace = require("./comm/uaBuildSpace");
var httpClient = require('./comm/httpClient');
var interface = require('./comm/interface');
var config = require('./config/config');
var Redis = require('ioredis');
var redisClient_sub = new Redis(config.redis);
var redisClient_set = new Redis(config.redis);

var the_session = {};    //ua会话句柄
var the_httpClient = {}; //datahub链接句柄
var requestArgs = {
    path: { 'path': config.dataHubPath  },          //区分restful接口
    parameters: { uaServer: config.uaSever.name },  //序列化到url中的parameters
    headers: {"Content-Type": "application/json"}   //request headers
};
var options = {
    securityMode: opcua.MessageSecurityMode.SIGNANDENCRYPT,
    securityPolicy: opcua.SecurityPolicy.Basic128Rsa15,
    requestedSessionTimeout: 1000,
    connectionStrategy: { maxRetry: 1, initialDelay: 2000, maxDelay: 10 * 1000 }
};

function init(cb) {
    async.auto({
        uaConnect: function (callback) { //链接ua数据库并创建会话句柄
            var uaInfo = config.uaSever;
            var endpointUrl = "opc.tcp://" + uaInfo.ip + ":" + uaInfo.port;//服务器地址
            var client = new opcua.OPCUAClient(options);
            client.connect(endpointUrl, function (err) {
                if (err) callback("connect to " + endpointUrl + "err:" + err);
                else {
                    client.on('close', function () { 
                        log.warn('connection closed!!!');
                        main(); 
                    });
                    client.createSession({ userName: uaInfo.user, password: uaInfo.password }, function (err, session) {
                        if (err) callback("create session failed:" + err);
                        else {
                            the_session = session;
                            the_session.on('session_closed',function(){
                                log.warn('session closed!!!');
                                main();
                            });
                            callback(null, "create session success!");
                        }
                    });
                }
            });
        },
        //注册访问datahub的webservice方法并获得句柄
        httpClient: function (callback) {
            httpClient.registerMethod(function (err, result) {
                if (err) {
                    callback(err);
                } else {
                    the_httpClient = result[0];
                    callback(null, " register http method success!");
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
        }]/*,
        //为ua数据库中变量配置报警
        varAlarmConf: ['addAlarmObj', function (result, callback) {
            interface.varAlarmConf(the_session, the_httpClient, requestArgs, callback);
        }] */
    }, function (err, results) {
        if (err) {
            log.error(err);
            setTimeout(init, 3000, cb);
        } else {
            cb(null, results);
        }
    });
}

var mesBuffer = [];
function subDataHubConfig() {
    redisClient_sub.subscribe(config.subChannel);
    redisClient_sub.on('message', function (channel, message) {
        log.info('subDataHubConfig:', message);
        mesBuffer.push(message);
        dealSubMes();
    });
}

function dealSubMes() {
    var para = {};
    var message = mesBuffer.shift();
    switch (message) {
        case '\"Driver_Add\"': //增加驱动
            {
                para = {};
                para.uaServer = config.uaSever.name;
                requestArgs.parameters = para;
                interface.addDriver(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else log.info(result);
                });
                break;
            }
        case '\"Driver_Del\"': //删除驱动
            {
                para = {};
                para.uaServer = config.uaServer.name;
                requestArgs.parameters = para;
                interface.delDriver(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else log.info(result);
                });
                break;
            }
        case '\"Channel_Add\"': //增加通道
            {
                interface.addChannel(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else log.info(result);
                });
                break;
            }
        case '\"Channel_Del\"': //删除通道
            {
                interface.delChannel(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else log.info(result);
                });
                break;
            }
        case '\"Device_Add\"': //增加设备
            {
                interface.addDevice(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else log.info(result);
                });
                break;
            }
        case '\"Device_Del\"': //删除设备
            {
                interface.addDevice(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else log.info(result);
                });
                break;
            }
        case '\"Variable_Add\"': //增加变量
            {
                interface.addVar(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else {
                        subUaRealData(function (err, result) {
                            if (err) log.error(err);
                            else log.info(result);
                        });
                    }
                });
                break;
            }
        case '\"Variable_Del\"': //删除变量
            {
                interface.delVar(the_session, the_httpClient, requestArgs, function (err, result) {
                    if (err) log.error(err);
                    else log.info(result);
                });
                break;
            }
        default:
            break;
    }
}

function subUaRealData(cb) {
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
    var resData = {};
    uaBuildSpace.browseAllDevices(the_session, function (err, varsNodeId) {
        varsNodeId.forEach(function (varNodeId) {
            itemToMonitor.nodeId = varNodeId;
            the_subscription.monitor(itemToMonitor, requestedParameters, opcua.read_service.TimestampsToReturn.Source)
                .on('changed', function (dataValue) {
                    resData.value = dataValue.value.value;//实时值
                    resData.sourceTimestamp = new Date(dataValue.sourceTimestamp);//时间戳
                    resData.statusCode = dataValue.statusCode.name;//质量戳
                    log.trace(varNodeId.value, resData);
                    redisClient_set.set(varNodeId.value, dataValue.value.value);
                })
                .on('error', function () {
                    log.error('subUaRealData error');
                });
        });
        cb(null, 'subUaRealData sucess !!!');
    });
}

function subUaRealAlarm(cb) {
    cb();
}

function main() {
    async.auto({
        init: function (cb) {
            init(cb);
        },
        subDataHubConfig: ['init', function (result, cb) {
            subDataHubConfig();
        }],
        subUaRealData: ['init', function (result, cb) {
            subUaRealData(cb);
        }],
        subUaRealAlarm: ['init', function (result, cb) {
            subUaRealAlarm(cb);
        }]
    }, function (err, result) {
        if (err) {
            log.error(err);
        } else {
            log.info(result);
        }
    });
}

main();