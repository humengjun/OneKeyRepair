package wit.hmj.onekeyrepair.my;

import android.content.Context;
import android.media.Ringtone;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import wit.hmj.onekeyrepair.R;

/**
 * Created by Administrator on 2017/1/9.
 */
public class NotficationAdapter extends BaseAdapter{
    private Context context;
    private List<Ringtone>date;
    private int UpPosition;

    public NotficationAdapter(Context context, List<Ringtone> date, int UpPosition) {
        this.context = context;
        this.date = date;
        this.UpPosition=UpPosition;
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int i) {
        return date.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Myhodler holder=null;
        if(view==null){
        view=View.inflate(context, R.layout.notification_adapter_item,null);
        holder=new Myhodler();
        holder.textView_tzls= (TextView) view.findViewById(R.id.textView_tzls);
        holder.imageView_tzls= (ImageView) view.findViewById(R.id.imageView_tzls);
            view.setTag(holder);
        }else{
            holder= (Myhodler) view.getTag();
        }
        holder.textView_tzls.setText(date.get(position).getTitle(context));
        if(position==UpPosition){
            holder.imageView_tzls.setBackgroundResource(R.mipmap.checked);
        }
        return view;
    }
    class Myhodler{
        TextView textView_tzls;
        ImageView imageView_tzls;
    }
}
