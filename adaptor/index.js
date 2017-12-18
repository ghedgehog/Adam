//用一个周期执行的函数检查pg中各张表的更新,然后触发执行创建驱动、创建通道、创建设备、创建变量、创建报警；
var opcua = require("node-opcua");
var uaServiceMethod = require("./comm/uaServiceMethod");
var uaBuildSpace = require("./comm/uaBuildSpace");
var httpClient = require('./comm/httpClient');
var async = require("async");

//全局变量
var the_session = {},the_httpClient = {};
var requestArgs = {
    path: { "path": "localhost:8080" }, //区分restful接口
    parameters: { uaServer: "ioserver" }, //序列化到url中的parameters
    headers: { "Content-Type": "application/json" } //request headers
};

function task() {
    async.auto({
        uaConnect: function (callback) {//连接ua数据库
            uaServiceMethod.createConnection("127.0.0.1", 4841, "admin", "admin", function (err, session) {
                if (err) {
                    callback(err);
                } else {
                    the_session = session;
                    callback(null, "create session success!");
                }
            });
        },
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
        addDriver: ['uaConnect', 'httpClient', function (result, callback) {//创建驱动
            httpClient.getDriverToAdd(the_httpClient, requestArgs, function (err, result) {
                if (err) callback("getDriverToAdd failed: " + err.code);
                else if (result.length != 0) {
                    uaBuildSpace.addDrivers(the_session, result, function (err1, result1) {
                        if (err1) callback(err1);
                        else {
                            callback(null, result1);
                        }
                    });
                } else {
                    callback("there is no driver to add !!!");
                }
            });
        },],
        addChannel: ['httpClient', 'addDriver', function (result, callback) {
            uaBuildSpace.browseIo(the_session, function (err, drivers) {
                if (err) callback(err);
                else if (drivers.length != 0) {
                    var para = {};
                    async.eachSeries(drivers, function (driver, cb) {
                        para.driver = driver.value;
                        requestArgs.parameters = para;
                        httpClient.getChannelToAdd(the_httpClient, requestArgs, function (err1, channels) {
                            if (err1) cb(err1);
                            else if (channels.length != 0) {
                                uaBuildSpace.addChannels(the_session, driver, channels, function (err2, result2) {
                                    if (err2) cb(err2);
                                    else cb();
                                });
                            } else {
                                console.warn("there is no channel below " + driver.value + " !!!");
                                cb();
                            }
                        });
                    }, function (err) {
                        if (err) callback(err);
                        else callback(null, "addChannel success!");
                    });
                } else {
                    callback("there is no driver in db !!!");
                }
            });
        }],
        addDevice: ['httpClient', 'addChannel', function (result, callback) {
            uaBuildSpace.browseAllDrivers(the_session, function (err, channels) {
                if (err) callback(err);
                else if (channels.length != 0) {
                    var para = {};
                    async.eachSeries(channels, function (channel, cb) {
                        para.channelName = channel.value;
                        requestArgs.parameters = para;
                        httpClient.getDeviceToAdd(the_httpClient, requestArgs, function (err1, devices) {
                            if (err1) cb(err1);
                            else if (devices.length != 0) {
                                uaBuildSpace.addDevices(the_session, channel, devices, function (err2, result2) {
                                    if (err2) cb(err2);
                                    else cb();
                                });
                            } else {
                                console.log("there is no device below " + channel.value + " !!!");
                                cb();
                            }
                        });
                    }, function (err) {
                        if (err) callback(err);
                        else callback(null, "addDevice success!");
                    });
                } else {
                    callback("there is no channel in db !!!");
                }
            });
        }],
        addVar: ['httpClient', 'addDevice', function (result, callback) {
            uaBuildSpace.browseAllChannels(the_session, function (err, devices) {
                if (err) callback(err);
                else {
                    var para = {};
                    async.eachSeries(devices, function (device, cb) {
                        para.device = device.value;
                        requestArgs.parameters = para;
                        httpClient.getVarToAdd(the_httpClient, requestArgs, function (err1, Vars) {
                            if (err1) cb(err1);
                            else {
                                uaBuildSpace.addVars(the_session, device, Vars, function (err2, result2) {
                                    if (err2) cb(err2);
                                    else cb();
                                });
                            }
                        });
                    }, function (err) {
                        if (err) callback(err);
                        else callback(null, "addVar success!");
                    });
                }
            });
        }],       
        addAlarmObj:['addVar',function(result,callback){
            httpClient.getAlarmObjToAdd(the_httpClient,requestArgs,function(err,alarmObjsToAdd){
                if(err) callback(err);
                else if(alarmObjsToAdd!=0){
                     alarmObjsToAdd = [
                        {type:"OnAlarmType",
                        name:"Alarm1",
                        conf:"<Values><Severity>300</Severity><Message>On</Message></Values>"},
                        {type:"ExclusiveDeviationAlarmType",
                        name:"Alarm2",
                        conf:"<Values><Severity>300</Severity></Values>"}];
                        uaBuildSpace.addAlarmObj(the_session,alarmObjsToAdd,function(err1,result1){
                            if(err1) callback(err1);
                            else callback(null,result1);
                        });
                }else{
                    callback("there is no alarmObj to add !!!");
                }
            });
        }]/* ,
        varAlarmConf:['addAlarmObj',function(result,callback){
            uaBuildSpace.browseAllDevices(the_session, function (err, vars) {
                if (err) callback(err);
                else {
                    var para = {};
                    async.eachSeries(vars, function (var1, cb) {
                        para.device = device.value;
                        requestArgs.parameters = para;
                        httpClient.getVarToAdd(the_httpClient, requestArgs, function (err1, Vars) {
                            if (err1) cb(err1);
                            else {
                                uaBuildSpace.addVars(the_session, device, Vars, function (err2, result2) {
                                    if (err2) cb(err2);
                                    else cb();
                                });
                            }
                        });
                    }, function (err) {
                        if (err) callback(err);
                        else callback(null, "addVar success!");
                    });
                }
            });
        }] */
    }, function (err, results) {
        if (err) {
            console.log(err);
            setTimeout(task, 3000);
        } else {
            console.log(results);
        }
    });
}

task();