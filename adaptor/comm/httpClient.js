var async = require('async');
var Client = require('node-rest-client').Client;

//定义访问datahub函数
var httpclient = new Client();
exports.getHttpClient = function (callback) {
    async.series([
        function (cb) {
            httpclient.registerMethod("getDriver_add", "http://${path}/api-driver/add", "GET");
            httpclient.registerMethod("getChannel_add", "http://${path}/api-channel/add", "GET");
            httpclient.registerMethod("getDevice_add", "http://${path}/api-device/add", "GET");
            httpclient.registerMethod("postDataHub", "http://${path}", "POST");
            cb(null, httpclient);
        }], function (err, results) {
            callback(err, results);
        });
}

exports.getDriverToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getDriver_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
}

exports.getChannelToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getChannel_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
}

exports.getDeviceToAdd = function(httpclient,requestArgs,callback){
    httpclient.methods.getDevice_add(requestArgs,function (data, response) {
        callback(null, data);
    }).on('error', function (err) {
        callback(err);
    });
}