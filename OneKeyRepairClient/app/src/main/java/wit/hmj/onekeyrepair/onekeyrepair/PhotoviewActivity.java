package wit.hmj.onekeyrepair.onekeyrepair;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.utils.ImageUtils;
import wit.hmj.onekeyrepair.utils.PhotoViewPager;

/**
 * @author Cloudsoar(wangyb)
 * @datetime 2015-11-19 20:53 GMT+8
 * @email 395044952@qq.com
 */
public class PhotoviewActivity  extends BaseActivity implements OnViewTapListener, View.OnClickListener {
    private PhotoViewPager mViewPager;
    private PhotoView mPhotoView;
    private String[] images;
    private String uri[];
    private PhotoViewAdapter mAdapter1;
    private PhotoViewURIAdapter mAdapter2;
    private PhotoViewAttacher mAttacher;
    private RadioGroup radioGroup;
    private List<RadioButton> radioButtons = new ArrayList<>();//保存所有的radiobutton
    private List<String> uriList = new ArrayList<>();
    private PopupWindow popupWindow;

    private DisplayImageOptions options;
    private ImageLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_photo_viewpager);
        loader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        images = getIntent().getStringArrayExtra("images");
        uri = getIntent().getStringArrayExtra("uri");
        if (uri != null) {
            for (int i = 0; i < uri.length; i++) {
                uriList.add(uri[i]);
            }
        }
        setupView();
        setupData();
    }

    private void setupView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager);
        //mPhotoView = (PhotoView) findViewById(R.id.photo);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setRadioButtonChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (images != null) {
            addRadioButton(images.length);
            View v = getLayoutInflater().inflate(R.layout.photoview_pop_item, null);
            popupWindow = new PopupWindow(v, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setAnimationStyle(R.style.PopupAnimation);
            popupWindow.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0x8000000);
            popupWindow.setBackgroundDrawable(dw);
            v.findViewById(R.id.textView_1).setOnClickListener(this);
            v.findViewById(R.id.textView_2).setOnClickListener(this);
            v.findViewById(R.id.textView_3).setOnClickListener(this);

        }
        if (uri != null) {
            if (uri[uri.length - 1] != null) {
                addRadioButton(uri.length);
            } else {
                addRadioButton(uri.length - 1);
            }
            findViewById(R.id.relative).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_back).setOnClickListener(this);
            findViewById(R.id.btn_del).setOnClickListener(this);
        }
    }

    private void returnActivity() {
        uri = new String[uriList.size()];
        uriList.toArray(uri);
        Intent intent = new Intent();
        intent.putExtra("uri", uri);
        setResult(101, intent);
        finish();
    }

    private void addRadioButton(int length) {
        radioGroup.removeAllViews();
        for (int i = 0; i < length; i++) {
            RadioButton btn = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 10);
            btn.setLayoutParams(params);
            btn.setEnabled(false);
            setRadioButtonChecked(i);
            radioGroup.addView(btn);
            radioButtons.add(btn);
        }
        setRadioButtonChecked(0);
    }

    private void setRadioButtonChecked(int i) {
        for (int j = 0; j < radioButtons.size(); j++) {
            if (j == i) {
                radioButtons.get(j).setButtonDrawable(getResources().getDrawable(R.mipmap.dot_select));
            } else {
                radioButtons.get(j).setButtonDrawable(getResources().getDrawable(R.mipmap.dot_unselect));
            }
        }
    }

    private void setupData() {
        int mCurrentUrl = getIntent().getIntExtra("photo_position", 0);
//        mImgUrls = Arrays.asList(Images.imageThumbUrls);
        if (images != null) {
            mAdapter1 = new PhotoViewAdapter();
            mViewPager.setAdapter(mAdapter1);
        }
        if (uri != null) {
            mAdapter2 = new PhotoViewURIAdapter();
            mViewPager.setAdapter(mAdapter2);
        }
        //设置当前需要显示的图片
        mViewPager.setCurrentItem(mCurrentUrl);
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        if (images != null) {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_del:
                final AlertDialog.Builder builder = new AlertDialog.Builder(PhotoviewActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("是否删除")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                uriList.remove(mViewPager.getCurrentItem());
                                if (uriList.size() == 0 || uriList.get(0) == null) {
                                    returnActivity();
                                    return;
                                }
                                mAdapter2 = new PhotoViewURIAdapter();
                                mViewPager.setAdapter(mAdapter2);
                                radioGroup.removeViewAt(0);
                                radioButtons.remove(0);
                                setRadioButtonChecked(0);

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btn_back:
                returnActivity();
                break;
            case R.id.textView_1:
                //保存图片
                try {
                    saveIMG();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                popupWindow.dismiss();
                break;
            case R.id.textView_2:
                //查看原图
                popupWindow.dismiss();
                break;
            case R.id.textView_3:
                //取消
                popupWindow.dismiss();
                break;
        }
    }

    private void saveIMG() throws IOException {
        //判断sd卡是否存在
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this,"保存失败，没有SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        File myCaptureFile = new File(Environment.getExternalStorageDirectory(), new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date())+".jpg");
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(myCaptureFile);

        //bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        out.flush();
        out.close();
        Toast.makeText(this,"保存成功", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
    }

    class PhotoViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(PhotoviewActivity.this,
                    R.layout.item_photo_view, null);
            mPhotoView = (PhotoView) view.findViewById(R.id.photo);
            //使用ImageLoader加载图片
//            CSApplication.getInstance().imageLoader.displayImage(
//                    mImgUrls.get(position),mPhotoView, DisplayImageOptionsUtil.defaultOptions);
            loader.displayImage(API.URL_Image+images[position], mPhotoView, options);
            //给图片增加点击事件
            mAttacher = new PhotoViewAttacher(mPhotoView);
            mAttacher.setOnViewTapListener(PhotoviewActivity.this);
            mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                    return true;
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAttacher = null;
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class PhotoViewURIAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(PhotoviewActivity.this,
                    R.layout.item_photo_view, null);
            mPhotoView = (PhotoView) view.findViewById(R.id.photo);
            //使用ImageLoader加载图片
//            CSApplication.getInstance().imageLoader.displayImage(
//                    mImgUrls.get(position),mPhotoView, DisplayImageOptionsUtil.defaultOptions);
            Bitmap bitmap = ImageUtils.getImageThumbnail(uriList.get(position), ImageUtils.getWidth(PhotoviewActivity.this), ImageUtils.getWidth(PhotoviewActivity.this));
            mPhotoView.setImageBitmap(bitmap);
            //给图片增加点击事件
            mAttacher = new PhotoViewAttacher(mPhotoView);
            mAttacher.setOnViewTapListener(PhotoviewActivity.this);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAttacher = null;
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (uriList.get(uriList.size() - 1) != null) {
                return uriList.size();
            } else {
                return uriList.size() - 1;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
