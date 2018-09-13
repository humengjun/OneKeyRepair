package wit.hmj.onekeyrepair.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.MainActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.choose.SelectPhotoActivity;
import wit.hmj.onekeyrepair.choose.codes.Codes;
import wit.hmj.onekeyrepair.choose.utils.ImageUtils;

public class PersonInfoActivity extends SelectPhotoActivity implements View.OnClickListener {

    private Button modify_name;
    private TextView text_title, text_phone;
    private ImageView headImg;
    String[] proj = {MediaStore.MediaColumns.DATA};

    String name = "";
    String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");

        setContentView(R.layout.activity_person_info);
        initView();

        modify_name.setText(Name);
        text_phone.setText(Phone);

        if (imagePath != null && !imagePath.equals("")) {
            loader.displayImage("File:///" + imagePath, headImg, options);
        }

    }

    private void initView() {
        modify_name = (Button) findViewById(R.id.modify_name);
        headImg = (ImageView) findViewById(R.id.headImg);
        findViewById(R.id.confirm).setVisibility(View.GONE);
        text_title = (TextView) findViewById(R.id.title);
        text_title.setText("个人信息");
        text_phone = (TextView) findViewById(R.id.text_phone);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.modify_pwd).setOnClickListener(this);
        findViewById(R.id.btn_returnLogin).setOnClickListener(this);
        headImg.setOnClickListener(this);
        modify_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_name:
                Intent intent1 = new Intent(this, ModifyNameActivity.class);
                intent1.putExtra("name", modify_name.getText().toString());
                startActivityForResult(intent1, 1);
                break;
            case R.id.modify_pwd:
                startActivityForResult(new Intent(this, ModifyPassWordActivity.class), 2);
                break;
            case R.id.back:
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("image", imagePath);
                setResult(101, intent);
                finish();
                break;
            case R.id.btn_returnLogin:
                LOGIN = false;
                USERID = null;
                SharedPreferences.Editor editor2 = login_sharePreferences.edit();
                editor2.putBoolean("ISLOGIN", false);
                editor2.putString("USERID", USERID);
                editor2.commit();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.headImg:
                showPicturePopupWindow(v, Codes.POP_BOTTOM);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_RESULT_CODE && resultCode == RESULT_OK) {

            Uri uri = null;
            if (data != null && data.getData() != null) {// 有数据返回直接使用返回的图片地址
                uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, proj, null,
                        null, null);
                if (cursor == null) {
                    uri = ImageUtils.getUri(this, data);
                }
                imagePath = ImageUtils.getFilePathByFileUri(this, uri);
            } else {// 无数据使用指定的图片路径
                imagePath = mImagePath;
            }
            Log.e("image111", imagePath);
            File file = new File(imagePath);
            String fileName = file.getName();
            loader.displayImage("File:///" + imagePath, headImg, options);
            SharedPreferences.Editor editor2 = image_share.edit();
            editor2.putString("IMGPATH", imagePath);
            editor2.commit();
            //上传图片到服务器
        }
        if (requestCode == 1 && resultCode == 1) {
            if (data != null) {
                name = data.getStringExtra("name");
                modify_name.setText(name);

            }
        }
    }
}
