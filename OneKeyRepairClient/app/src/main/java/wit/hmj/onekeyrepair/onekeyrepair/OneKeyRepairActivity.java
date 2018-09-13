package wit.hmj.onekeyrepair.onekeyrepair;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zaaach.citypicker.CityPickerActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.FeedBackActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.MainActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.my.ModifyNameActivity;
import wit.hmj.onekeyrepair.utils.MediaManager;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;
import wit.hmj.onekeyrepair.utils.Recorder;
import wit.hmj.onekeyrepair.view.AudioRecorderButton;

import static wit.hmj.onekeyrepair.R.id.view_speak;

public class OneKeyRepairActivity extends BaseActivity implements View.OnClickListener {

    TextView text_title, text_city, text_address;
    EditText edit_thing, edit_phone;
    GridView grid_photo;
    AudioRecorderButton audio;

    Recorder recorder;

    RelativeLayout relativeLayout;
    TextView textView_time, textView_delete, textView_ballon;
    private int mMinItemWidhth, mxScreen_width;
    private int mMaxItemWidhth, myScreen_hight;
    ImageView view_speak;
    private View photoView;
    private DisplayMetrics outMetrics;
    private File fileName;
    private PopupWindow popupWindow_photo;
    private QuestionAdapter adapter;

    private ArrayList<String> list;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(OneKeyRepairActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_Repair:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    RegisterJson registerJson = gson.fromJson(result, RegisterJson.class);
                    int status = registerJson.getStatus();
                    //如果失败
                    if (status==0){
                        Toast.makeText(OneKeyRepairActivity.this, registerJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(status==1) {
                        Toast.makeText(OneKeyRepairActivity.this, "报修成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_repair);
        list = new ArrayList<>();
        initWindow();
        initView();
        String thing=getIntent().getStringExtra("thing");
        if(thing!=null){
            edit_thing.setText(thing);
        }
    }

    private void initView() {
        findViewById(R.id.confirm).setVisibility(View.GONE);
        text_title = (TextView) findViewById(R.id.title);
        text_city = (TextView) findViewById(R.id.text_city);
        text_address = (TextView) findViewById(R.id.text_address);
        text_title.setText("一键报修");
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_city).setOnClickListener(this);
        findViewById(R.id.btn_address).setOnClickListener(this);
        audio = (AudioRecorderButton) findViewById(R.id.btn_say);
        view_speak = (ImageView) findViewById(R.id.view_speak);
        findViewById(R.id.takePhoto).setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        grid_photo = (GridView) findViewById(R.id.gridView_photo);

        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_thing = (EditText) findViewById(R.id.editText_wuye_content);

        adapter = new QuestionAdapter(this, list, outMetrics.widthPixels / 4, outMetrics.heightPixels / 11, loader, options);
        grid_photo.setAdapter(adapter);
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OneKeyRepairActivity.this, PhotoviewActivity.class);
                String[] strings = new String[list.size()];
                list.toArray(strings);
                intent.putExtra("images",strings);
                intent.putExtra("photo_position",i);
                startActivityForResult(intent,101);
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.relative_recoder);
        textView_time = (TextView) findViewById(R.id.tv_time);
        textView_delete = (TextView) findViewById(R.id.tv_delete);
        textView_delete.setOnClickListener(this);
        textView_ballon = (TextView) findViewById(R.id.tv_balloon1);

        audio.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float time, String filePath) {
                relativeLayout.setVisibility(View.VISIBLE);
                recorder = new Recorder((int) time, filePath);
                textView_time.setText(recorder.getTime() + "''");
                ViewGroup.LayoutParams layoutParams = textView_ballon.getLayoutParams();
                layoutParams.width = (int) (mMinItemWidhth + (mMaxItemWidhth / 60f * recorder.getTime()));
                textView_ballon.setLayoutParams(layoutParams);
                textView_ballon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view_speak.setBackgroundResource(R.drawable.play_anim);
                        AnimationDrawable anim = (AnimationDrawable) view_speak.getBackground();
                        anim.start();

                        // 播放音频
                        MediaManager.playSound(recorder.getFilePath(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                view_speak.setBackgroundResource(R.mipmap.chatfrom_voice_playing);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_selectPhotoes:
//                Intent intent1 = new Intent(this, PhotoWallActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putStringArrayList("data", list);
//                intent1.putExtras(bundle1);
//                startActivityForResult(intent1, 2);
                break;
            case R.id.tv_takePhotoes:
                takePhotoes();
                break;
            case R.id.btn_city:
                startActivityForResult(new Intent(this, CityPickerActivity.class), 103);
                break;
            case R.id.btn_address:
                startActivityForResult(new Intent(this, LocationActivity.class), 102);
                break;
            case R.id.takePhoto:
                if (list.size() >= 5) {
                    Toast.makeText(this, "最多选择五张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                popupWindow_photo.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case R.id.tv_delete:
                relativeLayout.setVisibility(View.GONE);
                recorder = null;
                break;
            case R.id.btn_commit:
                String phone = edit_phone.getText().toString();
                String thing = edit_thing.getText().toString();
                String city = text_city.getText().toString();
                String address = text_address.getText().toString();
                if (phone == null || !isMobileNO(phone)) {
                    Toast.makeText(this, "手机号码格式错误！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (thing == null || thing.equals("")) {
                    Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (city.equals("请选择")) {
                    Toast.makeText(this, "请输入城市！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (address.equals("请选择")) {
                    Toast.makeText(this, "请输入详细地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(list.size()<=0){
                    Toast.makeText(this, "至少添加一张图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交服务器参数，useid+手机号码+内容+城市+详细地址+图片+（语音）括号可有可无
                ProgressDialogUtils.showProgressDialog(this, "加载中...");
                Bundle bundle = new Bundle();
                bundle.putString("useid",USERID);
                bundle.putString("phone",phone);
                bundle.putString("thing",thing);
                bundle.putString("city",city);
                bundle.putString("address",address);
                bundle.putStringArrayList("image",list);
                OkHttpTools.request(bundle, API.URL_Repair,handler);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        popupWindow_photo.dismiss();
        if (requestCode == 101) {
            if (data == null) {
                return;
            }
            list.clear();
            String[] strs = data.getStringArrayExtra("uri");
            if(strs.length!=0){
                for (int i = 0;i<strs.length;i++){
                    list.add(strs[i]);
                }
            }else {
                grid_photo.setVisibility(View.GONE);
            }
        } if (requestCode == 103) {
            if (data == null) {
                return;
            }
            //选择城市
            String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
            text_city.setText(city);
        } else if (requestCode == 102) {
            if (data == null) {
                return;
            }
            //详细地址
            String address = data.getStringExtra("address");
            text_address.setText(address);
        } else if (requestCode == 1) {
            //拍照
            if (fileName.exists()) {
                list.add(fileName.getPath());
            }
        } else if (requestCode == 2) {
            if (data == null) {
                return;
            }
            //选择照片
            if (data != null) {
                Bundle bundle = data.getExtras();
                list = bundle.getStringArrayList("data");
            }
        }
        adapter.notifyDataSetChanged();

    }

    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        System.out.println(m.matches() + "---");

        return m.matches();

    }

    private void initWindow() {
        photoView = getLayoutInflater().inflate(R.layout.take_picture, null);
        photoView.findViewById(R.id.tv_selectPhotoes).setOnClickListener(this);
        photoView.findViewById(R.id.tv_takePhotoes).setOnClickListener(this);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidhth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidhth = (int) (outMetrics.widthPixels * 0.15f);

        mxScreen_width = outMetrics.widthPixels / 9 * 8;
        myScreen_hight = outMetrics.heightPixels / 5;

        popupWindow_photo = new PopupWindow(this);
        popupWindow_photo.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow_photo.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow_photo.setOutsideTouchable(true);
        popupWindow_photo.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow_photo.setBackgroundDrawable(dw);
        popupWindow_photo.setContentView(photoView);
    }

    private void takePhotoes() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory() + "/" + "Camera/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";
            fileName = new File(path, name);
            Uri uri = Uri.fromFile(fileName);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }
}
