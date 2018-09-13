package wit.hmj.onekeyrepair.my;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.util.HashMap;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.MainActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class RegisterCheckSuccessActivity extends BaseActivity implements View.OnClickListener{
    private EditText editText_inputname,editText_setpwd,editText_surepwd;
    private String phoneNumber;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(RegisterCheckSuccessActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_Register_3:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    RegisterJson registerJson = gson.fromJson(result, RegisterJson.class);
                    int status = registerJson.getStatus();
                    //如果失败
                    if (status==0){
                        Toast.makeText(RegisterCheckSuccessActivity.this, registerJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(status==1){
                        //如果成功
                        Toast.makeText(RegisterCheckSuccessActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterCheckSuccessActivity.this, MainActivity.class));
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        setContentView(R.layout.activity_registercheck_success);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.confirm).setVisibility(View.GONE);
        findViewById(R.id.btn_finish).setOnClickListener(this);
        editText_setpwd = (EditText) findViewById(R.id.editText_setpwd);
        editText_inputname = (EditText) findViewById(R.id.editText_inputname);
        editText_surepwd = (EditText) findViewById(R.id.editText_againinput);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_finish:
                if(editText_inputname.getText().toString().equals("")){
                    Toast.makeText(this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editText_setpwd.getText().toString().equals("")){
                    Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!editText_setpwd.getText().toString().equals(editText_surepwd.getText().toString())){
                    Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = editText_inputname.getText().toString();
                String password = editText_setpwd.getText().toString();
                //请求服务器注册3,参数：useid+用户名+密码
                ProgressDialogUtils.showProgressDialog(this, "加载中...");
                Bundle bundle = new Bundle();
                bundle.putString("useid",USERID);
                bundle.putString("name",name);
                bundle.putString("password",password);
                OkHttpTools.request(bundle, API.URL_Register_3,handler);

                break;
        }
    }

}
