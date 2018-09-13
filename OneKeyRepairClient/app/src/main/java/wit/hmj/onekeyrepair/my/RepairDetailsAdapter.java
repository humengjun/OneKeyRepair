package wit.hmj.onekeyrepair.my;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.onekeyrepair.OneKeyRepairActivity;
import wit.hmj.onekeyrepair.onekeyrepair.PhotoviewActivity;

/**
 * Created by Administrator on 2018/1/5.
 */

public class RepairDetailsAdapter extends RecyclerView.Adapter {

    private List<String> imageList;
    private Context context;
    private ImageLoader loader;
    private DisplayImageOptions options;

    public RepairDetailsAdapter(List<String> imageList, Context context, ImageLoader loader, DisplayImageOptions options) {
        this.imageList = imageList;
        this.context = context;
        this.loader = loader;
        this.options = options;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(context).inflate(R.layout.repair_details_adapter, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Holder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhotoviewActivity.class);
                String[] strings = new String[imageList.size()];
                imageList.toArray(strings);
                intent.putExtra("images",strings);
                intent.putExtra("photo_position",position);
                context.startActivity(intent);
            }
        });
        loader.displayImage(API.URL_Image+imageList.get(position), ((Holder) holder).imageView, options);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

