var httpclient = require('../db/http_client');
var requestArgs = {
    path: { "path": "localhost:8080/api-driver/add" }, //区分restful接口
    parameters: { uaServer: "ioserver" }, //序列化到url中的parameters
    headers: { "Content-Type": "application/json" } //request headers
};
httpclient.getHttpClient(function(err,result){
    if(err){
        console.log(err);
    }else{
        result[0].methods.getDataHub(requestArgs,function (data, response) {
            console.log(data);
        });
    }
});




