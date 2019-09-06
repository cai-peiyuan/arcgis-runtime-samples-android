package com.bohaigaoke.android.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bohaigaoke.forestry.R;


/**
 * 
 * 创建人：wulin 创建时间：2013-6-13 上午9:31:01 项目名称：GTHMProj 类名称：ToastView 说明： 自定义Toast
 * 
 */
public class ToastUtil {

	public static void showMessage(Context context, String msg) {
		Toast toast = new Toast(context.getApplicationContext());
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toast_view,(ViewGroup) ((Activity) context)
						.findViewById(R.id.ll_toast_layout_root));
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message.setText(msg);
		// 设置Toast的位置
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		// 显示自定义的View
		toast.setView(view);
		toast.show();
	}

	public static void showMessage(Context context, String msg, int time) {
		Toast toast = new Toast(context.getApplicationContext());
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toast_view,
				(ViewGroup) ((Activity) context)
						.findViewById(R.id.ll_toast_layout_root));
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message.setText(msg);
		// 设置Toast的位置
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(time);// Toast.LENGTH_SHORT
		// 显示自定义的View
		toast.setView(view);
		toast.show();
	}

	public static void showMessage(Activity context, int id) {
		if (context == null)
			return;
		String msg = context.getResources().getString(id);
		showMessage(context, msg);
	}

	public static void showMessage(Context context, int id) {
		if (context == null)
			return;
		String msg = context.getResources().getString(id);
		showMessage(context, msg);
	}
}
