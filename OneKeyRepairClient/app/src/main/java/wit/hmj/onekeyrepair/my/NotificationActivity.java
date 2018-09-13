package wit.hmj.onekeyrepair.my;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;

public class NotificationActivity extends BaseActivity implements View.OnClickListener{
    private ContentResolver resolver;
    private ListView listView_tzls;
    private NotficationAdapter dapter;
    private int UpPosition;
    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private ImageView imageView;
    private List<Ringtone> resArr;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        share=getSharedPreferences("tzls",MODE_PRIVATE);
        UpPosition = share.getInt("UpPosition", 0);
        editor=share.edit();
        inits();
        resArr = getRingtoneList(RingtoneManager.TYPE_NOTIFICATION);
        dapter=new NotficationAdapter(this,resArr,UpPosition);
        listView_tzls.setAdapter(dapter);

        listView_tzls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imageView= (ImageView) listView_tzls.getChildAt(UpPosition).findViewById(R.id.imageView_tzls);
                imageView.setBackgroundResource(R.mipmap.pressed);
                imageView= (ImageView) view.findViewById(R.id.imageView_tzls);
                imageView.setBackgroundResource(R.mipmap.checked);
                UpPosition=i;
                resArr.get(i).play();
                name = resArr.get(i).getTitle(NotificationActivity.this);
            }
        });
    }

    private void inits() {
        listView_tzls= (ListView) findViewById(R.id.listView_tzls);
        ((TextView)findViewById(R.id.title)).setText("新消息提示音");
        findViewById(R.id.back).setOnClickListener(this);
        ((Button)findViewById(R.id.confirm)).setText("保存");
        ((Button)findViewById(R.id.confirm)).setOnClickListener(this);
        resolver = getContentResolver();
    }

    public List<Ringtone> getRingtoneList(int type){

        List<Ringtone> resArr = new ArrayList<Ringtone>();

        RingtoneManager manager = new RingtoneManager(this);

        manager.setType(type);

        Cursor cursor = manager.getCursor();

        int count = cursor.getCount();

        for(int i = 0 ; i < count ; i ++){

            resArr.add(manager.getRingtone(i));

        }

        return resArr;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                editor.putInt("UpPosition", UpPosition);
                editor.commit();
                Intent intent=new Intent();
                intent.putExtra("name",name);
                setResult(110,intent);
                finish();
                break;
        }
    }
}
