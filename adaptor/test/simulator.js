var opcua = require('node-opcua');

var the_session;
var options = {
    securityMode: opcua.MessageSecurityMode.SIGNANDENCRYPT,
    securityPolicy: opcua.SecurityPolicy.Basic128Rsa15,
    connectionStrategy: {
        maxRetry: 1,
        initialDelay: 2000,
        maxDelay: 10 * 1000
    }
};

//建立连接并创建会话session
function createConnection(ipAddress, port, user, password, callback) {
    var endpointUrl = "opc.tcp://" + ipAddress + ":" + port;//服务器地址
    var client = new opcua.OPCUAClient(options);
     client.connect(endpointUrl, function (err) {
        if (err) {
            callback("create connect failed:");
        } else {
            client.createSession({ userName: user, password: password }, function (err, session) {
                if (!err) {
                    the_session = session;
                    callback(null, "create session success");
                } else {
                    callback(err, null);
                }
            });
        }
    }); 
}

function writeValue(){
    the_session.writeSingleNode(new opcua.NodeId(opcua.NodeIdType.STRING,
        'ModbusTcpClient.channel1.Blower.current',2),
        new opcua.Variant({ dataType: opcua.DataType.Double, value: 12}),function(err,result){
            if (err) {
                console.log("writeSingleNode err:",err);
            }else{
                console.log("writeSingleNode success:",result);
            }
        });
}
createConnection("127.0.0.1", 48025, "ModbusTcpClient", "Driver", function (err, result) {
    if (result) {
        console.log(result);
        writeValue();
    } else {
        console.log(err);
    }
});