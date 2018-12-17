package cl.entel.plugins.intentplugin;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.net.Uri;
import android.content.pm.PackageManager;
import java.util.List;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class IntentPlugin extends CordovaPlugin {

    private static final String DURATION_LONG = "long";
    private static final String INTENT_ACTION = "cl.transbank.onepay.APP_2_APP_ACTION";
    private static final String MARKET_URI = "market://details?id=cl.transbank.onepay";
    private static final String ONE_PAY_SCHEME = "onepay:";

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        
        if("openApp".equals(action)){
            try {
                String occ = args.getString(0);

                if (intentStart(occ)){
                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
                    callbackContext.sendPluginResult(pluginResult);
                    return true;
                }
            } catch (Exception e){
                callbackContext.error("Error ejecutando action: " + e);
                return false;
            }
        }
        callbackContext.error("No existe método: " + action);
        return false;
    }

    public boolean isIntentAvailable(Intent intent){
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        List activities = pm.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        if(activities.size() > 0){
            return true;
        }
        return false;
    }

    public boolean intentStart(String occ){
        try{
            Uri destino = Uri.parse(ONE_PAY_SCHEME);
            Intent intentOP = new Intent(INTENT_ACTION,destino);
            intentOP.putExtra("occ", occ);
            intentOP.putExtra("browser_fallback_url","market://details?id=cl.transbank.onepay");
            intentOP.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        
            if(isIntentAvailable(intentOP)) {
                this.cordova.getActivity().startActivity(intentOP);
                return true;
            } else {
                Uri destinoPlay = Uri.parse(MARKET_URI);
                Intent intentStore = new Intent(Intent.ACTION_VIEW,destinoPlay);
                if(isIntentAvailable(intentStore)){
                    this.cordova.getActivity().startActivity(intentStore);
                    return true;
                } else {
                    return false;
                }
            } 
        } catch (Exception e){
            return false;
        }

    }
}