package wit.hmj.onekeyrepair.my;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.my.fragment.BaseFragment;
import wit.hmj.onekeyrepair.my.fragment.FirstFragment;
import wit.hmj.onekeyrepair.my.fragment.FourFragment;
import wit.hmj.onekeyrepair.my.fragment.SecondFragment;
import wit.hmj.onekeyrepair.my.fragment.ThirdFragment;

public class MyRepairActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_1,btn_2,btn_3,btn_4;
    private View view_1,view_2,view_3,view_4;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_repair);
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.title)).setText("我的报修");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.confirm).setVisibility(View.GONE);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        view_4 = findViewById(R.id.view_4);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        //1.获取Fragment的管理者
        manager = getFragmentManager();
        replace(new FirstFragment());
    }


    public void replace(BaseFragment fragment){

        //2.开启Fragment的事务 ,通过管理者开启事务
        transaction = manager.beginTransaction();
        //事务 将Fragment 去替换layout容器
        transaction.replace(R.id.fragment, fragment);
        //事务必须要提交
        transaction.commit();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                //全部
                restoreColor();
                btn_1.setTextColor(getResources().getColor(R.color.colorYellow));
                view_1.setBackgroundResource(R.color.colorYellow);
                replace(new FirstFragment());
                break;
            case R.id.btn_2:
                //待审核
                restoreColor();
                btn_2.setTextColor(getResources().getColor(R.color.colorYellow));
                view_2.setBackgroundResource(R.color.colorYellow);
                replace(new SecondFragment());
                break;
            case R.id.btn_3:
                //待维修
                restoreColor();
                btn_3.setTextColor(getResources().getColor(R.color.colorYellow));
                view_3.setBackgroundResource(R.color.colorYellow);
                replace(new ThirdFragment());
                break;
            case R.id.btn_4:
                //已完成
                restoreColor();
                btn_4.setTextColor(getResources().getColor(R.color.colorYellow));
                view_4.setBackgroundResource(R.color.colorYellow);
                replace(new FourFragment());
                break;
        }
    }

    private void restoreColor() {
        view_1.setBackgroundResource(R.color.colorWhite);
        view_2.setBackgroundResource(R.color.colorWhite);
        view_3.setBackgroundResource(R.color.colorWhite);
        view_4.setBackgroundResource(R.color.colorWhite);
        btn_1.setTextColor(getResources().getColor(R.color.colorBlack));
        btn_2.setTextColor(getResources().getColor(R.color.colorBlack));
        btn_3.setTextColor(getResources().getColor(R.color.colorBlack));
        btn_4.setTextColor(getResources().getColor(R.color.colorBlack));
    }
}
