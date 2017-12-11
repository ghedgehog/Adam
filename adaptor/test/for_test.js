var opcua = require("node-opcua");

var nodesId = ["ns=2;i=343", "ns=2;i=344", "ns=2;i=345", "ns=2;i=346", "ns=2;i=347", "ns=2;i=348", "ns=2;i=349"];
var drivers = [{ "name": "ModbusTcpClient" }, { "name": "OPC_FC_Client" }, { "name": "IM_A11_RTU" },
{ "name": "IEC104TCP" }, { "name": "ModbusUdpServer" }, { "name": "IEC104TCP" }, { "name": "ModbusUdpServer" }];

var AddObjectArgs = {};
for (var index = 0; index < drivers.length; index++) {
    AddObjectArgs.ParentNodeId = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400001010, 2);
    AddObjectArgs.NodeId = new opcua.NodeId(opcua.NodeIdType.NUMERIC, +nodesId[index].substr(7,nodesId[index].length),2);
    AddObjectArgs.TypeDefinitionId = new opcua.NodeId(opcua.NodeIdType.NUMERIC, 400001010, 2);
    AddObjectArgs.BrowseName = drivers[index].name;
    AddObjectArgs.DisplayName = drivers[index].name;
    AddObjectArgs.Description = drivers[index].name;
}