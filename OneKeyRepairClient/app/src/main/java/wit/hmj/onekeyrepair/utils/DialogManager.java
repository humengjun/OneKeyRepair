package wit.hmj.onekeyrepair.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wit.hmj.onekeyrepair.R;


/**
 * Created by Administrator on 2016/12/30.
 */
public class DialogManager {
    private static final String TAG = "DialogManager";
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;
    private AnimationDrawable drawable;
    private Context mContext;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 显示对话框
     */
    public void showRecordeingDialog() {
        mDialog = new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog, null);
        mDialog.setContentView(view);

        mIcon = (ImageView) mDialog.findViewById(R.id.main_recorder_dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.main_recorder_dialog_voice);
        mLabel = (TextView) mDialog.findViewById(R.id.main_recorder_dialog_label);

        mIcon.setImageResource(R.drawable.recordinganim);
        drawable = (AnimationDrawable) mIcon.getDrawable();
        drawable.start();

        mDialog.show();
    }

    /**
     * 正在录制提示
     */
    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.recordinganim);
            mLabel.setText(R.string.str_dialog_want_send);
        }
    }

    /**
     * 取消录制对话框提示
     */
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.recordinganim);
            mLabel.setText(R.string.str_recorder_want_cancel);
        }
    }

    /**
     * 录音时间过短提示
     */
    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.mipmap.vv1);
            mLabel.setText(R.string.str_dialog_time_short);
        }
    }

    /**
     * 取消对话框
     */
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 显示音量大小
     */
    public void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {
            int resId = mContext.getResources().getIdentifier("v" + level, "mipmap", mContext.getPackageName());
            mVoice.setImageResource(resId);
        }
    }
}
