package wit.hmj.onekeyrepair.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.zip.Inflater;

import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.choose.utils.ImageUtils;
import wit.hmj.onekeyrepair.my.model.FeedBackInfo;

/**
 * Created by Administrator on 2018/1/4.
 */

public class MyFeedBackAdapter extends BaseAdapter {

    private List<FeedBackInfo> infoList;
    private Context context;
    private ImageLoader loader;
    private DisplayImageOptions options;

    public MyFeedBackAdapter(List<FeedBackInfo> infoList, Context context,ImageLoader loader,DisplayImageOptions options) {
        this.infoList = infoList;
        this.context = context;
        this.loader = loader;
        this.options = options;
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
        Holder holder=null;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.feedback_xlistview_item,null);
            holder = new Holder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.text_name = convertView.findViewById(R.id.text_name);
            holder.text_time = convertView.findViewById(R.id.text_time);
            holder.text_content = convertView.findViewById(R.id.text_content);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        FeedBackInfo feedBackInfo = infoList.get(position);
        String imagePath=feedBackInfo.getImagePath();
        String time=feedBackInfo.getTime();
        String name=feedBackInfo.getName();
        String content=feedBackInfo.getContent();
        if(!imagePath.equals("")){
            loader.displayImage("File:///"+imagePath,holder.imageView,options);
        }
        holder.text_name.setText(name);
        holder.text_time.setText(time);
        holder.text_content.setText(content);

        return convertView;
    }

    class Holder{
        ImageView imageView;
        TextView text_name,text_time,text_content;
    }
}
