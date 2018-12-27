package promethean2k18.com.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import net.steamcrafted.loadtoast.LoadToast;

import promethean2k18.com.R;
import promethean2k18.com.Utils.InternetCheck;

public class View_tutorial extends AppCompatActivity {

    private WebView webView;
    LoadToast lt;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tutorial);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Promethean 2k18");

        // http://bvrit.ac.in/index.php/transportlink

        url = "http://promethean2k18.com/app/view_tutorial.html";
        webView = findViewById(R.id.webviewV);
        lt=new LoadToast(this);
        InternetCheck connection = new InternetCheck(getApplicationContext());
        boolean checkinternet = connection.isIsInternetAvailable();
        if (checkinternet){
            lt.setText("Loading...");
            loadpage();
        }else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Home_final.class));
            finish();
        }
    }


    private void loadpage() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Conneting...", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                lt.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                lt.success();

            }

        });
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
