package wit.hmj.onekeyrepair.my;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.MainActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class ModifyNameActivity extends BaseActivity {

    private EditText edit_name;
    TextView title;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(ModifyNameActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_ModifyName:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    RegisterJson registerJson = gson.fromJson(result, RegisterJson.class);
                    int status = registerJson.getStatus();
                    //如果失败
                    if (status==0){
                        Toast.makeText(ModifyNameActivity.this, registerJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(status==1){
                        //如果成功
                        Intent intent = new Intent();
                        intent.putExtra("name",edit_name.getText().toString());
                        setResult(1,intent);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_modify_name);
        initView();
        edit_name.setText(name);
    }

    private void initView() {
        edit_name = (EditText) findViewById(R.id.edit_name);
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edit_name.getText().toString();
                //请求服务器修改昵称 参数useid+新昵称
                ProgressDialogUtils.showProgressDialog(ModifyNameActivity.this, "加载中...");
                Bundle bundle = new Bundle();
                bundle.putString("useid",USERID);
                bundle.putString("newName",newName);
                OkHttpTools.request(bundle, API.URL_ModifyName,handler);
            }
        });

        findViewById(R.id.confirm).setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.title);
        title.setText("修改昵称");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
