var async = require('async');
var Client = require('node-rest-client').Client;

//定义访问datahub函数
var httpclient = new Client();
exports.getHttpClient = function (callback) {
    async.series([
        function (cb) {
            //注册client端get函数,获取需要增加的信息,简化访问方式；
            httpclient.registerMethod("getDataHub", "http://${path}", "GET");
            //注册client端post函数,简化访问方式；},
            httpclient.registerMethod("postDataHub", "http://${path}", "POST");
            cb(null, httpclient);
        }], function (err, results) {
            callback(err, results);
        });
}

