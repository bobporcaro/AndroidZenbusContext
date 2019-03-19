package comdemo.demozenbus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

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
        myWebView.loadUrl("http://192.168.1.44:8080/demo/redirtan.html");
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
        public void loadZenbus(final String namespace, final String route, final String poi){

            /*myWebView.post(new Runnable() {
                @Override
                public void run() {
                    myWebView.loadUrl("https://zenbus.net/"+ namespace + "?route=" + route + "&busStop=" + poi);
                }
            });*/

            Intent intent = new Intent(MainActivity.this, ZenbusWebviewActivity.class);
            startActivity(intent);

        }
    }
}
