package wit.hmj.onekeyrepair.my.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;

import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.view.view.XListView;

/**
 * Created by Administrator on 2018/1/4.
 * 全部
 */

public class FirstFragment extends BaseFragment{
    private View view;

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
        adapter = new FragmentAdapter(getActivity(),infoList);
        xListView.setAdapter(adapter);
        return view;
    }

}
