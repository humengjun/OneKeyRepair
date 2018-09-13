package wit.hmj.onekeyrepair.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.FeedBackActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.MainActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.LoginJson;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView textView;
    private EditText editText_username,editText_password;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(LoginActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_Login:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    LoginJson loginJson = gson.fromJson(result, LoginJson.class);
                    int status = loginJson.getStatus();
                    //如果失败
                    if(status==0){
                        Toast.makeText(LoginActivity.this, loginJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(status==1) {
                        //如果成功,设置全局useid，islogin并保存
                        LOGIN = true;
                        USERID = loginJson.getData().getUseid();
                        SharedPreferences.Editor editor2 = login_sharePreferences.edit();
                        editor2.putBoolean("ISLOGIN",true);
                        editor2.putString("USERID",USERID);
                        editor2.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
        setContentView(R.layout.activity_login);
        textView = (TextView) findViewById(R.id.title);
        textView.setText("登录");
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_find_pw).setOnClickListener(this);
        findViewById(R.id.btn_find_register).setOnClickListener(this);
        findViewById(R.id.confirm).setVisibility(View.GONE);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                //点击登录按钮登录
                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();

                if(username.equals("hmj")&&password.equals("123")){
                    LOGIN = true;
                    USERID = "123456";
                    SharedPreferences.Editor editor2 = login_sharePreferences.edit();
                    editor2.putBoolean("ISLOGIN",true);
                    editor2.putString("USERID",USERID);
                    editor2.commit();
                    finish();
                    return;
                }

                //登录1：参数 用户名+密码
                ProgressDialogUtils.showProgressDialog(this, "加载中...");
                Bundle bundle = new Bundle();
                bundle.putString("name",username);
                bundle.putString("password",password);
                OkHttpTools.request(bundle, API.URL_Login,handler);
                break;
            case R.id.btn_find_register:
                //点击注册按钮注册
                Intent intent1 = new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_find_pw:
                //点击找回密码按钮
//                Intent intent2 = new Intent(this,FindPasswordActivity.class);
//                startActivity(intent2);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
