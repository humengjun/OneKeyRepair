package wit.hmj.onekeyrepair.my;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout relative_sy,relative_zd,relative_tzls;
    private Button btn_xxtz,btn_sy,btn_zd;
    private TextView textView_tzls;
    private SharedPreferences share,sharedPreferences;
    private boolean Isxxtz;
    private boolean Issy;
    private boolean Iszd;
    private SharedPreferences.Editor editor;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferences = getSharedPreferences("sjls",MODE_PRIVATE);
        inits();
    }

    @Override
    protected void onStart() {
        super.onStart();
        name = sharedPreferences.getString("name","");
        textView_tzls.setText(name);
    }

    private void inits() {
        findViewById(R.id.confirm).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.title)).setText("设置");
        btn_xxtz= (Button) findViewById(R.id.btn_xxtz);
        btn_sy= (Button) findViewById(R.id.btn_sy);
        btn_zd= (Button) findViewById(R.id.btn_zd);
        findViewById(R.id.relative_jcxbb).setOnClickListener(this);
        relative_sy= (RelativeLayout) findViewById(R.id.relative_sy);
        relative_zd= (RelativeLayout) findViewById(R.id.relative_zd);
        relative_tzls= (RelativeLayout) findViewById(R.id.relative_tzls);
        textView_tzls= (TextView) findViewById(R.id.textView_tzls);
        btn_xxtz.setOnClickListener(this);
        btn_sy.setOnClickListener(this);
        btn_zd.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        relative_sy.setOnClickListener(this);
        relative_zd.setOnClickListener(this);
        relative_tzls.setOnClickListener(this);
        share=getSharedPreferences("sz",MODE_APPEND);
        editor=share.edit();
        Isxxtz=share.getBoolean("Isxxtz",true);
        XXTZ();
        Issy=share.getBoolean("Issy",true);
        SY();
        Iszd=share.getBoolean("Iszd",true);
        if(Iszd){
            btn_zd.setBackground(getResources().getDrawable(R.mipmap.on350));
        }else{
            btn_zd.setBackground(getResources().getDrawable(R.mipmap.off350));
        }
    }

    private void start() {
        btn_xxtz.setBackground(getResources().getDrawable(R.mipmap.on350));
        btn_sy.setBackground(getResources().getDrawable(R.mipmap.on350));
        btn_zd.setBackground(getResources().getDrawable(R.mipmap.on350));
        relative_sy.setVisibility(View.VISIBLE);
        relative_zd.setVisibility(View.VISIBLE);
        relative_tzls.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_xxtz:
                Isxxtz=!Isxxtz;
                editor.putBoolean("Isxxtz",Isxxtz);
                editor.commit();
                XXTZ();
                break;
            case R.id.btn_sy:
                Issy=!Issy;
                editor.putBoolean("Issy",Issy);
                editor.commit();
                SY();
                break;
            case R.id.btn_zd:
                Iszd=!Iszd;
                editor.putBoolean("Iszd",Iszd);
                editor.commit();
                if(Iszd){
                    btn_zd.setBackground(getResources().getDrawable(R.mipmap.on350));
                    Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
                    vibrator.vibrate(new long[]{0,1000}, -1);
                }else{
                    btn_zd.setBackground(getResources().getDrawable(R.mipmap.off350));
                }
                break;
            case R.id.relative_tzls:
                Intent intent=new Intent(this,NotificationActivity.class);
                startActivityForResult(intent,100);
                break;
            case R.id.relative_jcxbb:
                Toast.makeText(this,"当前已是最新版本！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void XXTZ() {
        if(Isxxtz){
            start();
        }else{
            btn_xxtz.setBackground(getResources().getDrawable(R.mipmap.off350));
            relative_sy.setVisibility(View.GONE);
            relative_zd.setVisibility(View.GONE);
            relative_tzls.setVisibility(View.GONE);
        }
    }
    private void SY() {
        if(Issy){
            btn_sy.setBackground(getResources().getDrawable(R.mipmap.on350));
            relative_tzls.setVisibility(View.VISIBLE);
        }else{
            btn_sy.setBackground(getResources().getDrawable(R.mipmap.off350));
            relative_tzls.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            name = data.getStringExtra("name");
            textView_tzls.setText(name);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name",name);
            editor.commit();
        }
    }
}
