package wit.hmj.onekeyrepair.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.my.model.FragmentInfo;
import wit.hmj.onekeyrepair.view.view.XListView;

/**
 * Created by Administrator on 2018/1/4.
 * 已完成
 */

public class FourFragment extends BaseFragment{
    private View view;

    private List<FragmentInfo> fourInfoList = new ArrayList<>();;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.first_fragment,container,false);
        addData();
        xListView = view.findViewById(R.id.xListView);
        //设置XlistView 的监听
        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        adapter = new FragmentAdapter(getActivity(),fourInfoList);
        xListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void notifyChange() {
        fourInfoList.clear();
        for (int i = 0; i < infoList.size(); i++) {
            FragmentInfo fragmentInfo = infoList.get(i);
            if(fragmentInfo.getState()==State.STATE_YWC){
                fourInfoList.add(fragmentInfo);
            }
        }
    }
}
