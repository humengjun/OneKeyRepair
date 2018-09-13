package wit.hmj.onekeyrepair;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class BaseActivity extends AppCompatActivity {

    public Gson gson = new Gson();
    public static boolean LOGIN;//判断是否登录
    public static String USERID;
    public static String imagePath;
    public static String Name;
    public static String Phone;
    public static SharedPreferences login_sharePreferences;
    public static SharedPreferences image_share;
    public static final String phoneReg = "^[1]([3][0-9]{1}|59|58|88|89|82|87|86|50)[0-9]{8}$";

    public ImageLoader loader;
    public DisplayImageOptions options = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.mipmap.no_pic) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.mipmap.no_pic) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.mipmap.no_pic) // 设置图片加载或解码过程中发生错误显示的图片
            .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
            .delayBeforeLoading(1000)  // 下载前的延迟时间
            .cacheInMemory(false) // default  设置下载的图片是否缓存在内存中
            .cacheOnDisk(false) // default  设置下载的图片是否缓存在SD卡中
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login_sharePreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        image_share = getSharedPreferences("image",MODE_PRIVATE);
        imagePath = image_share.getString("IMGPATH",null);
        LOGIN=login_sharePreferences.getBoolean("ISLOGIN",false);
        USERID=login_sharePreferences.getString("USERID",null);

        loader = ImageLoader.getInstance();
        loader.init(ImageLoaderConfiguration.createDefault(this));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
