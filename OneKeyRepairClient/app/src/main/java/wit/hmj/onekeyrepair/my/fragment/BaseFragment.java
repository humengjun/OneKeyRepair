package wit.hmj.onekeyrepair.my.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.HandlerCodes;
import wit.hmj.onekeyrepair.model.RegisterJson;
import wit.hmj.onekeyrepair.model.RepairJson;
import wit.hmj.onekeyrepair.my.ModifyNameActivity;
import wit.hmj.onekeyrepair.my.model.FragmentInfo;
import wit.hmj.onekeyrepair.utils.OkHttpTools;
import wit.hmj.onekeyrepair.utils.ProgressDialogUtils;
import wit.hmj.onekeyrepair.view.view.XListView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class BaseFragment extends Fragment implements XListView.IXListViewListener {

    public List<FragmentInfo> infoList= new ArrayList<>();
    public FragmentAdapter adapter;

    public XListView xListView;
    //int state, String thing, String name, String phone, String address, String time, String workNumber,
    // String workerName, String workerPhone
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            //你得关闭下拉刷新
            xListView.stopRefresh();
            //设置刷新的时间
            Date date = new Date();
            final String ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            xListView.setRefreshTime(ss);
            if (getActivity() == null) {
                return;
            }
            switch (msg.what) {
                case HandlerCodes.Code_ERROR:
                    Toast.makeText(getActivity(), "网络请求异常！", Toast.LENGTH_SHORT).show();
                    break;
                case HandlerCodes.Code_MyRepair:
                    String result = (String) msg.obj;
                    //解析json，判断返回码
                    RepairJson repairJson = ((BaseActivity) getActivity()).gson.fromJson(result, RepairJson.class);
                    int status = repairJson.getStatus();
                    //如果失败
                    if (status == 0) {
                        Toast.makeText(getActivity(), repairJson.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if (status == 1) {
                        //如果成功
                        infoList.clear();
                        List<RepairJson.DataBean> data = repairJson.getData();
                        for (RepairJson.DataBean dataBean : data) {
                            //int state, String thing, String name, String phone, String address,
                            // String time, String workNumber, String workerName, String workerPhone, List<String> imageList
                            infoList.add(new FragmentInfo(dataBean.getState(), dataBean.getThing(), dataBean.getName(),
                                    dataBean.getPhone(), dataBean.getAddress(), dataBean.getTime(), dataBean.getWorkNumber(),
                                    dataBean.getWorkerName(), dataBean.getWorkerPhone(), dataBean.getImageList(),
                                    dataBean.getScore(), dataBean.getEvaluation()));
                        }
                        if (adapter != null) {
                            notifyChange();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    };

    public void addData() {
        //请求服务器获取报修信息 参数useid
        ProgressDialogUtils.showProgressDialog(getActivity(), "加载中...");
        Bundle bundle = new Bundle();
        bundle.putString("useid", BaseActivity.USERID);
        OkHttpTools.request(bundle, API.URL_MyRepair, handler);
    }

    public void notifyChange() {

    }

    @Override
    public void onRefresh() {
        if (xListView == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //刷新适配器，也就是说只要你的数据源发生了改变那么你就一定要刷新你的适配
                addData();

            }
        });
    }

    @Override
    public void onLoadMore() {
        Date date = new Date();
        final String ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        if (xListView == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addData();
                xListView.stopLoadMore();
                xListView.setRefreshTime(ss);
            }
        });
    }
}
