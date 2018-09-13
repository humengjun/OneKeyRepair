package wit.hmj.onekeyrepair.my;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.model.FeedBackJson;
import wit.hmj.onekeyrepair.my.model.FeedBackInfo;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;
import wit.hmj.onekeyrepair.view.view.XListView;

public class MyFeedBackActivity extends BaseActivity implements XListView.IXListViewListener {

    private XListView xListView;
    private List<FeedBackInfo> infoList;
    private MyFeedBackAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(MyFeedBackActivity.this, "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_MyFeedBack:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    FeedBackJson feedBackJson = gson.fromJson(result,FeedBackJson.class);
                    int status = feedBackJson.getStatus();
                    //如果失败
                    if (status==0){
                        Toast.makeText(MyFeedBackActivity.this, feedBackJson.getMsg(), Toast.LENGTH_SHORT).show();
                    }else if (status==1){
                        //如果成功
                        infoList.clear();
                        List<FeedBackJson.DataBean> data = feedBackJson.getData();
                        for (FeedBackJson.DataBean dataBean:data) {
                            infoList.add(new FeedBackInfo(Name,dataBean.getContent(),dataBean.getTime(),imagePath));
                        }
                        //适应改变
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed_back);
        addData();
        initView();
        adapter = new MyFeedBackAdapter(infoList,this,loader,options);
        xListView.setAdapter(adapter);

    }

    private void addData() {
        if(imagePath==null){
            imagePath="";
        }
        //请求服务器获取反馈信息 参数useid
        ProgressDialogUtils.showProgressDialog(this, "加载中...");
        Bundle bundle = new Bundle();
        bundle.putString("useid",USERID);
        OkHttpTools.request(bundle, API.URL_MyFeedBack,handler);

        infoList = new ArrayList<>();
    }

    private void initView() {
        ((TextView)findViewById(R.id.title)).setText("我的反馈");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.confirm).setVisibility(View.GONE);
        xListView = (XListView) findViewById(R.id.xListView);
        //设置XlistView 的监听
        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
    }

    @Override
    public void onRefresh() {
        Date date = new Date();
        final String ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //刷新适配器，也就是说只要你的数据源发生了改变那么你就一定要刷新你的适配
                adapter.notifyDataSetChanged();
                //你得关闭下拉刷新
                xListView.stopRefresh();
                //设置刷新的时间
                xListView.setRefreshTime(ss);
            }
        });
    }

    @Override
    public void onLoadMore() {
        Date date = new Date();
        final String ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                xListView.stopLoadMore();
                xListView.setRefreshTime(ss);
            }
        });
    }
}
