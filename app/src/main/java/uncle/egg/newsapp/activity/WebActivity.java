package uncle.egg.newsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import uncle.egg.newsapp.R;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        init();


        webView.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        webView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }
            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                Toast.makeText(WebActivity.this, "Error : " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        url = getIntent().getStringExtra("url");
        Log.v("MY_TAG------",url);
        webView = (WebView) findViewById(R.id.webview);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode ==  KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
