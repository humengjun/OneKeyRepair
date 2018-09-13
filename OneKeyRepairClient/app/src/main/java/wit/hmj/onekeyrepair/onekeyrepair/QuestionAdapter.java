package wit.hmj.onekeyrepair.onekeyrepair;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import wit.hmj.onekeyrepair.R;

/**
 * Created by Administrator on 2017/1/7.
 */
public class QuestionAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private int width;
    private int height;
    private ImageLoader loader;
    private DisplayImageOptions options;

    public QuestionAdapter(Context context, List list,int width,int height,ImageLoader loader,DisplayImageOptions options) {
        this.context = context;
        this.list = list;
        this.width=width;
        this.height=height;
        this.loader = loader;
        this.options = options;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        QuestionHolder holder;
        if (view==null){
            view = View.inflate(context, R.layout.item_questiongrid,null);
            holder = new QuestionHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.imageView_wuye_question_item);
            view.setTag(holder);
        }else {
            holder = (QuestionHolder) view.getTag();
        }
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.width = width;
        if(list.size()>4){
            params.height = height*12/10;
        }else {
            params.height = height;
        }
        holder.imageView.setLayoutParams(params);
        loader.displayImage("File:///"+list.get(i),holder.imageView,options);
        return view;
    }

    class QuestionHolder{
        ImageView imageView;
    }
}
