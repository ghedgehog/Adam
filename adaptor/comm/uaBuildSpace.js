var opcua = require("node-opcua");
var uaServiceMethod = require("./uaServiceMethod");
var async = require('async');
var ioDriverRoot = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400001010, 2);
var ioDriverType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000301, 2);
var ioChannelType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000310, 2);
var Organizes = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 35, 0);

exports.addDrivers = function (the_session, driversToAdd, callback) {
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

exports.addChannels = function (the_session, driverNodeId, channelsToAdd, callback) {
    var AddObjectArgs = {};
    async.eachSeries(channelsToAdd, function (channel, cb) {
        AddObjectArgs.ParentNodeId = driverNodeId;
        AddObjectArgs.NodeId = new opcua.NodeId(opcua.NodeIdType.STRING, driverNodeId.value+"."+channel.name, 2);
        AddObjectArgs.TypeDefinitionId = ioChannelType;
        AddObjectArgs.BrowseName = channel.name;
        AddObjectArgs.DisplayName = channel.name;
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

exports.browseDrivers = function (the_session, callback) {
    var browseDescription = {
        nodeId: ioDriverRoot,
        referenceTypeId: Organizes,
        browseDirection: opcua.BrowseDirection.Forward,
        includeSubtypes: true,
        nodeClassMask: 0,
        resultMask: 63
    }
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

exports.browseChannels = function (the_session, driverNodeId, callback) {
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