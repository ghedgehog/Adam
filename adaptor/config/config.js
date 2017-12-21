exports.dataHubPath = 'localhost:8080';  //datahub的url
exports.subChannel = 'Adam';             //通过监听subChannel判断uaserver需要修改什么配置
exports.redis = {//redis配置信息
    host:'196.168.65.163',
    port:6379,
    db:1
};
exports.uaSever = {//uaserver配置信息
    name: 'ioserver',
    ip: '127.0.0.1',
    port: 4841,
    user: 'admin',
    password: 'admin'
};
