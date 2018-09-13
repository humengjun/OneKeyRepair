package wit.hmj.onekeyrepair.utils;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/1/5.
 */

public class OkHttpUtils {
    private static OkHttpClient singleton;

    private OkHttpUtils() {

    }

    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient();
                }
            }
        }
        return singleton;
    }
}

