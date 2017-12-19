var async = require('async');
var Client = require('node-rest-client').Client;

//定义访问datahub函数
var httpclient = new Client();
exports.getHttpClient = function (callback) {
    async.series([
        function (cb) {
            /*驱动*/
            httpclient.registerMethod("getDriver_add", "http://${path}/api-driver/add", "GET");
            /*通道*/
            httpclient.registerMethod("getChannel_add", "http://${path}/api-channel/add", "GET");
            /*设备*/
            httpclient.registerMethod("getDevice_add", "http://${path}/api-device/add", "GET");
            /*变量*/
            httpclient.registerMethod("getVar_add", "http://${path}/api-device/var", "GET");
            /*变量报警*/
            httpclient.registerMethod("getVarAlarmConf", "http://${path}/api-device/alm-conf-var", "GET");
            /*报警对象*/
            httpclient.registerMethod("getAlarmObj_add", "http://${path}/api-device/alm-conf", "GET");
            /*推送实时*/
            httpclient.registerMethod("postDataHub", "http://${path}", "POST");
            /*推送报警*/
            httpclient.registerMethod("postDataHub", "http://${path}", "POST");
            cb(null, httpclient);
        }], function (err, results) {
            callback(err, results);
        });
};

exports.getDriverToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getDriver_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
};

exports.getChannelToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getChannel_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
};

exports.getDeviceToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getDevice_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
};

exports.getVarToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getVar_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
};

exports.getVarToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getVar_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
};

exports.getAlarmObjToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getAlarmObj_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
};

exports.getVarAlarmConf = function(httpclient,requestArgs,callback){
    httpclient.methods.getVarAlarmConf(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
};