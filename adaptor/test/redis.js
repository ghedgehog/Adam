var Redis = require('ioredis');
var redisClient = new Redis();
redisClient.subscribe('redisChat',function (err, count) {
  });
  
  redisClient.on('message', function (channel, message) {
    // Receive message Hello world! from channel news
    // Receive message Hello again! from channel music
    console.log( message);
  });