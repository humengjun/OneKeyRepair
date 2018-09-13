package wit.hmj.onekeyrepair.choose;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;
import wit.hmj.onekeyrepair.choose.codes.Codes;
import wit.hmj.onekeyrepair.choose.utils.SelectPicPopupWindow;
import wit.hmj.onekeyrepair.choose.utils.SelectPicSecondPopupWindow;

/**
 * BaseActivity<br>
 */
public class SelectPhotoActivity extends BaseActivity {

    /**
     * 选择图片的返回码
     */
    public final static int SELECT_IMAGE_RESULT_CODE = 200;
    /**
     * 当前选择的图片的路径
     */
    public String mImagePath;
    /**
     * 自定义的PopupWindow
     */
    private SelectPicPopupWindow menuWindow;
    private SelectPicSecondPopupWindow window;

//    /**
//     * Fragment回调接口
//     */
//    public OnFragmentResult mOnFragmentResult;
//
//    public void setOnFragmentResult(OnFragmentResult onFragmentResult) {
//        mOnFragmentResult = onFragmentResult;
//    }
//
//    /**
//     * 回调数据给Fragment的接口
//     */
//    public interface OnFragmentResult {
//        void onResult(String mImagePath);
//    }

    /**
     * 拍照或从图库选择图片(Dialog形式)
     */
    public void showPictureDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{"拍摄照片", "选择照片", "取消"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0://拍照
                                takePhoto();
                                break;
                            case 1://相册选择图片
                                pickPhoto();
                                break;
                            case 2://取消
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    /**
     * 拍照或从图库选择图片(PopupWindow形式)
     */
    public void showPicturePopupWindow(View view, int code) {
        if (code == Codes.POP_BOTTOM) {
            menuWindow = new SelectPicPopupWindow(this, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 隐藏弹出窗口
                    menuWindow.dismiss();
                    switch (v.getId()) {
                        case R.id.takePhotoBtn:// 拍照
                            takePhoto();
                            break;
                        case R.id.pickPhotoBtn:// 相册选择图片
                            pickPhoto();
                            break;
                        case R.id.cancelBtn:// 取消
                            break;
                        default:
                            break;
                    }
                }
            });
            menuWindow.setAnimationStyle(R.style.headpop_anim_style);
            menuWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        if (code == Codes.POP_CENTER) {
            window = new SelectPicSecondPopupWindow(this, new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 隐藏弹出窗口
                    window.dismiss();
                    switch (v.getId()) {
                        case R.id.btn_takePhoto:// 拍照
                            takePhoto();
                            break;
                        case R.id.btn_selectPhoto:// 相册选择图片
                            pickPhoto();
                            break;
                        default:
                            break;
                    }
                }
            });
            window.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/" + formatter.format(new Date()) + ".jpg";
            File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件
            mImagePath = imageFile.getAbsolutePath();
            Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
        } else {
            Toast.makeText(this, "内存卡不存在!", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE_RESULT_CODE);
    }

}
