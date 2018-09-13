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
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class ModifyPassWordActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private EditText current_password,new_password,again_password;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(ModifyPassWordActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_ModifyPassword:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    RegisterJson registerJson = gson.fromJson(result, RegisterJson.class);
                    int status = registerJson.getStatus();
                    //如果失败
                    if (status==0){
                        Toast.makeText(ModifyPassWordActivity.this, registerJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(status==1){
                        //如果成功
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pass_word);
        initView();
    }

    private void initView() {
        findViewById(R.id.confirm).setVisibility(View.GONE);
        title = (TextView) findViewById(R.id.title);
        title.setText("修改密码");
        current_password = (EditText) findViewById(R.id.currentPwd);
        new_password = (EditText) findViewById(R.id.newPwd);
        again_password = (EditText) findViewById(R.id.againPwd);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){
            finish();
        }else if(v.getId() == R.id.btn_commit){
            if(current_password.getText().toString().equals("")){
                Toast.makeText(this,"请输入当前密码！",Toast.LENGTH_SHORT).show();
                return;
            }
            if(new_password.getText().toString().equals("")){
                Toast.makeText(this,"请输入新密码！",Toast.LENGTH_SHORT).show();
                return;
            }
            if(again_password.getText().toString().equals("")){
                Toast.makeText(this,"请输入确认密码！",Toast.LENGTH_SHORT).show();
                return;
            }
            if(new_password.getText().toString().equals(again_password.getText().toString())){
                String currentPassword = current_password.getText().toString();
                String newPassword = new_password.getText().toString();
                //请求服务器修改密码 参数 useid+旧密码+新密码
                ProgressDialogUtils.showProgressDialog(this, "加载中...");
                Bundle bundle = new Bundle();
                bundle.putString("useid",USERID);
                bundle.putString("currentPassword",currentPassword);
                bundle.putString("newPassword",newPassword);
                OkHttpTools.request(bundle, API.URL_ModifyPassWord,handler);
            }else {
                Toast.makeText(this,"两次密码不一致！",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
