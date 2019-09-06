package com.bohaigaoke.android.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.util.Iterator;
import java.util.List;


/**
 * 获取设备信息工具类
 * @author name
 *
 */
public class DeviceUtil {
	private static TelephonyManager tm;
	private static Context context;
	private static ConnectivityManager mConnectivityManager;
	
	public static void setTelephonyManager(Context context_){
		context = context_;
		if(tm == null){
			tm = (TelephonyManager) context_.getSystemService(Context.TELEPHONY_SERVICE);
		}
		if(mConnectivityManager == null){
			mConnectivityManager = (ConnectivityManager) context_.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
	}
	
	/**
	 * dp 转化为 px
	 * 
	 * @param dpValue
	 * @return
	 */
	public static int dp2px(float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px 转化为 dp
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2dp(float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取设备宽度（px）
	 * 
	 * @return
	 */
	public static int deviceWidth() {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取设备高度（px）
	 * 
	 * @return
	 */
	public static int deviceHeight() {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * SD卡判断
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 是否有网
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected() {
		if (context != null && mConnectivityManager != null) {
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 返回版本名字 对应build.gradle中的versionName
	 * 
	 * @return
	 */
	public static String getVersionName() {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 返回版本号 对应build.gradle中的versionCode
	 * 
	 * @return
	 */
	public static String getVersionCode() {
		String versionCode = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionCode = String.valueOf(packInfo.versionCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取设备的唯一标识，deviceId
	 * 
	 * @return
	 */
	public static String getDeviceId() {

		String deviceId = null;

		try {
			deviceId = tm.getDeviceId();
		}catch (SecurityException e){
			e.printStackTrace();
		}


		if (deviceId == null) {
			return "";
		} else {
			return deviceId;
		}
	}

	/** 
     * 获取电话号码 
     */  
    public static String getNativePhoneNumber() {  
        String NativePhoneNumber=null;
		try {
			NativePhoneNumber=tm.getLine1Number();
		}catch (SecurityException e){
			e.printStackTrace();
		}

        return NativePhoneNumber;  
    }  
  
    /** 
     * 获取手机服务商信息 
     */  
    public String getProvidersName() {  
        String ProvidersName = "N/A";  
        try{
			String IMSI = null;
			try {
				IMSI = tm.getSubscriberId();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			System.out.println(IMSI);
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
            ProvidersName = "中国移动";  
        } else if (IMSI.startsWith("46001")) {  
            ProvidersName = "中国联通";  
        } else if (IMSI.startsWith("46003")) {  
            ProvidersName = "中国电信";  
        }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return ProvidersName;  
    }  
      
	/**
	 * 获取手机品牌
	 * 
	 * @return
	 */
	public static String getPhoneBrand() {
		return android.os.Build.BRAND;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getPhoneModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取手机Android API等级（22、23 ...）
	 * 
	 * @return
	 */
	public static int getBuildLevel() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获取手机Android 版本（4.4、5.0、5.1 ...）
	 * 
	 * @return
	 */
	public static String getBuildVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取当前App进程的id
	 * 
	 * @return
	 */
	public static int getAppProcessId() {
		return android.os.Process.myPid();
	}

	/**
	 * 获取当前App进程的Name
	 * 
	 * @param processId
	 * @return
	 */
	public static String getAppProcessName(int processId) {
		String processName = null;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取所有运行App的进程集合
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = context.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == processId) {
					CharSequence c = pm.getApplicationLabel(pm
							.getApplicationInfo(info.processName,
									PackageManager.GET_META_DATA));

					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				Log.e(DeviceUtil.class.getName(), e.getMessage(), e);
			}
		}
		return processName;
	}

	/**
	 * 创建App文件夹
	 * 
	 * @param appName
	 * @param application
	 * @return
	 */
	public static String createAPPFolder(String appName, Application application) {
		return createAPPFolder(appName, application, null);
	}

	/**
	 * 创建App文件夹
	 * 
	 * @param appName
	 * @param application
	 * @param folderName
	 * @return
	 */
	public static String createAPPFolder(String appName, Application application, String folderName) {
		File root = Environment.getExternalStorageDirectory();
		File folder;
		/**
		 * 如果存在SD卡
		 */
		if (DeviceUtil.isSDCardAvailable() && root != null) {
			folder = new File(root, appName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		} else {
			/**
			 * 不存在SD卡，就放到缓存文件夹内
			 */
			root = application.getCacheDir();
			folder = new File(root, appName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
		if (folderName != null) {
			folder = new File(folder, folderName);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
		return folder.getAbsolutePath();
	}

	/**
	 * 通过Uri找到File
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static File uri2File(Activity context, Uri uri) {
		File file;
		String[] project = { MediaStore.Images.Media.DATA };
		Cursor actualImageCursor = context.getContentResolver().query(uri,
				project, null, null, null);
		if (actualImageCursor != null) {
			int actual_image_column_index = actualImageCursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualImageCursor.moveToFirst();
			String img_path = actualImageCursor
					.getString(actual_image_column_index);
			file = new File(img_path);
		} else {
			file = new File(uri.getPath());
		}
		if (actualImageCursor != null)
			actualImageCursor.close();
		return file;
	}

	/**
	 * 获取AndroidManifest.xml里 的值
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getMetaData(Context context, String name) {
		String value = null;
		try {
			ApplicationInfo appInfo = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			value = appInfo.metaData.getString(name);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return value;
	}

}