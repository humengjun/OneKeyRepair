package wit.hmj.onekeyrepair.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.FeedBackActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.my.model.FragmentInfo;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;

public class RepairDetailsActivity extends BaseActivity implements View.OnClickListener {

    private TextView text_title, text_name, text_phone, text_workerNumber, text_address, text_time,
            text_thing, text_workerName, text_workerPhone, text_finish;

    private ImageView headImage, workerImage;

    private RecyclerView recyclerView;
    private FragmentInfo fragmentInfo;
    private RepairDetailsAdapter adapter;

    private RatingBar ratingBar;
    private EditText edit_evaluate;
    private Button btn_evaluate;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(RepairDetailsActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_Evaluation:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    RegisterJson registerJson = gson.fromJson(result, RegisterJson.class);
                    int status = registerJson.getStatus();
                    //如果失败
                    if (status==0){
                        Toast.makeText(RepairDetailsActivity.this, registerJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if(status==1) {
                        Toast.makeText(RepairDetailsActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentInfo = (FragmentInfo) getIntent().getSerializableExtra("FragmentInfo");
        setContentView(R.layout.activity_repair_details_ativity);
        initView();
        if (fragmentInfo != null) {
            if(fragmentInfo.getState()==1){
                findViewById(R.id.worker).setVisibility(View.GONE);
                text_workerNumber.setVisibility(View.GONE);
            }else {
                text_workerNumber.append(fragmentInfo.getWorkNumber());
                text_workerName.setText(fragmentInfo.getWorkerName());
                text_workerPhone.append(fragmentInfo.getWorkerPhone());
                text_finish.append((fragmentInfo.getState() == 3 ? "是" : "否"));
            }
            text_name.setText(Name);
            text_phone.append(fragmentInfo.getPhone());
            text_address.append(fragmentInfo.getAddress());
            text_time.append(fragmentInfo.getTime());
            text_thing.append(fragmentInfo.getThing());
            loader.displayImage("File:///" + imagePath, headImage, options);
            loader.displayImage("File:///" + imagePath, workerImage, options);
            adapter = new RepairDetailsAdapter(fragmentInfo.getImageList(), this, loader, options);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);

            if(fragmentInfo.getState()==3){
                findViewById(R.id.linear_score).setVisibility(View.VISIBLE);
                findViewById(R.id.linear_evaluation).setVisibility(View.VISIBLE);
                btn_evaluate.setVisibility(View.VISIBLE);
                //如果已评价设置ratingBar和edit_evaluate,并且设置btn_evaluate为不可见
                String score = fragmentInfo.getScore();
                String evaluation = fragmentInfo.getEvaluation();
                if(score!=null&&!score.equals("")){
                    ratingBar.setRating(Float.valueOf(score));
                    edit_evaluate.setText(evaluation);
                    edit_evaluate.setEnabled(false);
                    btn_evaluate.setVisibility(View.GONE);
                }

            }
        }

    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_call).setOnClickListener(this);
        findViewById(R.id.confirm).setVisibility(View.GONE);
        text_title = (TextView) findViewById(R.id.title);
        text_title.setText("报修详情");
        text_name = (TextView) findViewById(R.id.text_name);
        text_phone = (TextView) findViewById(R.id.text_phone);
        text_workerNumber = (TextView) findViewById(R.id.text_workerNumber);
        text_address = (TextView) findViewById(R.id.text_address);
        text_workerName = (TextView) findViewById(R.id.text_workerName);
        text_workerPhone = (TextView) findViewById(R.id.text_workerPhone);
        text_finish = (TextView) findViewById(R.id.text_finish);
        text_time = (TextView) findViewById(R.id.text_time);
        text_thing = (TextView) findViewById(R.id.text_thing);
        headImage = (ImageView) findViewById(R.id.headImg);
        workerImage = (ImageView) findViewById(R.id.workerImg);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ratingBar = (RatingBar) findViewById(R.id.score);
        edit_evaluate = (EditText) findViewById(R.id.edit_evaluate);
        btn_evaluate = (Button) findViewById(R.id.btn_evaluate);
        btn_evaluate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            finish();
        } else if (v.getId() == R.id.btn_call) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + fragmentInfo.getWorkerPhone()));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }else if(v.getId() == R.id.btn_evaluate){
            float score = ratingBar.getRating();
            String evaluation = edit_evaluate.getText().toString();
            if(score==0){
                Toast.makeText(this,"请评星，最低为半颗星！",Toast.LENGTH_SHORT).show();
                return;
            }
            if(evaluation.equals("")){
                Toast.makeText(this,"请评价！",Toast.LENGTH_SHORT).show();
                return;
            }
            Log.i("evaluation","ratingbar："+score+"，evaluation："+evaluation);
            //把评价提交到服务器 参数useid+内容
            ProgressDialogUtils.showProgressDialog(RepairDetailsActivity.this, "加载中...");
            Bundle bundle = new Bundle();
            bundle.putString("workNumber",fragmentInfo.getWorkNumber());
            bundle.putFloat("score",score);
            bundle.putString("evaluation",evaluation);
            OkHttpTools.request(bundle, API.URL_Evaluation,handler);
        }
    }
}
