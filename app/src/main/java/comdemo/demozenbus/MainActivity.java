package comdemo.demozenbus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


/**
 * Main activity to load TAN demo web app
 *
 * This activity exposes two javascript functions:
 * - zenbusRedir --> start installed zenbus traveller app OR redir to google play to install it
 * - zenbusLoad --> start in app activity to load zenbus.net into a webview
 */
public class MainActivity extends Activity {

    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        myWebView.setWebContentsDebuggingEnabled(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl(Config.dev_srv);
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void zenbusRedir(String namespace, String route, String poi) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.byjoul.code.zenbus.android");

            if (launchIntent != null) {
                launchIntent.putExtra("namespace", namespace);
                launchIntent.putExtra("route", route);
                launchIntent.putExtra("poi", poi);
            }else {
                // Bring user to the market or let them choose an app?
                launchIntent = new Intent(Intent.ACTION_VIEW);
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchIntent.setData(Uri.parse("market://details?id=com.byjoul.code.zenbus.android"));
            }
            startActivity(launchIntent);
        }
        @JavascriptInterface
        public void zenbusLoad(final String namespace, final String route, final String poi){
            Intent intent = new Intent(MainActivity.this, ZenbusWebviewActivity.class);
            intent.putExtra("namespace", namespace);
            intent.putExtra("route", route);
            intent.putExtra("poi", poi);
            
            startActivity(intent);
        }
    }
}