package com.bohaigaoke.android.util;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


public class NumberBitmap {

	private Context context;
	private Resources res;

	public NumberBitmap(Context con, Resources res) {
		this.context = con;
		this.res = res;
	}
	
	/**
	 * 
	 * 在位图上加数字
	 * 
	 * @param icon
	 * @return
	 */
	public Bitmap getNumberIcon(int resId, int number) {
		Bitmap normalBitmap;
		Drawable icon = res.getDrawable(resId);
		BitmapDrawable bd = (BitmapDrawable) icon;
		normalBitmap = bd.getBitmap();
		// 初始化画布
		float density = GisUtils.getDensity(context);
		int mWidth = (int) (icon.getMinimumWidth() / density * 1.5);
		int mHeight = (int) (icon.getMinimumHeight() / density * 1.5);

		Bitmap contactIcon = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		
		Canvas canvas = new Canvas(contactIcon);
		// 拷贝图片
		Paint iconPaint = new Paint();
		iconPaint.setDither(true);// 防抖动
		iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
		Rect src = new Rect(0, 0, normalBitmap.getWidth(), normalBitmap.getHeight());
		Rect dst = new Rect(0, 0, mWidth, mHeight);
		canvas.drawBitmap(normalBitmap, src, dst, iconPaint);

		// 模拟数字
		if (number >= 10) {
			// 启用抗锯齿和使用设备的文本字距
			Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
					| Paint.DEV_KERN_TEXT_FLAG);
			// 字体颜色
			countPaint.setColor(Color.WHITE);
			// 字体大小
			countPaint.setTextSize(15);
			// 加粗
			countPaint.setTypeface(Typeface.DEFAULT_BOLD);
			// 数字的显示位置
			canvas.drawText(String.valueOf(number), mWidth / 2 - 10,
					mHeight / 2, countPaint);
		} else {
			// 启用抗锯齿和使用设备的文本字距
			Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
					| Paint.DEV_KERN_TEXT_FLAG);
			countPaint.setColor(Color.WHITE);
			countPaint.setTextSize(15);
			countPaint.setTypeface(Typeface.DEFAULT_BOLD);
			// 数字的显示位置
			canvas.drawText(String.valueOf(number), mWidth / 2 - 5,
					mHeight / 2, countPaint);
		}
		return contactIcon;
	}
}
