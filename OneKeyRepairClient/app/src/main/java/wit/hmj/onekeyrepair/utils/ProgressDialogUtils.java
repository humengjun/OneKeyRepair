package wit.hmj.onekeyrepair.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import wit.hmj.onekeyrepair.R;


public class ProgressDialogUtils {
	private static Dialog loadingDialog;

	/**
	 * 显示ProgressDialog
	 * @param context
	 * @param message
	 */
	public static void showProgressDialog(Context context, CharSequence message){
		if(loadingDialog == null){
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
			CardView layout = (CardView) v.findViewById(R.id.dialog_view);// 加载布局
			// main.xml中的ImageView
			ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
			TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
			// 加载动画
			Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
					context, R.anim.loading_animation);
			// 使用ImageView显示动画
			spaceshipImage.startAnimation(hyperspaceJumpAnimation);
			tipTextView.setText(message);// 设置加载信息

			loadingDialog = new Dialog(context,R.style.loading_dialog);// 创建自定义样式dialog

			loadingDialog.setCancelable(false);// 不可以用“返回键”取消
			loadingDialog.setContentView(layout);// 设置布局
			loadingDialog.setCanceledOnTouchOutside(true);//设置点外部可消失
			loadingDialog.show();

			WindowManager m = loadingDialog.getWindow().getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			WindowManager.LayoutParams p = loadingDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
			p.height = (int) (d.getHeight() * 0.15); // 高度设置为屏幕的0.6
			p.width = (int) (d.getWidth() * 0.4); // 宽度设置为屏幕的0.95
			loadingDialog.getWindow().setAttributes(p);
		}else{
			loadingDialog.show();
		}
	}

	/**
	 * 关闭ProgressDialog
	 */
	public static void dismissProgressDialog(){
		if(loadingDialog != null){
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}
}
