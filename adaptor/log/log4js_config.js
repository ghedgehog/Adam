module.exports = {
    "appenders":
        [{
            "category": "console",
            "type": "console"
        },
        {
            "category": "mylog",
            "type": "file",
            "filename": "./logs/log_info/adaptor.log",
            "maxLogSize": 104857500,
            "backups": 5
        }],
    "replaceConsole": true,
    "levels": { "mylog": "ALL" }
};