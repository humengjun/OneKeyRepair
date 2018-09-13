package wit.hmj.onekeyrepair.choose.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import wit.hmj.onekeyrepair.R;


/**
 * 从底部弹出或滑出选择菜单或窗口  
 */
public class SelectPicSecondPopupWindow extends PopupWindow {

	/**
	 * 图库选择
	 */
	private Button takePhotoBtn;
	/**
	 * 拍照
	 */
	private Button pickPhotoBtn;
	/**
	 * 取消
	 */
	private Button cancelBtn;
	private View view;

	@SuppressLint("InflateParams")
	public SelectPicSecondPopupWindow(Context context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.select_photo_pop_item, null);
		takePhotoBtn = (Button) view.findViewById(R.id.btn_takePhoto);
		pickPhotoBtn = (Button) view.findViewById(R.id.btn_selectPhoto);
		// 设置按钮监听
		pickPhotoBtn.setOnClickListener(itemsOnClick);
		takePhotoBtn.setOnClickListener(itemsOnClick);
		
		// 设置SelectPicPopupWindow的View
		this.setContentView(view);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		//this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x80000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {

				int height = view.findViewById(R.id.pop_layout).getTop();
				int height1 = view.findViewById(R.id.pop_layout).getBottom();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
					if(y>height1){
						dismiss();
					}
				}
				return true;
			}
		});

	}

}
