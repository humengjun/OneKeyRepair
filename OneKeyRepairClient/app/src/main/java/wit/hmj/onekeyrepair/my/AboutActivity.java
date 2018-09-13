package wit.hmj.onekeyrepair.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.confirm).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.title)).setText("关于我们");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
