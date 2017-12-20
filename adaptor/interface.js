var opcua = require("node-opcua");
var uaServiceMethod = require("./comm/uaServiceMethod");
var uaBuildSpace = require("./comm/uaBuildSpace");
var httpClient = require('./comm/httpClient');
var async = require("async");

exports.addDriver = function (the_session, the_httpClient, requestArgs, callback) {
    httpClient.getDriverToAdd(the_httpClient, requestArgs, function (err, result) {
        if (err) callback("getDriverToAdd failed: " + err.code);
        else if (result.length > 0) {
            uaBuildSpace.addDrivers(the_session, result, function (err1, result1) {
                if (err1) callback(err1);
                else {
                    callback(null, result1);
                }
            });
        } else {
            console.log("there is no driver to add !!!");
            callback(null,"there is no driver to add !!!");
        }
    });
};

exports.delDriver = function (the_session, the_httpClient, requestArgs, callback) {
    httpClient.getDriverToDel(the_httpClient, requestArgs, function (err, result) {
        if (err) callback("getDriverToDel failed: " + err.code);
        else if (result.length > 0) {
            uaBuildSpace.delDrivers(the_session, result, function (err1, result1) {
                if (err1) callback(err1);
                else {
                    callback(null, result1);
                }
            });
        } else {
            console.log("there is no driver to del !!!");
            callback(null,"there is no driver to del !!!");
        }
    });
};

exports.addChannel = function (the_session, the_httpClient, requestArgs, callback) {
    uaBuildSpace.browseIo(the_session, function (err, drivers) {
        if (err) callback(err);
        else if (drivers.length > 0) {
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
                        console.log("there is no channel below " + driver.value + " !!!");
                        cb();
                    }
                });
            }, function (err) {
                if (err) callback(err);
                else callback(null, "addChannel success!");
            });
        } else {
            console.log("there is no driver in db !!!");
            callback(null,"there is no driver in db !!!");
        }
    });
};

exports.delChannel = function (the_session, the_httpClient, requestArgs, callback) {
    uaBuildSpace.browseIo(the_session, function (err, drivers) {
        if (err) callback(err);
        else if (drivers.length > 0) {
            var para = {};
            async.eachSeries(drivers, function (driver, cb) {
                para.driver = driver.value;
                requestArgs.parameters = para;
                httpClient.getChannelToDel(the_httpClient, requestArgs, function (err1, channels) {
                    if (err1) cb(err1);
                    else if (channels.length != 0) {
                        uaBuildSpace.delChannels(the_session, driver, channels, function (err2, result2) {
                            if (err2) cb(err2);
                            else cb();
                        });
                    } else {
                        console.log("there is no channel below " + driver.value + " !!!");
                        cb();
                    }
                });
            }, function (err) {
                if (err) callback(err);
                else callback(null, "addChannel success!");
            });
        } else {
            console.log("there is no driver in db !!!");
            callback(null,"there is no driver in db !!!");
        }
    });
};

exports.addDevice = function (the_session, the_httpClient, requestArgs, callback) {
    uaBuildSpace.browseAllDrivers(the_session, function (err, channels) {
        if (err) callback(err);
        else if (channels.length > 0) {
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
            console.log("there is no channel in db !!!");
            callback(null,"there is no channel in db !!!");
        }
    });
};

exports.addVar = function (the_session, the_httpClient, requestArgs, callback) {
    uaBuildSpace.browseAllChannels(the_session, function (err, devices) {
        if (err) callback(err);
        else if(devices.length>0){
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
        }else{
            console.log("there is no device in db !!");
            callback(null,"there is no device in db !!");
        }
    });
};

exports.addAlarmObj = function (the_session, the_httpClient, requestArgs, callback) {
    httpClient.getAlarmObjToAdd(the_httpClient, requestArgs, function (err, alarmObjsToAdd) {
        if (err) callback(err);
        else if (alarmObjsToAdd.length > 0) {
            uaBuildSpace.addAlarmObj(the_session, alarmObjsToAdd, function (err1, result1) {
                if (err1) callback(err1);
                else callback(null, result1);
            });
        } else {
            callback(null, "there is no alarmObj to add !!!");
        }
    });
};

exports.varAlarmConf = function (the_session, the_httpClient, requestArgs, callback) {
    uaBuildSpace.browseAllDevices(the_session, function (err, vars) {
        if (err) callback(err);
        else if (vars.length > 0) {
            var para = {};
            async.eachSeries(vars, function (var1, cb) {
                para.var = var1.value.split(".")[3];
                requestArgs.parameters = para;
                httpClient.getVarAlarmConf(the_httpClient, requestArgs, function (err1, alarmObjs) {
                    if (err1) cb(err1);
                    else if (alarmObjs.length > 0) {
                        uaBuildSpace.varAlarmConf(the_session, var1, alarmObjs, function (err2, result2) {
                            if (err2) cb(err2);
                            else cb();
                        });
                    } else cb();
                });
            }, function (err) {
                if (err) callback(err);
                else callback(null, "varAlarmConf success !");
            });
        } else {
            callback(null, "there is no var in db !");
        }
    });
};