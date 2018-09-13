package wit.hmj.onekeyrepair.classificationrepair;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wit.hmj.onekeyrepair.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class RecyclerAdapter extends RecyclerView.Adapter {
    private List<String> stringList;
    private Context context;
    private CallBack callBack;

    public RecyclerAdapter(List<String> stringList, Context context,CallBack callBack) {
        this.stringList = stringList;
        this.context = context;
        this.callBack = callBack;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerHolder holder = new RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((RecyclerHolder)holder).textView.setText(stringList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.sendData(stringList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}
