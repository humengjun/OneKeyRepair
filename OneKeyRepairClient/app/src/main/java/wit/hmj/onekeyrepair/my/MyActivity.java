package wit.hmj.onekeyrepair.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.choose.SelectPhotoActivity;
import wit.hmj.onekeyrepair.choose.codes.Codes;
import wit.hmj.onekeyrepair.choose.utils.ImageUtils;
import wit.hmj.onekeyrepair.model.PersonInfoJson;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class MyActivity extends SelectPhotoActivity implements View.OnClickListener {

    private Button register;
    private TextView text_title, text_name, text_phone;
    private LinearLayout linear;
    private ImageView image;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(MyActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_PersonInfo:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    PersonInfoJson personInfoJson = gson.fromJson(result, PersonInfoJson.class);
                    int status = personInfoJson.getStatus();
                    //如果失败
                    if (status == 0) {
                        Toast.makeText(MyActivity.this, personInfoJson.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        //如果成功
                        Name = personInfoJson.getData().getName();
                        Phone = personInfoJson.getData().getPhone();
                        text_name.setText(personInfoJson.getData().getName());
                        text_phone.setText(personInfoJson.getData().getPhone());
                        if (imagePath != null && !imagePath.equals("")) {
                            loader.displayImage("File:///" + imagePath, image, options);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();
    }

    private void initView() {
        text_title = (TextView) findViewById(R.id.title);
        text_title.setText("个人中心");
        register = (Button) findViewById(R.id.confirm);
        register.setText("注册");
        register.setOnClickListener(this);
        findViewById(R.id.btn_myMessage).setOnClickListener(this);
        findViewById(R.id.btn_repairRecord).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
        findViewById(R.id.btn_feedback).setOnClickListener(this);
        findViewById(R.id.btn_about).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        text_name = (TextView) findViewById(R.id.text_name);
        text_phone = (TextView) findViewById(R.id.phone);
        linear = (LinearLayout) findViewById(R.id.linear);
        image = (ImageView) findViewById(R.id.imageView);
        if (LOGIN) {
            findViewById(R.id.NoLogin).setVisibility(View.GONE);
            linear.setVisibility(View.VISIBLE);
            //根据useid请求得到用户名，电话号码 参数useid
            ProgressDialogUtils.showProgressDialog(this, "加载中...");
            Bundle bundle = new Bundle();
            bundle.putString("useid", USERID);
            OkHttpTools.request(bundle, API.URL_PersonInfo, handler);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                //注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.back:
                finish();
                break;
            case R.id.btn_myMessage:
                //个人信息
                if (!LOGIN) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 110);
                } else {
                    Intent intent = new Intent(this, PersonInfoActivity.class);
                    String name = text_name.getText().toString();
                    String phone = text_phone.getText().toString();
                    Log.e("Name-Phone",(name==null)+"");
					if(name==null||name.equals("")){
						return;
					}
                    intent.putExtra("name", name);
                    intent.putExtra("phone", phone);
                    startActivityForResult(intent, 109);
                }
                break;
            case R.id.btn_repairRecord:
                //我的报修
                if (!LOGIN) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 110);
                } else {
                    startActivity(new Intent(this, MyRepairActivity.class));
                }
                break;
            case R.id.btn_setting:
                //设置
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.btn_feedback:
                //我的反馈
                if (!LOGIN) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 110);
                } else {
                    startActivity(new Intent(this, MyFeedBackActivity.class));
                }
                break;
            case R.id.btn_about:
                //关于我们
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String usename = data.getStringExtra("usename");
            String phone = data.getStringExtra("phone");
            String imagePath = data.getStringExtra("image");

            if (usename != null) {
                text_name.setText(usename);
            }
            if (phone != null) {
                text_phone.setText(phone);
            }
            if (imagePath != null && !imagePath.equals("")) {
                //image
                Bitmap bitmap = ImageUtils.getImageThumbnail(imagePath, ImageUtils.getWidth(this) / 4 - 20, ImageUtils.getWidth(this) / 4 - 20);
                image.setImageBitmap(bitmap);
            }
        }
    }
}
