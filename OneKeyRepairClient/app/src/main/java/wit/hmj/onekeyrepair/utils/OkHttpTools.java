package wit.hmj.onekeyrepair.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import wit.hmj.onekeyrepair.API;
import wit.hmj.onekeyrepair.HandlerCodes;

/**
 * Created by Administrator on 2018/1/5.
 */

public class OkHttpTools {

    private OkHttpTools() {
    }

    public static void request(Bundle bundle, String url, Handler handler) {

        switch (url) {
            case API.URL_FeedBack:
                FeedBackRequest(bundle, url, handler);
                break;
            case API.URL_Login:
                LoginRequest(bundle, url, handler);
                break;
            case API.URL_ModifyName:
                ModifyNameRequest(bundle, url, handler);
                break;
            case API.URL_ModifyPassWord:
                ModifyPassWordRequest(bundle, url, handler);
                break;
            case API.URL_MyFeedBack:
                MyFeedBackRequest(bundle, url, handler);
                break;
            case API.URL_MyRepair:
                MyRepairRequest(bundle, url, handler);
                break;
            case API.URL_PersonInfo:
                PersonInfoRequest(bundle, url, handler);
                break;
            case API.URL_Register_1:
                Register_1Request(bundle, url, handler);
                break;
            case API.URL_Register_2:
                Register_2Request(bundle, url, handler);
                break;
            case API.URL_Register_3:
                Register_3Request(bundle, url, handler);
                break;
            case API.URL_Repair:
                RepairRequest(bundle, url, handler);
                break;
            case API.URL_Evaluation:
                EvaluationRequest(bundle, url, handler);
                break;
        }
    }


    //注册1：参数： 11位数手机号
    private static void Register_1Request(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String phone = bundle.getString("phone");
        RequestBody formBody = new FormBody.Builder()
                .add("phone", phone)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_Register_1;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });

    }

    //注册2：参数： 手机号+验证码
    private static void Register_2Request(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String phone = bundle.getString("phone");
        String receive = bundle.getString("receive");
        RequestBody formBody = new FormBody.Builder()
                .add("phone", phone)
                .add("receive", receive)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_Register_2;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //注册3：参数： useid+用户名+密码
    private static void Register_3Request(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        String name = bundle.getString("name");
        String password = bundle.getString("password");
        RequestBody formBody = new FormBody.Builder()
                .add("useid", useid)
                .add("name", name)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_Register_3;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //登录：参数： 用户名+密码
    private static void LoginRequest(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String name = bundle.getString("name");
        String password = bundle.getString("password");
        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_Login;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //用户反馈：参数： useid+反馈信息
    private static void FeedBackRequest(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        String content = bundle.getString("content");
        RequestBody formBody = new FormBody.Builder()
                .add("useid", useid)
                .add("content", content)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_FeedBack;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //用户评价：参数：useid+评分+评价
    private static void EvaluationRequest(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String workNumber = bundle.getString("workNumber");
        String evaluation = bundle.getString("evaluation");
        float score = bundle.getFloat("score");
        RequestBody formBody = new FormBody.Builder()
                .add("workNumber", workNumber)
                .add("score", score + "")
                .add("evaluation", evaluation)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_Evaluation;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //修改昵称：参数： useid+新昵称
    private static void ModifyNameRequest(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        String newName = bundle.getString("newName");
        RequestBody formBody = new FormBody.Builder()
                .add("useid", useid)
                .add("newName", newName)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_ModifyName;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //修改密码：参数： useid+旧密码+新密码
    private static void ModifyPassWordRequest(Bundle bundle, String url, final Handler handler) {

        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        String currentPassword = bundle.getString("currentPassword");
        String newPassword = bundle.getString("newPassword");
        RequestBody formBody = new FormBody.Builder()
                .add("useid", useid)
                .add("currentPassword", currentPassword)
                .add("newPassword", newPassword)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_ModifyPassword;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //用户报修：参数： useid+手机号码+内容+城市+详细地址+图片+（语音）括号可有可无
    private static void RepairRequest(Bundle bundle, String url, final Handler handler) {
        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        String phone = bundle.getString("phone");
        String thing = bundle.getString("thing");
        String city = bundle.getString("city");
        String address = bundle.getString("address");
        List<String> imgList = bundle.getStringArrayList("image");

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);           // multipart/form-data
        builder.addFormDataPart("useid", useid);
        builder.addFormDataPart("phone", phone);
        builder.addFormDataPart("thing", thing);
        builder.addFormDataPart("city", city);
        builder.addFormDataPart("address", address);
        for (String path : imgList) {
            File file = new File(path);
            builder.addFormDataPart("image", phone.substring(7) + "-" + file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }
        RequestBody requestBody = builder.build();
        //构建请求体
//        RequestBody requestBody = multipartBodyBuilder.build();

//        RequestBody requestBody = new FormBody.Builder()
////                .add("useid", useid)
//                .add("phone", phone)
//                .add("thing", thing)
//                .add("city", city)
//                .add("address", address)
//                .build();

        Request request = new Request.Builder()
                .url(url)// 添加URL地址
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_Repair;
                    message.obj = result;
                    message.sendToTarget();
                }
            }
        });

    }

    //个人信息：参数： useid
    private static void PersonInfoRequest(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        RequestBody formBody = new FormBody.Builder()
                .add("useid", useid)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_PersonInfo;
                    message.obj = result;
                    message.sendToTarget();
                }
            }
        });
    }

    //反馈记录：参数： useid
    private static void MyFeedBackRequest(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        RequestBody formBody = new FormBody.Builder()
                .add("useid", useid)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_MyFeedBack;
                    message.obj = result;
                    message.sendToTarget();
                }

            }
        });
    }

    //报修记录：参数： useid
    private static void MyRepairRequest(Bundle bundle, String url, final Handler handler) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        String useid = bundle.getString("useid");
        RequestBody formBody = new FormBody.Builder()
                .add("useid", useid)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    handler.sendEmptyMessage(HandlerCodes.Code_ERROR);
                } else {
                    String result = response.body().string();
                    Message message = handler.obtainMessage();
                    message.what = HandlerCodes.Code_MyRepair;
                    message.obj = result;
                    message.sendToTarget();
                }
            }
        });
    }


}
