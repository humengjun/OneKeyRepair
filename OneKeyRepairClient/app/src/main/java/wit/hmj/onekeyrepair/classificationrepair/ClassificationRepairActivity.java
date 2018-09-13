package wit.hmj.onekeyrepair.classificationrepair;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.onekeyrepair.OneKeyRepairActivity;
import wit.hmj.onekeyrepair.utils.DividerItemDecoration;

public class ClassificationRepairActivity extends BaseActivity implements View.OnClickListener,CallBack {
    TextView text_title;
    EditText edit_seek;
    Button cancel;
    ImageButton seek;
    GridView gridView;
    private List<String> stringList;
    private List<Integer> integerList;
    private PopupWindow popupWindow;
    private RecyclerView recylerView;
    private List<String> popStringList;
    RecyclerAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_repair);
        addData();
        initView();
        MyAdapter mAdapter = new MyAdapter(stringList,integerList,this);
        gridView.setAdapter(mAdapter);
        initWindow();

    }

    private void initWindow() {
        View v = getLayoutInflater().inflate(R.layout.popup_window_classification_repair, null);
        popupWindow = new PopupWindow(v, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);
        ColorDrawable dw = new ColorDrawable(0x8000000);
        popupWindow.setBackgroundDrawable(dw);
        recylerView = v.findViewById(R.id.recyclerView);
        rAdapter = new RecyclerAdapter(popStringList,this,this);
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setAdapter(rAdapter);
        recylerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    }

    private void addData() {
        popStringList = new ArrayList<>();
        stringList = new ArrayList<>();
        integerList = new ArrayList<>();
        stringList.add(getResources().getString(R.string.type_bgzy));
        stringList.add(getResources().getString(R.string.type_kt));
        stringList.add(getResources().getString(R.string.type_xyj));
        stringList.add(getResources().getString(R.string.type_dj));
        stringList.add(getResources().getString(R.string.type_dn));
        stringList.add(getResources().getString(R.string.type_dsj));
        stringList.add(getResources().getString(R.string.type_dyj));
        stringList.add(getResources().getString(R.string.type_sg));
        integerList.add(R.drawable.bgzy);
        integerList.add(R.drawable.kt);
        integerList.add(R.drawable.xyj);
        integerList.add(R.drawable.dj);
        integerList.add(R.drawable.dn);
        integerList.add(R.drawable.dsj);
        integerList.add(R.drawable.dyj);
        integerList.add(R.drawable.sg);
    }

    private void initView() {
        text_title= (TextView) findViewById(R.id.title);
        text_title.setText("分类报修");
        findViewById(R.id.confirm).setVisibility(View.GONE);
        seek= (ImageButton) findViewById(R.id.seek);
        cancel= (Button) findViewById(R.id.cancel);
        gridView = (GridView) findViewById(R.id.gridView);
        seek.setVisibility(View.VISIBLE);
        seek.setOnClickListener(this);
        cancel.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        edit_seek = (EditText) findViewById(R.id.edit_seek);
        edit_seek.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                popStringList.clear();
                String editString=s.toString();
                for (int i = 0; i < stringList.size(); i++) {
                    if(editString!=null&&!editString.equals("")&&stringList.get(i).contains(editString)){
                        popStringList.add(stringList.get(i)+"报修");
                    }
                }
                rAdapter.notifyDataSetChanged();
                if(popStringList.size()>0){
                    popupWindow.showAtLocation(edit_seek, Gravity.TOP,0,150);
                }else {
                    popupWindow.dismiss();
                }

            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ClassificationRepairActivity.this, OneKeyRepairActivity.class);
                intent.putExtra("thing",stringList.get(position)+"报修");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seek:
                seek.setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                edit_seek.setVisibility(View.VISIBLE);
                break;
            case R.id.cancel:
                seek.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.GONE);
                edit_seek.setVisibility(View.GONE);
                edit_seek.setText("");
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void sendData(String data) {
        popupWindow.dismiss();
        popStringList.clear();
        //跳转
        Intent intent = new Intent(this, OneKeyRepairActivity.class);
        intent.putExtra("thing",data);
        startActivity(intent);
    }
}
