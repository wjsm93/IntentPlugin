var exec = require('cordova/exec');

exports.echo = function(arg0, success, error) {
  exec(success, error, 'IntentPlugin', 'echo', [arg0]);
};

exports.openApp = function(arg0, success, error) {
  exec(success, error, 'IntentPlugin', 'openApp', [arg0]);
};