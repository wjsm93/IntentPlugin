package cl.entel.plugins.intentplugin;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class IntentPlugin extends CordovaPlugin {

    private static final String DURATION_LONG = "long";

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

        Log.d("HOLA", "execute method native Android");

        // Send a positive result to the callbackContext
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
        callbackContext.sendPluginResult(pluginResult);

        return true;
    }
}