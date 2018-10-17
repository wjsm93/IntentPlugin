var exec = require('cordova/exec');

exports.openApp = function(arg0, success, error) {
  exec(success, error, 'IntentPlugin', 'openApp', [arg0]);
};