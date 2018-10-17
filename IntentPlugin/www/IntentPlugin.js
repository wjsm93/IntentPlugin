var exec = require('cordova/exec');

exports.openApp = function(parametros, success, error) {
  var objetoJSON = JSON.parse(parametros);
  var occStr = objetoJSON.occ.toString();
  exec(success, error, 'IntentPlugin', 'openApp', occStr);
};