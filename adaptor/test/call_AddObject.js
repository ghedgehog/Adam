var opcua = require('./node-opcua');
console.log(new opcua.NodeId(opcua.NodeIdType.NUMBERIC,333334,2));
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

function createConnection(ipAddress, port, user, password, callback) {
  //建立连接和创建会话session
  var endpointUrl = "opc.tcp://" + ipAddress + ":" + port;//服务器地址
  var client = new opcua.OPCUAClient(options);
  client.connect(endpointUrl, function (err) {
    if (err) {
      console.log("create connect failed:");
    } else {
      client.createSession({ userName: user, password: password }, function (err, session) {
        if (err) {
          console.log("create session failed:"+err, null);
        } else {
          the_session = session;
          callback(null,"create session success:");
        }
      });
    }
  });
}

function methodToCall() {
  var methodToCalls = [];
  var id = new opcua.NodeId(opcua.NodeIdType.NUMBERIC,400001010,2);
  methodToCalls.push(new opcua.call_service.CallMethodRequest({
    objectId: "ns=2;i=400001511",
    methodId: "ns=2;s=400001511.AddObject",
    inputArguments: [
      new opcua.Variant({ dataType: 17, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400001010,2)}),
      new opcua.Variant({ dataType: 17, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC,333335,2)}),
      new opcua.Variant({ dataType: 17, value: new opcua.NodeId(opcua.NodeIdType.NUMERIC,400000301,2)}),
      new opcua.Variant({ dataType: 12, value: "wzj_browsename" }),
      new opcua.Variant({ dataType: 12, value: "wzj_displayname"}),
      new opcua.Variant({ dataType: 12, value: "wzj_description"})]
  }));
  the_session.call(methodToCalls, function (err, result) {
    if (err) {
      console.log("err:" + err);
    } else {
      console.log("call result:" + result);
    }
  });
}

createConnection("127.0.0.1", 4841, "admin", "admin", function (err, result) {
  if (result) {
    console.log(result);
    methodToCall();
  } else {
    console.log(err);
  }
});

