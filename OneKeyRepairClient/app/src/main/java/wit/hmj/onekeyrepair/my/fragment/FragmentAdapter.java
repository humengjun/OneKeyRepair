package wit.hmj.onekeyrepair.my.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.my.RepairDetailsActivity;
import wit.hmj.onekeyrepair.my.model.FragmentInfo;

/**
 * Created by Administrator on 2018/1/4.
 */

public class FragmentAdapter extends BaseAdapter {
    private Context context;
    private List<FragmentInfo> infoList;

    public FragmentAdapter(Context context, List<FragmentInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.fragment_adapter_item,null);
            holder = new Holder();
            holder.text_name = convertView.findViewById(R.id.text_name);
            holder.text_thing = convertView.findViewById(R.id.text_thing);
            holder.text_time = convertView.findViewById(R.id.text_time);
            holder.text_address = convertView.findViewById(R.id.text_address);
            holder.btn_state = convertView.findViewById(R.id.btn_state);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        final FragmentInfo fragmentInfo = infoList.get(position);
        holder.text_name.setText(BaseActivity.Name);
        holder.text_time.setText(fragmentInfo.getTime());
        holder.text_thing.setText(fragmentInfo.getThing());
        holder.text_address.setText(fragmentInfo.getAddress());
        switch (fragmentInfo.getState()){
            case State.STATE_DSH:
                holder.btn_state.setText("待审核");
                break;
            case State.STATE_DWX:
                holder.btn_state.setText("待维修");
                break;
            case State.STATE_YWC:
                holder.btn_state.setText("已完成");
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,RepairDetailsActivity.class);
                intent.putExtra("FragmentInfo",fragmentInfo);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class Holder{
        TextView text_thing,text_name,text_time,text_address;
        Button btn_state;
    }
}
