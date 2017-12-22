var Redis = require('ioredis');
var redisClient = new Redis( {
  host:'196.168.65.163',
port:6379,
db:1});
redisClient.set(1,33333);
redisClient.subscribe('redisChat',function (err, count) {
  });
  
  redisClient.on('message', function (channel, message) {
    // Receive message Hello world! from channel news
    // Receive message Hello again! from channel music
    console.log( message);
  });