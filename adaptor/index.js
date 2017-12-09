//用一个周期执行的函数检查pg中各张表的更新,然后触发执行创建驱动、创建通道、创建设备、创建变量、创建报警；
var opcua = require("node-opcua");
var uacilent = require("./db/ua_client");
var async = require("async");
var Client = require('node-rest-client').Client;

//全局变量
var httpclient = new Client();
var the_session = {};
var options = {
    securityMode: opcua.MessageSecurityMode.SIGNANDENCRYPT,
    securityPolicy: opcua.SecurityPolicy.Basic128Rsa15,
    connectionStrategy: {
        maxRetry: 1,
        initialDelay: 2000,
        maxDelay: 10 * 1000
    }
};

var InputArguments = [
    { ParentNodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400001010,2)},
    { NodeId: new opcua.NodeId(opcua.NodeIdType.NUMERIC,33335,2)},
    { TypeDefinitionId:new opcua.NodeId(opcua.NodeIdType.NUMERIC,400000301,2)},
    { BrowseName: "wzj_BrowseName" },
    { DisplayName: "wzj_displayname" },
    { Description: "wzj_description" }];

async.auto({
    uaconnect: function (callback) {//连接ua数据库
        uacilent.createConnection("127.0.0.1", 4841, "admin", "admin", options, function (err, session) {
            if (err) {
                console.log(err);
                //TODO:过段时间重连，不能callback
                callback();
            } else {
                the_session = session;
                callback(null,"create session success!");
            }
        });
    },
    createDriver: ['uaconnect', function (uaconnect_res,callback) {//创建驱动
        /* httpclient.get("https://way.jd.com/he/freecity?city=beijing&appkey=600336a2ff99a20501e12644524fd576",args, function (data, response) {
            console.log(data.result.HeWeather5); */
            uacilent.AddObject(the_session, InputArguments, function (err, result) {
                if (err) {
                    console.log(err);
                    //TODO:过段时间再次AddObject，不能callback
                    callback();
                } else {
                    callback(null,result);
                }
            });
        /* }).on('error', function (err) {
            console.log('something went wrong on the request', err.request.options);
        }); */
    }]
}, function (err, results) {
    console.log(results);
});