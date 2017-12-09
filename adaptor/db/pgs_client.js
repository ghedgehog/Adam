var Client = require('node-rest-client').Client;

var client = new Client();

var args = {
	headers: { "Content-Type": "application/json" }
};

// direct way
client.get("https://way.jd.com/he/freecity?city=beijing&appkey=600336a2ff99a20501e12644524fd576",args, function (data, response) {
	// parsed response body as js object
	console.log(data.result.HeWeather5);
	// raw response
	//console.log(response);
});