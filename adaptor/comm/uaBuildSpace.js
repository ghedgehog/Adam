var opcua = require("node-opcua");
var uaServiceMethod = require("./uaServiceMethod");
var async = require('async');
var ioDriverRoot = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400001010, 2);
var ioDriverType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000301, 2);
var ioChannelType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000310, 2);
var ioDeviceTYpe = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000330, 2);
var ioProxyVariableType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000350, 2);
var BaseDataVariableType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 63, 0);
var Organizes = new opcua.NodeId(opcua.NodeIdType.NUMERIC, opcua.ReferenceTypeIds.Organizes, 0);
var HasCondition = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 9006, 0);                      /*变量和报警对象之间的引用关系*/

var dbAlarmConfigRoot = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400001113, 2);            /*报警配置根目录*/
var alarmType = {
    OnAlarmType: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000700, 2),                   /*开报警*/
    OffAlarmType: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000701, 2),                  /*关报警*/
    ExclusiveRateOfChangeAlarmType: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000704, 2),/*单一变化率报警*/
    NonExclusiveRateOfChangeAlarm: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000700, 2), /*非单一变化率报警*/
    ExclusiveLevelAlarmType: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000703, 2),       /*单一限值报警*/
    NonExclusiveLevelAlarmType: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000705, 2),    /*非单一限值报警*/
    ExclusiveDeviationAlarmType: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000702, 2),   /*单一偏差报警*/
    NonExclusiveDeviationAlarm: new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000706, 2)     /*非单一偏差报警*/
};

function addDrivers(the_session, driversToAdd, callback) {
    var AddObjectArgs = {};
    async.eachSeries(driversToAdd, function (driver, cb) {
        AddObjectArgs.ParentNodeId = ioDriverRoot;
        AddObjectArgs.NodeId = new opcua.NodeId(opcua.NodeIdType.STRING, driver.name, 2);
        AddObjectArgs.TypeDefinitionId = ioDriverType;
        AddObjectArgs.BrowseName = driver.name;
        AddObjectArgs.DisplayName = driver.name;
        AddObjectArgs.Description = driver.name;
        uaServiceMethod.AddObject(the_session, AddObjectArgs, function (err, result) {
            if (err) cb(err);
            else cb();
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "addDriver success!");
    });
}

function delDrivers(the_session, driversToAdd, callback) {
    var nodeId = {};
    async.eachSeries(driversToAdd, function (driver, cb) {
        nodeId = new opcua.NodeId(opcua.NodeIdType.STRING, driver.name, 2);
        uaServiceMethod.DeleteObject(the_session, nodeId, function (err, result) {
            if (err) cb(err);
            else cb();
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "delDriver success!");
    });
}

function addChannels(the_session, driverNodeId, channelsToAdd, callback) {
    var AddObjectArgs = {};
    async.eachSeries(channelsToAdd, function (channel, cb) {
        AddObjectArgs.ParentNodeId = driverNodeId;
        AddObjectArgs.NodeId = new opcua.NodeId(opcua.NodeIdType.STRING, channel.name, 2);
        AddObjectArgs.TypeDefinitionId = ioChannelType;
        AddObjectArgs.BrowseName = channel.name.split('.')[1];
        AddObjectArgs.DisplayName = channel.name.split('.')[1];
        AddObjectArgs.Description = channel.name;
        uaServiceMethod.AddObject(the_session, AddObjectArgs, function (err, result) {
            if (err) cb(err);
            else cb();
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "addChannel success!");
    });
}

function delChannels(the_session, driverNodeId, channelsToAdd, callback) {
    var NodeId = {};
    async.eachSeries(channelsToAdd, function (channel, cb) {
     NodeId = new opcua.NodeId(opcua.NodeIdType.STRING, channel.name, 2);
        uaServiceMethod.DeleteObject(the_session, NodeId, function (err, result) {
            if (err) cb(err);
            else cb();
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "delChannel success!");
    });
}

function addDevices(the_session, channelNodeId, devicesToAdd, callback) {
    var AddObjectArgs = {};
    async.eachSeries(devicesToAdd, function (device, cb) {
        AddObjectArgs.ParentNodeId = channelNodeId;
        AddObjectArgs.NodeId = new opcua.NodeId(opcua.NodeIdType.STRING, device.name, 2);
        AddObjectArgs.TypeDefinitionId = ioDeviceTYpe;
        AddObjectArgs.BrowseName = device.name.split('.')[2];
        AddObjectArgs.DisplayName = device.name.split('.')[2];
        AddObjectArgs.Description = device.name;
        uaServiceMethod.AddObject(the_session, AddObjectArgs, function (err, result) {
            if (err) cb(err);
            else cb();
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "addChannel success!");
    });
}

//添加NodeId为STRING的长点名var;
  function addVars(the_session, deviceNodeId, VarsToAdd, callback) {
    var AddVarArgs = {};
    async.eachSeries(VarsToAdd, function (Var, cb) {
        AddVarArgs.ParentNodeId = deviceNodeId;
        AddVarArgs.NodeId = new opcua.NodeId(opcua.NodeIdType.STRING, deviceNodeId.value + '.' + Var.name, 2);
        AddVarArgs.TypeDefinitionId = BaseDataVariableType;
        AddVarArgs.DataType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 11, 0);
        AddVarArgs.ValueRank = -1;
        AddVarArgs.AccessLevel = 15;
        AddVarArgs.BrowseName = Var.name;
        AddVarArgs.DisplayName = Var.name;
        AddVarArgs.Description = Var.description;
        uaServiceMethod.AddVariable(the_session, AddVarArgs, function (err, result) {
            if (err) cb(err);
            else {
                uaServiceMethod.SetVariableProperty(the_session, AddVarArgs.NodeId, Var.propConf, function (err1, result1) {
                    if (err1) cb(err1);
                    else cb();
                });
            }
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "addVar success!");
    });
} 

//添加NUMERIC的var
/* function addVars(the_session, deviceNodeId, VarsToAdd, callback) {
    uaServiceMethod.GetFreeNodeIds(the_session, VarsToAdd.length, function (err, nodesId) {
        if (err) {
            callback(err);
        } else {
            var AddVarArgs = {},index=0;
            async.eachSeries(VarsToAdd, function (Var, cb) {
                AddVarArgs.ParentNodeId = deviceNodeId;
                AddVarArgs.NodeId = nodesId[index];
                index++;
                AddVarArgs.TypeDefinitionId = BaseDataVariableType;
                AddVarArgs.DataType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 11, 0);
                AddVarArgs.ValueRank = -1;
                AddVarArgs.AccessLevel = 15;
                AddVarArgs.BrowseName = Var.name;
                AddVarArgs.DisplayName = Var.name;
                AddVarArgs.Description = Var.description;
                uaServiceMethod.AddVariable(the_session, AddVarArgs, function (err, result) {
                    if (err) cb(err);
                    else {
                        uaServiceMethod.SetVariableProperty(the_session,AddVarArgs.NodeId,Var.propConf,function(err1,result1){
                            if(err1) cb(err1);
                            else cb();
                        });
                    }
                });
            }, function (err) {
                if (err) callback(err);
                else callback(null, "addVar success!");
            });
        }});
}  */
/* var alarmObjsToAdd = [{type:"OnAlarmType",
                        name:"Alarm1",
                        conf:"<Values></Values>"},
                        {type:"ExclusiveDeviationAlarmType",
                        name:"Alarm2",
                        conf:"<Values></Values>"}];*/
//添加报警对象
function addAlarmObj(the_session, alarmObjsToAdd, callback) {
    var AddAlarmObjArgs = {};
    async.eachSeries(alarmObjsToAdd, function (alarmObj, cb) {
        AddAlarmObjArgs.ParentNodeId = dbAlarmConfigRoot;
        AddAlarmObjArgs.NodeId = new opcua.NodeId(opcua.NodeIdType.STRING, alarmObj.name, 2);
        AddAlarmObjArgs.TypeDefinitionId = alarmType[alarmObj.type];
        AddAlarmObjArgs.BrowseName = alarmObj.name;
        AddAlarmObjArgs.DisplayName = alarmObj.name;
        AddAlarmObjArgs.Description = alarmObj.name;
        uaServiceMethod.AddObject(the_session, AddAlarmObjArgs, function (err, result) {
            if (err) cb(err);
            else {
                uaServiceMethod.SetObjectProperty(the_session, AddAlarmObjArgs.NodeId, alarmObj.conf, function (err1, result1) {
                    if (err1) cb(err1);
                    else cb();
                });
            }
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "addAlarmObj success!");
    });
}

//配置变量报警
function varAlarmConf(the_session, varNodeId, alarmObjs, callback) {
    var varAlarmConfArgs = {};
    async.eachSeries(alarmObjs, function (alarmObj, cb) {
        varAlarmConfArgs.SourceNodeId = varNodeId;
        varAlarmConfArgs.TargetNodeId = new opcua.NodeId(opcua.NodeIdType.STRING, alarmObj.name, 2);
        varAlarmConfArgs.ReferenceType = HasCondition;
        uaServiceMethod.AddReference(the_session, varAlarmConfArgs, function (err, result) {
            if (err) cb(err);
            else cb();
        });
    }, function (err) {
        if (err) callback(err);
        else callback(null, "addAlarmObj success!");
    });
}

function browseIo(the_session, callback) {
    var browseDescription = {
        nodeId: ioDriverRoot,
        referenceTypeId: Organizes,
        browseDirection: opcua.BrowseDirection.Forward,
        includeSubtypes: true,
        nodeClassMask: 0,
        resultMask: 63
    };
    var driversNodeId = [];
    the_session.browse(browseDescription, function (err, browse_result) {
        if (err) {
            callback(err);
        } else {
            async.eachSeries(browse_result[0].references, function (reference, cb) {
                driversNodeId.push(reference.nodeId);
                cb();
            }, function (err) {
                if (err) callback(err);
                else callback(null, driversNodeId);
            });
        }
    });
}

function browseDriver(the_session, driverNodeId, callback) {
    var browseDescription = {
        nodeId: driverNodeId,
        referenceTypeId: Organizes,
        browseDirection: opcua.BrowseDirection.Forward,
        includeSubtypes: true,
        nodeClassMask: 0,
        resultMask: 63
    }
    var channelsNodeId = [];
    the_session.browse(browseDescription, function (err, browse_result) {
        if (err) {
            callback(err);
        } else {
            async.eachSeries(browse_result[0].references, function (reference, cb) {
                channelsNodeId.push(reference.nodeId);
                cb();
            }, function (err) {
                if (err) callback(err);
                else callback(null, channelsNodeId);
            });
        }
    });
}

function browseAllDrivers(the_session, callback) {
    async.waterfall([
        function (cb_waterfall) {//遍历IO
            browseIo(the_session, function (err, drivers) {
                if (err) cb_waterfall(err);
                else {
                    cb_waterfall(null, drivers);
                }
            });
        },
        function (drivers, cb_waterfall) {//遍历驱动
            var channelsNodeId = [];
            async.eachSeries(drivers, function (driver, cb_eachSeries1) {
                browseDriver(the_session, driver, function (err, channels) {
                    if (err) cb_eachSeries1(err);
                    else if (channels.length != 0) {
                        async.eachSeries(channels, function (channel, cb_eachSeries2) {
                            channelsNodeId.push(channel);
                            cb_eachSeries2();
                        }, function (err) {
                            if (!err) cb_eachSeries1();
                        });
                    } else cb_eachSeries1();
                });
            }, function (err) {
                if (err) cb_waterfall(err);
                else cb_waterfall(null, channelsNodeId);
            });
        }
    ], function (err, result) {
        if (err) callback(err);
        else {
            callback(null, result);
        }
    });
}

function browseChannel(the_session, channelNodeId, callback) {
    var browseDescription = {
        nodeId: channelNodeId,
        referenceTypeId: Organizes,
        browseDirection: opcua.BrowseDirection.Forward,
        includeSubtypes: true,
        nodeClassMask: 0,
        resultMask: 63
    }
    var devicesNodeId = [];
    the_session.browse(browseDescription, function (err, browse_result) {
        if (err) {
            callback(err);
        } else {
            async.eachSeries(browse_result[0].references, function (reference, cb) {
                devicesNodeId.push(reference.nodeId);
                cb();
            }, function (err) {
                if (err) callback(err);
                else callback(null, devicesNodeId);
            });
        }
    });
}

function browseAllChannels(the_session, callback) {
    async.waterfall([
        function (cb_waterfall) {//遍历所有驱动
            browseAllDrivers(the_session, function (err, channels) {
                if (err) cb_waterfall(err);
                else {
                    cb_waterfall(null, channels);
                }
            });
        },
        function (channels, cb_waterfall) {//遍历所有设备
            var devicesNodeId = [];
            async.eachSeries(channels, function (channel, cb_eachSeries1) {
                browseChannel(the_session, channel, function (err, devices) {
                    if (err) cb_eachSeries1(err);
                    else if (devices.length != 0) {
                        async.eachSeries(devices, function (device, cb_eachSeries2) {
                            devicesNodeId.push(device);
                            cb_eachSeries2();
                        }, function (err) {
                            if (!err) cb_eachSeries1();
                        });
                    } else cb_eachSeries1();
                });
            }, function (err) {
                if (err) cb_waterfall(err);
                else cb_waterfall(null, devicesNodeId);
            });
        }
    ], function (err, result) {
        if (err) callback(err);
        else {
            callback(null, result);
        }
    });
}

function browseDevice(the_session, deviceNodeId, callback) {
    var browseDescription = {
        nodeId: deviceNodeId,
        referenceTypeId: Organizes,
        browseDirection: opcua.BrowseDirection.Forward,
        includeSubtypes: true,
        nodeClassMask: 0,
        resultMask: 63
    }
    var varsNodeId = [];
    the_session.browse(browseDescription, function (err, browse_result) {
        if (err) {
            callback(err);
        } else {
            async.eachSeries(browse_result[0].references, function (reference, cb) {
                varsNodeId.push(reference.nodeId);
                cb();
            }, function (err) {
                if (err) callback(err);
                else callback(null, varsNodeId);
            });
        }
    });
}

function browseAllDevices(the_session, callback) {
    async.waterfall([
        function (cb_waterfall) {//遍历所有通道
            browseAllChannels(the_session, function (err, devices) {
                if (err) cb_waterfall(err);
                else {
                    cb_waterfall(null, devices);
                }
            });
        },
        function (devices, cb_waterfall) {//遍历所有变量
            var varsNodeId = [];
            async.eachSeries(devices, function (device, cb_eachSeries1) {
                browseDevice(the_session, device, function (err, vars) {
                    if (err) cb_eachSeries1(err);
                    else if (vars.length != 0) {
                        async.eachSeries(vars, function (var1, cb_eachSeries2) {
                            varsNodeId.push(var1);
                            cb_eachSeries2();
                        }, function (err) {
                            if (!err) cb_eachSeries1();
                        });
                    } else cb_eachSeries1();
                });
            }, function (err) {
                if (err) cb_waterfall(err);
                else cb_waterfall(null, varsNodeId);
            });
        }
    ], function (err, result) {
        if (err) callback(err);
        else {
            callback(null, result);
        }
    });
}

exports.addDrivers = addDrivers;
exports.delDrivers = delDrivers;
exports.addChannels = addChannels;
exports.delChannels = delChannels;
exports.addDevices = addDevices;
exports.addVars = addVars;
exports.addAlarmObj = addAlarmObj;
exports.varAlarmConf = varAlarmConf;
exports.browseIo = browseIo;
exports.browseDriver = browseDriver;
exports.browseAllDrivers = browseAllDrivers;
exports.browseChannel = browseChannel;
exports.browseAllChannels = browseAllChannels;
exports.browseDevice = browseDevice;
exports.browseAllDevices = browseAllDevices;
