package wit.hmj.onekeyrepair.classificationrepair;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wit.hmj.onekeyrepair.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class MyAdapter extends BaseAdapter {

    private List<String> stringList;
    private List<Integer> integerList;
    private Context context;

    public MyAdapter(List<String> stringList, List<Integer> integerList, Context context) {
        this.stringList = stringList;
        this.integerList = integerList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            convertView  = View.inflate(context, R.layout.gridview_classification_repair,null);
            holder = new Holder();
            holder.textView = convertView.findViewById(R.id.textView);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(stringList.get(position));
        holder.imageView.setImageResource(integerList.get(position));

        return convertView;
    }

    class Holder{
        ImageView imageView;
        TextView textView;
    }
}
