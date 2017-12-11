//用一个周期执行的函数检查pg中各张表的更新,然后触发执行创建驱动、创建通道、创建设备、创建变量、创建报警；
var opcua = require("node-opcua");
var uacilent = require("./db/ua_client");
var httpclient = require('./db/http_client');
var async = require("async");

//全局变量
var the_session = {};
var requestArgs = {
    path: { "path": "localhost:8080/api-driver/add" }, //区分restful接口
    parameters: { uaServer: "ioserver" }, //序列化到url中的parameters
    headers: { "Content-Type": "application/json" } //request headers
};

function task() {
    async.auto({
        uaConnect: function (callback) {//连接ua数据库
            uacilent.createConnection("127.0.0.1", 4841, "admin", "admin", function (err, session) {
                if (err) {
                    callback(err);
                } else {
                    the_session = session;
                    callback(null, "create session success!");
                }
            });
        },
        httpClient: function (callback) {
            httpclient.getHttpClient(function (err, result) {
                if (err) {
                    callback(err);
                } else {
                    callback(null, result[0]);
                }
            })
        },
        getDriver: ['httpClient', function (result, callback) {//获取驱动信息
            result.httpClient.methods.getDataHub(requestArgs, function (data, response) {
                callback(null, data);
            }).on('error', function (err) {
                callback(err);
            });
        }],
        addDriver: ['uaConnect', 'getDriver', function (result, callback) {//创建驱动
            uacilent.GetFreeNodeIds(the_session, result.getDriver.length, function (err, nodesId) {
                if (err) {
                    callback(err);
                } else {
                    var AddObjectArgs = {},
                        ioDriverRoot = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400001010, 2),
                        ioDriverType = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400000301, 2);
                    for (var index = 0; index < result.getDriver.length; index++) {
                        AddObjectArgs.ParentNodeId = ioDriverRoot;
                        AddObjectArgs.NodeId = nodesId[index];
                        AddObjectArgs.TypeDefinitionId = ioDriverType;
                        AddObjectArgs.BrowseName = result.getDriver[index].name;
                        AddObjectArgs.DisplayName = result.getDriver[index].name;
                        AddObjectArgs.Description = result.getDriver[index].name;
                        uacilent.AddObject(the_session, AddObjectArgs, function (err, result) {
                            if (err) {
                                callback(err);
                            }
                        });
                    }
                    callback(null, "addDriver success!");
                }
            });
        }]
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