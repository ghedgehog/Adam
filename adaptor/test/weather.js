/*global require,console,setInterval */
"use strict";
Error.stackTraceLimit = Infinity;
/*global require,setInterval,console */
var cities = ['shanghai', 'beijing', 'yuncheng'];
// read the World Weather Online API key.
var fs = require("fs");
//fs.readFileSync("worldweatheronline.key");
function getCityWeather(city, callback) {
    //var api_url="http://api.worldweatheronline.com/free/v2/weather.ashx?q="+city+"+&format=json&key="+ key;
    //https://way.jd.com/he/freecity?city=beijing&appkey=600336a2ff99a20501e12644524fd576"
    var api_url = "https://way.jd.com/he/freecity?city=" + city + "&appkey=600336a2ff99a20501e12644524fd576";
    console.error("api_url:",api_url);
    var options = {
        url: api_url,
        "content-type": "application-json",
        json: ""
    };
    var request = require("request");
    request(options, function (error, response, body) {
        if (!error && response.statusCode === 200) {
            var data = perform_read(city, body);
            callback(null, data);
        } else {
            callback(error);
        }
    });
}
//https://way.jd.com/he/freecity?city=shanghai+&format=json&key=600336a2ff99a20501e12644524fd576
//https://way.jd.com/he/freecity?city=beijing&appkey=600336a2ff99a20501e12644524fd576
function perform_read(city, body) {
    //body:"{"code":"10000","charge":false,"msg":"查询成功",
    //"result":{"HeWeather5":[{"basic":{"city":"北京","lon":"116.40528870","id":"CN101010100","prov":"北京","cnty":"中国","lat":"39.90498734"},"status":"ok"}]}}"
    var obj = JSON.parse(body);
    console.log("body:",body);
    var current_condition = obj.result.HeWeather5[0].basic;
    return {
        id: current_condition.id,
        city: current_condition.city,
        cnty: current_condition.cnty,
        date: new Date(),
        lon: parseFloat(current_condition.lon),
        lat: parseFloat(current_condition.lat)
    };
}
var city_data_map = {};
// a infinite round-robin iterator over the city array
var next_city = function (arr) {
    var counter = arr.length;
    return function () {
        counter += 1;
        if (counter >= arr.length) {
            counter = 0;
        }
        return arr[counter];
    };
}(cities);

function update_city_data(city) {
    getCityWeather(city, function (err, data) {
        if (!err) {
            city_data_map[city] = data;
            console.log(city, JSON.stringify(data, null, " "));
        } else {
            console.log("error city", city, err);
        }
    });
}

// make a API call every 10 seconds
var interval = 10 * 1000;
setInterval(function () {
    var city = next_city();
    update_city_data(city);
}, interval);

var opcua = require("node-opcua");

var server = new opcua.OPCUAServer({
    port: 4334 // the port of the listening socket of the server
});

server.buildInfo.productName = "WeatherStation";
server.buildInfo.buildNumber = "7658";
server.buildInfo.buildDate = new Date(2014, 5, 2);
function post_initialize() {
    console.log("initialized");
    function construct_my_address_space(server) {
        // declare some folders
        var citiesNode = server.engine.addressSpace.addFolder("ObjectsFolder", { browseName: "Cities" });
        function create_CityNode(city_name) {
            // declare the city node
            var cityNode = server.engine.addressSpace.addFolder(citiesNode, { browseName: city_name });
            server.engine.addressSpace.addVariable({
                componentOf: cityNode,
                browseName: "Temperature",
                dataType: "Double",
                value: { get: function () { return extract_value(city_name, "date"); } }
            });
            server.engine.addressSpace.addVariable({
                componentOf: cityNode,
                browseName: "Humidity",
                dataType: "Double",
                value: { get: function () { return extract_value(city_name, "lon"); } }
            });
            server.engine.addressSpace.addVariable({
                componentOf: cityNode,
                browseName: "Pressure",
                dataType: "Double",
                value: { get: function () { return extract_value(city_name, "lat"); } }
            });
        }
        cities.forEach(function (city) {
            create_CityNode(city);
        });
        function extract_value(city_name, property) {
            var city = city_data_map[city_name];
            if (!city) {
                return opcua.StatusCodes.BadDataUnavailable
            }
            var value = city[property];
            return new opcua.Variant({ dataType: opcua.DataType.Double, value: value });
        }
    }
    construct_my_address_space(server);
    server.start(function () {
        console.log("Server is now listening ... ( press CTRL+C to stop)");
        console.log("port ", server.endpoints[0].port);
        var endpointUrl = server.endpoints[0].endpointDescriptions()[0].endpointUrl;
        console.log(" the primary server endpoint url is ", endpointUrl);
    });
}
server.initialize(post_initialize);
