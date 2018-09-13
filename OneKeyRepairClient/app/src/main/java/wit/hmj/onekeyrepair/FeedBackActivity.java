package wit.hmj.onekeyrepair;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.my.ModifyNameActivity;
import wit.hmj.onekeyrepair.my.RegisterCheckSuccessActivity;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class FeedBackActivity extends BaseActivity {
    private EditText editText;
    private Button commit;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(FeedBackActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_FeedBack:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    RegisterJson registerJson = gson.fromJson(result, RegisterJson.class);
                    int status = registerJson.getStatus();
                    //如果失败
                    if (status==0){
                        Toast.makeText(FeedBackActivity.this, registerJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(status==1) {
                        Toast.makeText(FeedBackActivity.this, "反馈成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        editText = (EditText) findViewById(R.id.editText);
        commit = (Button) findViewById(R.id.btn_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                //把反馈提交到服务器 参数useid+内容
                ProgressDialogUtils.showProgressDialog(FeedBackActivity.this, "加载中...");
                Bundle bundle = new Bundle();
                bundle.putString("useid",USERID);
                bundle.putString("content",content);
                OkHttpTools.request(bundle, API.URL_FeedBack,handler);
            }
        });
    }


}
