package com.bohaigaoke.forestry.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bohaigaoke.forestry.MainActivity;
import com.bohaigaoke.forestry.R;


/**
 * 我的位置组件
 * @author name
 *
 */
public class LocationFragment extends Fragment {
	private final String TAG = "LocationFragment";
	/**
	 * 
	 */
	private Button mButtonScale;
	private ImageButton mButtonLocation;
	private TextView mTextViewScale;
	private TextView mTxt_debug_info;// 调试信息
	private TextView mTxt_debug_info1;// 调试信息
	private String text = "20公里";// 比例尺文本

	private boolean isLocated = false;
	private int scaleWidth = 104;// 比例尺宽度
	private int scaleHeight = 8;// 比例尺高度
	private int textColor = Color.BLACK;// 比例尺字体颜色
	private int textSize = 18;// 比例尺宽度
	private int scaleSpaceText = 8;// 比例尺文本与图形的间隔高度
	private Paint mPaint = new Paint();// 画笔

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_location_scale, container, false);
		
		mButtonLocation = (ImageButton) view.findViewById(R.id.button_location);
		mButtonScale = (Button) view.findViewById(R.id.btnScale);
		mTextViewScale = (TextView) view.findViewById(R.id.ScaleText);
		mTxt_debug_info = (TextView) view.findViewById(R.id.txt_debug_info);
		mTxt_debug_info1 = (TextView) view.findViewById(R.id.txt_debug_info1);

		//註冊我的位置按鈕，定位地圖到設備位置
		mButtonLocation.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (getActivity().getClass().getName()
						.equals(MainActivity.class.getName())) {
					
					if(!isLocated){
						isLocated = true;
						mButtonLocation.setImageDrawable(
								getActivity().getResources().getDrawable(R.mipmap.map_location_pressed));
						((MainActivity) getActivity()).startLocated();
					}else{
						isLocated = false;
						mButtonLocation.setImageDrawable(
								getActivity().getResources().getDrawable(R.mipmap.map_location_normal));
						((MainActivity) getActivity()).stopLocated();
					}
				} 
			}
		});
		return view;
	}

	
	//設置比例尺信息
	public void setScale(double mapScale) {
		mTxt_debug_info.setText("Scale:"+String.valueOf(mapScale));
		//mTxt_debug_info1.setText("Resolution:"+String.valueOf(px));
		double scale = mapScale/100;//结果单位米，表示图上1厘米代表*米  
        double ppi = getPPIOfDevice();  
        if(scale>0&&scale<=20){  
            String unit = "20米";  
            int scaleWidth=(int)(20*ppi/2.54/scale);//264为ppi，ppi/2.54为1厘米的像素数  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>20&&scale<=50){//换算50米  
            String unit = "50米";  
            int scaleWidth=(int)(50*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>50&&scale<=100){  
            String unit = "100米";  
            int scaleWidth=(int)(100*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>100&&scale<=200){  
            String unit = "200米";  
            int scaleWidth=(int)(200*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>200&&scale<=500){  
            String unit = "500米";  
            int scaleWidth=(int)(500*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>500&&scale<=1000){  
            String unit = "1公里";  
            int scaleWidth=(int)(1000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>1000&&scale<=2000){  
            String unit = "2公里";  
            int scaleWidth=(int)(2000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>2000&&scale<=5000){  
            String unit = "5公里";  
            int scaleWidth=(int)(5000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>5000&&scale<=10000){  
            String unit = "10公里";  
            int scaleWidth=(int)(10000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>10000&&scale<=20000){  
            String unit = "20公里";  
            int scaleWidth=(int)(20000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>20000&&scale<=25000){  
            String unit = "25公里";  
            int scaleWidth=(int)(25000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>25000&&scale<=50000){  
            String unit = "50公里";  
            int scaleWidth=(int)(50000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>50000&&scale<=100000){  
            String unit = "100公里";  
            int scaleWidth=(int)(100000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>100000&&scale<=200000){  
            String unit = "200公里";  
            int scaleWidth=(int)(200000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>200000&&scale<=250000){  
            String unit = "250公里";  
            int scaleWidth=(int)(250000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>250000&&scale<=500000){  
            String unit = "500公里";  
            int scaleWidth=(int)(500000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }else if(scale>500000&&scale<=scale){  
            String unit = "1000公里";  
            int scaleWidth=(int)(1000000*ppi/2.54/scale);  
            setText(unit);
            setScaleWidth(scaleWidth);
        }  
		/*
        ///int scale = (int) mi;
		if (scale >= 1000) {
			mTextViewScale.setText(Math.round(scale / 100d) / 10d + " 公里");
		} else {
			mTextViewScale.setText(scale + "米");
		}
		int dp = (int) Math.round(px * 10000) * 20;
		if (dp < 100) {
			dp = 50;
		}
		if (dp > 200) {
			dp = 200;
		}*/
		
	}
	
	/**
	 * 设置比例尺文字
	 * @param text
	 */
	private void setText(String text){
		mTextViewScale.setText(text);
	}
	/**
	 * 设置比例尺长度  像素
	 * @param width
	 */
	private void setScaleWidth(int width){
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mButtonScale.getLayoutParams();
		linearParams.width = width;
		mButtonScale.setLayoutParams(linearParams);
	}
	
	private double getPPIOfDevice() {  
		Point point = new Point();  
		getActivity().getWindowManager().getDefaultDisplay().getRealSize(point);  
	    DisplayMetrics dm = getResources().getDisplayMetrics();  
	    double x = Math.pow(point.x/ dm.xdpi, 2);  
	    double y = Math.pow(point.y / dm.ydpi, 2);  
	    double screenInches = Math.sqrt(x + y);  
	    Log.d(TAG, "Screen inches : " + screenInches);  
	   /* Point point = new Point();  
	    getActivity().getWindowManager().getDefaultDisplay().getRealSize(point);//获取屏幕的真实分辨率  
	    DisplayMetrics dm = getResources().getDisplayMetrics();  
	    double x = Math.pow(point.x/ dm.xdpi, 2);//  
	    double y = Math.pow(point.y / dm.ydpi, 2);  
	    double screenInches = Math.sqrt(x + y);  */
	   Double ppi=Math.sqrt(Math.pow(point.x, 2)+Math.pow(point.y, 2))/screenInches;  
	    return ppi;  
	}  

	@Override
	public void onPause() {
		super.onPause();
	}
	private void drawNinepath(Canvas canvas, int resId, Rect rect){  
	    Bitmap bmp= BitmapFactory.decodeResource(getResources(), resId);  
	    NinePatch patch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);  
	    patch.draw(canvas, rect);  
	}  
	@SuppressLint("DrawAllocation")  
	protected void onDraw(Canvas canvas) {  
	    //super.onDraw(canvas);  
	    int width = scaleWidth ;  
	    mPaint.setColor(textColor);  
	    mPaint.setAntiAlias(true);  
	    mPaint.setTextSize(textSize);  
	    mPaint.setTypeface(Typeface.DEFAULT_BOLD);  
	    float textWidth = mPaint.measureText(text);  
	    canvas.drawText(text, (width - textWidth) / 2, textSize, mPaint);  
	    Rect scaleRect = new Rect(0, textSize + scaleSpaceText, width, textSize + scaleSpaceText + scaleHeight);  
	    drawNinepath(canvas, R.mipmap.map_icon_scale, scaleRect);  
	} 

}
