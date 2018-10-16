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
    private static final String INTENT_ACTION = "cl.ionix.ewallet.APP_2_APP_ACTION";
    private static final String MARKET_URI = "market://details?id=cl.ionix.ewallet";
    private static final String ONE_PAY_SCHEME = "onepay:";

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {

        Log.d("HOLA", "execute method native Android: "+action);
        Uri destino = Uri.parse(ONE_PAY_SCHEME);

        Intent intentOP = new Intent(INTENT_ACTION,destino);
        intentOP.putExtra("occ","1810324060790136");
        intentOP.putExtra("browser_fallback_url","market://details?id=cl.transbank.onepay");
        intentOP.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try{
            if(isIntentAvailable(intentOP)) {
                Log.d("debug","***OnePay Found - Intent: "+intentOP.toUri(Intent.URI_INTENT_SCHEME));
                startActivity(intentOP);
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
                callbackContext.sendPluginResult(pluginResult);
                return true;
            } else {
                Log.d("debug","***OnePay Not Found - Opening Market");
                Uri destinoPlay = Uri.parse(MARKET_URI);
                Intent intentStore = new Intent(Intent.ACTION_VIEW,destinoPlay);
                if(isIntentAvailable(intentStore)){
                    Log.d("debug","***Market Found - Intent: "+intentStore.toUri(Intent.URI_INTENT_SCHEME));
                    startActivity(intentStore);
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
                    callbackContext.sendPluginResult(pluginResult);
                    return true;
                } else {
                    Log.d("debug","***No Apps Found");
                    return false;
                }
            } 
        } catch (Exception e){
            Log.d("debug","***Error");
            return false;
        }
 
    }

    public boolean isIntentAvailable(Intent intent){
        PackageManager pm = getPackageManager();
        List activities = pm.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        if(activities.size() > 1){
            return true;
        }
        return false;
    }
}