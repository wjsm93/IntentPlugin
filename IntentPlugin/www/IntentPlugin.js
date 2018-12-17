var intentplugin = {
  openApp: function (arg0, success, error) {
    exec(success, error, 'IntentPlugin', 'openApp', [arg0]);
  }
}

cordova.addConstructor(function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.intentplugin = intentplugin;
  return window.plugins.intentplugin;
});