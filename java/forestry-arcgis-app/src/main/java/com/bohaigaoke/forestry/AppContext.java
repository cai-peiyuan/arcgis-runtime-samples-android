package com.bohaigaoke.forestry;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bohaigaoke.android.config.ConfigLoader;
import com.bohaigaoke.android.config.FarmHouseConfig;
import com.bohaigaoke.android.model.login.Userinfo_;
import com.bohaigaoke.android.model.query.PageResult;
import com.bohaigaoke.android.util.DeviceUtil;
import com.bohaigaoke.forestry.model.HouseBean;
import com.bohaigaoke.forestry.service.LocationService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.tencent.smtt.sdk.QbSdk;

import net.oschina.app.AppConfig;
import net.oschina.app.api.ApiHttpClient;
import net.oschina.app.base.BaseApplication;
import net.oschina.app.bean.Constants;
import net.oschina.app.cache.DataCleanManager;
import net.oschina.app.util.MethodsCompat;
import net.oschina.app.util.StringUtils;
import net.oschina.app.util.TLog;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static net.oschina.app.AppConfig.KEY_FRITST_START;
import static net.oschina.app.AppConfig.KEY_LOAD_IMAGE;
import static net.oschina.app.AppConfig.KEY_TWEET_DRAFT;


/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @version 1.0
 * @created 2014-04-22
 */
public class AppContext extends BaseApplication {

	private String loginUid;

	private boolean login;

	private Userinfo_ userInfo;// 当前登录用户信息

	private static AppContext instance;// this instance

	public PageResult themeData;// 专题列表

	public LocationService locationService;// 百度lbs定位用

	public Vibrator mVibrator;// 百度lbs定位用

	private List<Member> memberList = new ArrayList<Member>();

	private Member member;

	private Point currPoint = new Point();

	private FarmHouseConfig farmhouseconfig;

	private HouseBean houseBean;

	public String nameTmp = "";

	public String tempCaseId = "";

	private String recordCacheFilePath = "";

	public File file = null;

	public String audioPath = "";

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		DeviceUtil.setTelephonyManager(this); // 设置硬件设备管理类
		init();
		initX5();
	}

	private void initX5() {
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		
				QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
					
					@Override
					public void onViewInitFinished(boolean arg0) {
						// TODO Auto-generated method stub
						//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
						Log.d("app", " onViewInitFinished is " + arg0);
					}
					
					@Override
					public void onCoreInitFinished() {
						// TODO Auto-generated method stub
					}
				};
				//x5内核初始化接口
				QbSdk.initX5Environment(getApplicationContext(),  cb);
	}

	private void init() {

		farmhouseconfig = new ConfigLoader().getKysConfig();
		/***
		 * 初始化定位sdk，建议在Application中创建
		 */
		locationService = new LocationService(getApplicationContext());
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		//SDKInitializer.initialize(getApplicationContext());

		// 初始化网络请求
		AsyncHttpClient client = new AsyncHttpClient();
		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
		client.setCookieStore(myCookieStore);
		client.setTimeout(Constants.AJAX_TIME_OUT);
		ApiHttpClient.setHttpClient(client);
		ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

		// Log控制器
		KJLoger.openDebutLog(true);
		TLog.DEBUG = BuildConfig.DEBUG;
		// Bitmap缓存地址
		BitmapConfig.CACHEPATH = "bohaigaoke/imagecache";
	}

	public static AppContext getInstance() {
		return instance;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public HouseBean getHouseBean() {
		return houseBean;
	}

	public void setHouseBean(HouseBean houseBean) {
		this.houseBean = houseBean;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public Point getCurrPoint() {
		return currPoint;
	}

	public void setCurrPoint(Point currPoint) {
		this.currPoint = currPoint;
	}

	public String getTempCaseId() {
		return tempCaseId;
	}

	public void setTempCaseId(String tempCaseId) {
		this.tempCaseId = tempCaseId;
	}

	public String getRecordCacheFilePath() {
		return recordCacheFilePath;
	}

	public void setRecordCacheFilePath(String recordCacheFilePath) {
		this.recordCacheFilePath = recordCacheFilePath;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public void setUserInfo(Userinfo_ userInfo) {
		this.userInfo = userInfo;
	}

	public Userinfo_ getUserInfo() {
		return userInfo;
	}

	public String getAudioTempPath() {
		return getAudioTempFile().getAbsolutePath();
	}

	public File getAudioTempFile() {
		return getConfigTempPath("tmpAudio.wav");
	}

	public File getConfigTempPath(String fileName) {
		String tmpPath = getConfigPath("temp");
		File tmpDir = new File(tmpPath);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		File tmpFile = new File(tmpPath, fileName);
		if (!tmpFile.exists()) {
			try {
				tmpFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tmpFile;
	}

	public String getConfigPath(String fileName) {
		File cgtPath = new File(this.getKyscheckConfig().getSystemConfigPath());
		if (!cgtPath.exists()) {
			cgtPath.mkdirs();
		}
		return MessageFormat.format("{0}{1}", this.getKyscheckConfig()
				.getSystemConfigPath(), fileName);
	}

	public FarmHouseConfig getKyscheckConfig() {
		return farmhouseconfig;
	}

	/*
	 */
	/*
	 * public void clearEventModel() { eventModel.setAddress("");
	 * eventModel.setAudioPath(""); eventModel.setCaseId("");
	 * eventModel.getImage_files().clear(); eventModel.getVoice_files().clear();
	 * eventModel.getImagesPath().clear(); eventModel.setLat("");
	 * eventModel.setLon(""); eventModel.setTypeDL("");
	 * eventModel.setTypeXL(""); eventModel.setIssue(""); }
	 */

	/*
	 */
	/*
	 * public void clearUnitModel() { unitModel.setAddress("");
	 * unitModel.setAudioPath(""); unitModel.setBgCode("");
	 * unitModel.setBgName(""); unitModel.setIssue("");
	 * unitModel.setObjCode(""); unitModel.setObjID("");
	 * unitModel.setObjMaterial(""); unitModel.setObjName("");
	 * unitModel.setObjQSDW(""); unitModel.setObjSize("");
	 * unitModel.setObjYHDW(""); unitModel.setObjZGDW("");
	 * unitModel.setTypeDL(""); unitModel.setTypeDLId("");
	 * unitModel.setTypeXL(""); unitModel.setTypeXLId("");
	 * unitModel.setUnitId(""); unitModel.getImage_files().clear();
	 * unitModel.getVoice_files().clear(); unitModel.getImagesPath().clear(); }
	 */
	/**
     */
	/*
	 * public void clearMyCaseDoneModel(){
	 * 
	 * myCaseDoneBean.setDealStatus("");
	 * myCaseDoneBean.setDistributeOpinion("");
	 * myCaseDoneBean.getImage_files().clear();
	 * myCaseDoneBean.getList_bitmap().clear();
	 * myCaseDoneBean.getVoice_files().clear(); }
	 */
	/**
     */
	/*
	 * public void clearMyCaseCheckModel(){ myCaseCheckBean.setDealStatus("");
	 * myCaseCheckBean.setDistributeOpinion("");
	 * myCaseCheckBean.getImageCaseCheck_files().clear();
	 * myCaseCheckBean.getList_bitmap().clear();
	 * myCaseCheckBean.getVoice_files().clear();
	 * 
	 * }
	 */

	/*
	 * ���CaseModel
	 */
	/*
	 * public void clearEventSelfModel() { eventSelfModel.setAddress("");
	 * eventSelfModel.setAudioPath(""); eventSelfModel.setCaseId("");
	 * eventSelfModel.getImage_files().clear();
	 * eventSelfModel.getVoice_files().clear();
	 * eventSelfModel.getImagesPath().clear(); eventSelfModel.setLat("");
	 * eventSelfModel.setLon(""); eventSelfModel.setTypeDL("");
	 * eventSelfModel.setTypeXL(""); eventSelfModel.setIssue(""); }
	 */

	/*
	 */
	/*
	 * public void clearUnitSelfModel() { unitSelfModel.setAddress("");
	 * unitSelfModel.setAudioPath(""); unitSelfModel.setUnitId("");
	 * unitSelfModel.setUnitCode(""); unitSelfModel.getImage_files().clear();
	 * unitSelfModel.getVoice_files().clear();
	 * unitSelfModel.getImagesPath().clear(); unitSelfModel.setTypeDL("");
	 * unitSelfModel.setTypeXL(""); unitSelfModel.setIssue(""); }
	 */

	/*
	 * ���UnitUpdateModel
	 */
	/*
	 * public void clearUnitUpdateModel() { unitUpdateModel.setAddress("");
	 * unitUpdateModel.setIssue(""); unitUpdateModel.setLat("");
	 * unitUpdateModel.setLon(""); unitUpdateModel.setNote("");
	 * unitUpdateModel.setObjMaterial(""); unitUpdateModel.setObjSize("");
	 * unitUpdateModel.setObjQSDW(""); unitUpdateModel.setObjSize("");
	 * unitUpdateModel.setObjZGDW(""); unitUpdateModel.setObjYHDW("");
	 * unitUpdateModel.getImage_files().clear();
	 * unitUpdateModel.getImagesPath().clear(); }
	 */

	/**
     */
	/*
	 * public void clearCache() { nameTmp = ""; file = null;
	 * list_bitmap.clear(); audioPath = ""; }
	 */

	/**
	 * 
	 * @param resource
	 * @return
	 */
	public View inflateView(int resource) {
		return inflateView(resource, null);
	}

	public View inflateView(int resource, ViewGroup root) {
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return vi.inflate(resource, root);
	}

	public void hideSoftInputFromWindow(IBinder windowToken) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
	}

	public String getTwoNumber(int from) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
		format.applyPattern("00");
		return format.format(from);
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 获取App唯一标识
	 * 
	 * @return
	 */
	public String getAppId() {
		String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
		if (StringUtils.isEmpty(uniqueID)) {
			uniqueID = UUID.randomUUID().toString();
			setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}

	public boolean containsProperty(String key) {
		Properties props = getProperties();
		return props.containsKey(key);
	}

	public void setProperties(Properties ps) {
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties() {
		return AppConfig.getAppConfig(this).get();
	}

	public void setProperty(String key, String value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	/**
	 * 获取cookie时传AppConfig.CONF_COOKIE
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		String res = AppConfig.getAppConfig(this).get(key);
		return res;
	}

	public void removeProperty(String... key) {
		AppConfig.getAppConfig(this).remove(key);
	}

	/**
	 * 保存登录信息
	 * 
	 * @param user
	 */
	@SuppressWarnings("serial")
	public void saveUserInfo(final Userinfo_ user) {
		this.loginUid = user.getId_();
		this.login = true;
		setProperties(new Properties() {
			{
				setProperty("user.uid", String.valueOf(user.getId_()));
				setProperty("user.name", user.getName_());
				setProperty("user.account", user.getAccount_());
				/*
				 * setProperty("user.pwd", CyptoUtils.encode("oschinaApp",
				 * user.getPwd())); setProperty("user.location",
				 * user.getLocation()); setProperty("user.followers",
				 * String.valueOf(user.getFollowers()));
				 * setProperty("user.fans", String.valueOf(user.getFans()));
				 * setProperty("user.score", String.valueOf(user.getScore()));
				 * setProperty("user.favoritecount",
				 * String.valueOf(user.getFavoritecount()));
				 * setProperty("user.gender", String.valueOf(user.getGender()));
				 * setProperty("user.isRememberMe",
				 * String.valueOf(user.isRememberMe()));// 是否记住我的信息
				 */}
		});
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	@SuppressWarnings("serial")
	public void updateUserInfo(final Userinfo_ user) {
		setProperties(new Properties() {
			{
				setProperty("user.name", user.getName_());
				/*
				 * setProperty("user.face", user.getPortrait());// 用户头像-文件名
				 * setProperty("user.followers",
				 * String.valueOf(user.getFollowers()));
				 * setProperty("user.fans", String.valueOf(user.getFans()));
				 * setProperty("user.score", String.valueOf(user.getScore()));
				 * setProperty("user.favoritecount",
				 * String.valueOf(user.getFavoritecount()));
				 * setProperty("user.gender", String.valueOf(user.getGender()));
				 */
			}
		});
	}

	/**
	 * 获得登录用户的信息
	 * 
	 * @return
	 */
	public Userinfo_ getLoginUser() {
		Userinfo_ user = new Userinfo_();
		user.setId_(getProperty("user.uid"));
		user.setAccount_(getProperty("user.account"));
		user.setName_(getProperty("user.name"));
		/*
		 * user.setPortrait(getProperty("user.face"));
		 * user.setLocation(getProperty("user.location"));
		 * user.setFollowers(StringUtils.toInt(getProperty("user.followers"),
		 * 0)); user.setFans(StringUtils.toInt(getProperty("user.fans"), 0));
		 * user.setScore(StringUtils.toInt(getProperty("user.score"), 0));
		 * user.setFavoritecount(StringUtils.toInt(
		 * getProperty("user.favoritecount"), 0));
		 * user.setRememberMe(StringUtils
		 * .toBool(getProperty("user.isRememberMe")));
		 * user.setGender(getProperty("user.gender"));
		 */return user;
	}

	/**
	 * 清除登录信息
	 */
	public void cleanLoginInfo() {
		this.loginUid = null;
		this.login = false;
		removeProperty("user.uid", "user.name", "user.face", "user.location",
				"user.followers", "user.fans", "user.score",
				"user.isRememberMe", "user.gender", "user.favoritecount");
	}

	public String getLoginUid() {
		return loginUid;
	}

	public boolean isLogin() {
		return login;
	}

	/**
	 * 用户注销
	 */
	public void Logout() {
		cleanLoginInfo();
		ApiHttpClient.cleanCookie();
		this.cleanCookie();
		this.login = false;
		this.loginUid = null;
		//Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
		//sendBroadcast(intent);
	}

	/**
	 * 清除保存的缓存
	 */
	public void cleanCookie() {
		removeProperty(AppConfig.CONF_COOKIE);
	}

	/**
	 * 清除app缓存
	 */
	public void clearAppCache() {
		DataCleanManager.cleanDatabases(this);
		// 清除数据缓存
		DataCleanManager.cleanInternalCache(this);
		// 2.2版本才有将应用缓存转移到sd卡的功能
		if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
			DataCleanManager.cleanCustomCache(MethodsCompat
					.getExternalCacheDir(this));
		}
		// 清除编辑器保存的临时内容
		Properties props = getProperties();
		for (Object key : props.keySet()) {
			String _key = key.toString();
			if (_key.startsWith("temp"))
				removeProperty(_key);
		}
		new KJBitmap().cleanCache();
	}

	public static void setLoadImage(boolean flag) {
		set(KEY_LOAD_IMAGE, flag);
	}

	/**
	 * 判断当前版本是否兼容目标版本的方法
	 * 
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	public static String getTweetDraft() {
		return getPreferences().getString(
				KEY_TWEET_DRAFT + getInstance().getLoginUid(), "");
	}

	public static void setTweetDraft(String draft) {
		set(KEY_TWEET_DRAFT + getInstance().getLoginUid(), draft);
	}

	public static String getNoteDraft() {
		return getPreferences().getString(
				AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), "");
	}

	public static void setNoteDraft(String draft) {
		set(AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), draft);
	}

	public static boolean isFristStart() {
		return getPreferences().getBoolean(KEY_FRITST_START, true);
	}

	public static void setFristStart(boolean frist) {
		set(KEY_FRITST_START, frist);
	}

	public PageResult getThemeData() {
		return themeData;
	}

	public void setThemeData(PageResult themeData) {
		this.themeData = themeData;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
