var log4js = require('log4js');
exports.getLogger = function () {
  log4js.configure({
    appenders: {
      log: {
        type: "file",
        filename: __dirname + '/log_file/adaptor.log',
        maxLogSize: 1048576,
        backups: 50
      },
      console: {
        type: 'console'
      }
    },
    categories: {
      default: {
        appenders: ['log','console'],
        level: 'ALL'
      }
    }
  });
  return log4js.getLogger('log');
};

