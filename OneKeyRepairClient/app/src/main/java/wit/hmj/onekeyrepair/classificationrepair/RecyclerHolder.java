package wit.hmj.onekeyrepair.classificationrepair;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import wit.hmj.onekeyrepair.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class RecyclerHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public RecyclerHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }
}
