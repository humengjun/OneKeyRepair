package wit.hmj.onekeyrepair.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.LoginJson;
import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText editText_phone, editText_receive;
    private Button btn_receive, btn_next, btn_service;
    private CheckBox checkBox;
    private TextView textView;
    private int i = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(RegisterActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_Register_1:
                    String result1 = (String) msg.obj;
                    //解析json，判断返回码
                    RegisterJson registerJson = gson.fromJson(result1, RegisterJson.class);
                    int status_1 = registerJson.getStatus();
                    //如果失败
                    if (status_1 == 0) {
                        Toast.makeText(RegisterActivity.this, registerJson.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if (status_1 == 1) {
                        //如果成功
                        Toast.makeText(RegisterActivity.this, "验证发送成功，请查收！", Toast.LENGTH_SHORT).show();
                        editText_receive.setText(registerJson.getData().getReceive());
                        setText();
                    }
                    break;
                case HandlerCodes.Code_Register_2:
                    String result2 = (String) msg.obj;
                    //解析json，判断返回码
                    LoginJson loginJson = gson.fromJson(result2, LoginJson.class);
                    int status_2 = loginJson.getStatus();
                    //如果失败
                    if (status_2 == 0) {
                        Toast.makeText(RegisterActivity.this, loginJson.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if (status_2 == 1) {
                        //如果成功,暂时设置全局useid
                        USERID = loginJson.getData().getUseid();
                        SharedPreferences.Editor editor2 = login_sharePreferences.edit();
                        editor2.putString("USERID",USERID);
                        editor2.commit();
                        startActivity(new Intent(RegisterActivity.this, RegisterCheckSuccessActivity.class));
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
        setContentView(R.layout.activity_register);
        initView();
        editText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText_phone.getText().toString().length() == 11) {
                    btn_receive.setEnabled(true);
                    btn_receive.setBackgroundResource(R.color.colorSelectSocial_btn_2);
                } else {
                    btn_receive.setEnabled(false);
                    btn_receive.setBackgroundResource(R.color.colorRegisterButton);
                }
            }
        });
        editText_receive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtn_next();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setBtn_next();
            }
        });
    }

    private void initView() {
        editText_phone = (EditText) findViewById(R.id.editText_phone);
        editText_receive = (EditText) findViewById(R.id.editText_receive);
        btn_service = (Button) findViewById(R.id.btn_service);
        btn_receive = (Button) findViewById(R.id.btn_receive);
        btn_next = (Button) findViewById(R.id.btn_next);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        btn_service = (Button) findViewById(R.id.btn_service);
        textView = (TextView) findViewById(R.id.title);
        textView.setText("注册");
        findViewById(R.id.confirm).setVisibility(View.GONE);
        btn_service.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_receive.setOnClickListener(this);
        btn_next.setEnabled(false);
        btn_receive.setEnabled(false);
        findViewById(R.id.back).setOnClickListener(this);
    }

    public void setBtn_next() {
        String phoneNumber = editText_phone.getText().toString();
        Pattern pattern = Pattern.compile(phoneReg);
        Matcher m = pattern.matcher(phoneNumber);
        if (m.find() && editText_receive.getText().toString().length() != 0 && checkBox.isChecked()) {
            btn_next.setEnabled(true);
            btn_next.setBackgroundResource(R.color.colorSelectSocial_btn_2);
        } else {
            btn_next.setEnabled(false);
            btn_next.setBackgroundResource(R.color.colorRegisterButton);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_receive:
                //获取验证码
                String phone = editText_phone.getText().toString();
                Pattern pattern = Pattern.compile(phoneReg);
                Matcher m = pattern.matcher(phone);
                if (!m.find()) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                i = 30;
                //注册1，参数 11位手机号
                ProgressDialogUtils.showProgressDialog(this, "加载中...");
                Bundle bundle1 = new Bundle();
                bundle1.putString("phone", phone);
                try {
                    OkHttpTools.request(bundle1, API.URL_Register_1, handler);
                } catch (Exception exception) {
                    Toast.makeText(this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_next:
                //下一步
                String myPhone = editText_phone.getText().toString();
                String receive = editText_receive.getText().toString();

                //注册2,参数 手机号+验证码
                ProgressDialogUtils.showProgressDialog(this, "加载中...");
                Bundle bundle2 = new Bundle();
                bundle2.putString("phone", myPhone);
                bundle2.putString("receive", receive);
                try {
                    OkHttpTools.request(bundle2, API.URL_Register_2, handler);
                } catch (Exception exception) {
                    Toast.makeText(this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back:
                //返回按钮
                finish();
                break;
        }
    }

    private void setText() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 30; j++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            i--;
                            btn_receive.setText(i + "秒后重试");
                            if (i == 0) {
                                btn_receive.setEnabled(true);
                                btn_receive.setText("重新获取验证码");
                                btn_receive.setBackgroundResource(R.color.colorSelectSocial_btn_2);
                            } else {
                                btn_receive.setEnabled(false);
                                btn_receive.setBackgroundResource(R.color.colorRegisterButton);
                            }
                        }
                    });
                }
            }
        }).start();
    }

}
