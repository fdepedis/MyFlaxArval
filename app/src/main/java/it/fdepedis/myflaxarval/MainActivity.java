package it.fdepedis.myflaxarval;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private final String URL =
            "https://iam.arval.com/authenticationendpoint/login.do?commonAuthCallerPath=%2Fsamlsso&forceAuth=false&passiveAuth=false&tenantDomain=carbon.super&sessionDataKey=545f9eaf-d631-4249-8b6c-1936e4079ab5&relyingParty=https%3A%2F%2Fmyarval.arval.com&type=samlsso&sp=arval_sp_MyArvalWeb&isSaaSApp=false&authenticators=CustomBasicAuthenticator:LOCAL"
            /*"https://iam.arval.com/authenticationendpoint/login.do"*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        webView.setWebViewClient(new WebViewClient());

        // Load URL
        webView.loadUrl(URL);

        // Enable responsive layout
        webView.getSettings().setUseWideViewPort(true);
        // Zoom out if the content width is greater than the width of the viewport
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true); // allow pinch to zooom
        webView.getSettings().setDisplayZoomControls(false); // disable the default zoom controls on the page
        webView.getSettings().setDomStorageEnabled(true); // load property javascript
        webView.getSettings().setAppCachePath(getBaseContext().getCacheDir().getAbsolutePath());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(getBaseContext()), "Android");

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {

                webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            webView.loadUrl(URL);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}