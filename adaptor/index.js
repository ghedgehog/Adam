//用一个周期执行的函数检查pg中各张表的更新,然后触发执行创建驱动、创建通道、创建设备、创建变量、创建报警；
var opcua = require("node-opcua");
var uaServiceMethod = require("./comm/uaServiceMethod");
var uaBuildSpace = require("./comm/uaBuildSpace");
var httpClient = require('./comm/httpClient');
var async = require("async");

//全局变量
var the_session = {};
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
                    callback(null, result[0]);
                }
            })
        },
        addDriver: ['uaConnect', 'httpClient', function (result, callback) {//创建驱动
            httpClient.getDriverToAdd(result.httpClient, requestArgs, function (err, result) {
                if (err) callback(err);
                else {
                    uaBuildSpace.addDrivers(the_session, result, function (err1, result1) {
                        if (err1) callback(err1);
                        else {
                            callback(null, result1);
                        }
                    });
                }
            });
        },],
        addChannel:['httpClient','addDriver',function(result,callback){
            uaBuildSpace.browseDrivers(the_session,function(err,drivers){
                if(err) callback(err);
                else{
                    var para = {};
                    async.eachSeries(drivers, function(driver, cb) {
                        para.driver = driver.value;
                        requestArgs.parameters = para;
                        httpClient.getChannelToAdd(result.httpClient,requestArgs,function(err1,channels){
                            if(err1) cb(err1);
                            else{
                                uaBuildSpace.addChannels(the_session,driver,channels,function(err2,result2){
                                    if(err2) cb(err2);
                                    else cb();
                                });
                            }
                        })
                    },function(err){
                        if(err) callback(err);
                        else callback(null,"addChannel success!");
                    }) 
                }
            });
        }]/* ,
        addDevice:['httpClient','addChannel',function(result,callback){
            uaBuildSpace.browseDrivers(the_session,function(err,drivers){
                if(err) callback(err);
                else{
                    var para = {};
                    async.eachSeries(drivers, function(driver, cb) {
                        para.driver = driver.name;
                        requestArgs.parameters = para;
                        httpClient.getChannelToAdd(result.httpClient,requestArgs,function(err1,channels){
                            if(err1) cb(err1);
                            else{
                                uaBuildSpace.addChannels(the_session,driver.nodeId,channels,function(err2,result2){
                                    if(err2) cb(err2);
                                    else cb();
                                });
                            }
                        })
                    },function(err){
                        if(err) callback(err);
                        else callback(null,"addChannel success!");
                    }) 
                }
            });
        }] */
    }, function (err, results) {
        if (err) {
            console.log(err);
            setTimeout(task, 3000);
        } else {
            console.log(JSON.stringify(results));
        }
    });
}

task();