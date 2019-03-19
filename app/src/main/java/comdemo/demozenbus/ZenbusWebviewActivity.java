package comdemo.demozenbus;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ZenbusWebviewActivity extends Activity {

    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("http://zenbus.net/opentourparis");
    }
}
