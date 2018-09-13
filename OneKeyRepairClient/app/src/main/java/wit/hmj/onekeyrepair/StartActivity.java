package wit.hmj.onekeyrepair;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

public class StartActivity extends BaseActivity {

    WebView webView;
    ImageView imageView;
    private final String imagePath = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514541268711&di=b7787fac493cfa432957a6aef76b7987&imgtype=0&src=http%3A%2F%2Fp17.qhimg.com%2Ft0183c8ec22746a5858.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        webView = (WebView) findViewById(R.id.webView);
//        webView.loadUrl(imagePath);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
