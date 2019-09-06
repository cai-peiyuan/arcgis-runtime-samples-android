package com.bohaigaoke.android.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bohaigaoke.forestry.AppContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigLoader {
	private SharedPreferences preference;

	private Object preferenceLock = new Object();

	/**
	 * @return SharedPreferences
	 */
	public SharedPreferences getPreference() {
		synchronized (preferenceLock) {
			if (preference == null) {
				preference = AppContext
						.getInstance()
						.getApplicationContext()
						.getSharedPreferences(APP_SETTINGS,
								Context.MODE_PRIVATE);
			}
			return preference;
		}
	}

	public static final String APP_SETTINGS = "APP_SETTINGS";

	public static final String FARMDBS = "farmconfig.db";

	public static final String LASTUSERNAME = "LASTUSERNAME";

	public static final String KEEP_PASSWORD = "KEEPPASSWORD";

	public static final String AUTO_LOGIN = "AUTOLOGIN";

	public static final String PASSWORD = "PASSWORD";

	public static final String USERID = "USERID";

	public static final String USERNAME = "USERNAME";

	public static final String PHONE = "PHONE";

	/**
     */
	private static final String FIRST_LOGIN = "FIRSTLOGIN";

	public String getUserId() {
		return getPreference().getString(USERID, "");
	}

	public void saveUserId(String userId) {
		saveValue(USERID, userId);
	}

	public String getUserName() {
		return getPreference().getString(USERNAME, "");
	}

	public void saveUserName(String userName) {
		saveValue(USERNAME, userName);
	}

	public String getPhoneNum() {
		return getPreference().getString(PHONE, "");
	}

	public void savePhoneNum(String phoneNum) {
		saveValue(PHONE, phoneNum);
	}

	public String getLastUserName() {
		return getPreference().getString(LASTUSERNAME, "");
	}

	public void saveLastUserName(String userName) {
		saveValue(LASTUSERNAME, userName);
	}

	public String getPassword() {
		return getPreference().getString(PASSWORD, "");
	}

	public void savePassword(String password) {
		saveValue(PASSWORD, password);
	}

	public boolean getKeepPassword() {
		return getPreference().getBoolean(KEEP_PASSWORD, false);
	}

	public void saveKeepPassword(Boolean keeppassword) {
		saveValue(KEEP_PASSWORD, keeppassword);
	}

	public boolean getAutoLogin() {
		return getPreference().getBoolean(AUTO_LOGIN, false);
	}

	public void saveAutoLogin(Boolean autologin) {
		saveValue(AUTO_LOGIN, autologin);
	}

	public boolean isFirstlogin() {
		return getPreference().getBoolean(FIRST_LOGIN, true);
	}

	public void saveFirstLogin(Boolean firstlogin) {
		saveValue(FIRST_LOGIN, firstlogin);
	}

	/**
	 * 获取配件信息模型
	 * 
	 * @return
	 */
	public FarmHouseConfig getKysConfig() {
		FarmHouseConfig kysFarmConfig = new FarmHouseConfig();
		try {
			Map<String, ?> configMap = getPreference().getAll();
			Method[] methods = FarmHouseConfig.class.getMethods();
			Map<String, Method> getMethodMap = new HashMap<String, Method>();
			List<Method> setMethodList = new ArrayList<Method>();
			for (Method method : methods) {
				if (method.getModifiers() == Method.DECLARED) {
					String methodName = method.getName();
					if (methodName.startsWith("get")
							|| methodName.startsWith("is")) {
						getMethodMap.put(methodName, method);
					} else if (methodName.startsWith("set")) {
						setMethodList.add(method);
					}
				}
			}
			for (Method method : setMethodList) {
				String setMethodName = method.getName();
				String key = setMethodName.replaceFirst("set", "");
				String getMethodName = "get" + key;
				if (!getMethodMap.containsKey(getMethodName)) {
					getMethodName = "is" + key;
				}
				if (!getMethodMap.containsKey(getMethodName)) {
					continue;
				}
				Object initValue = getMethodMap.get(getMethodName).invoke(
						kysFarmConfig);
				method.invoke(kysFarmConfig,
						getValue(configMap, key, initValue));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return kysFarmConfig;
	}

	/**
	 * @param
	 */
	public void saveConfig(FarmHouseConfig KysFarmConfig) {
		try {
			Method[] methods = FarmHouseConfig.class.getMethods();
			for (Method method : methods) {
				if (method.getModifiers() == Method.DECLARED) {
					String setMethodName = method.getName();
					String key = null;
					if (setMethodName.startsWith("get")) {
						key = setMethodName.replaceFirst("get", "");
					} else if (setMethodName.startsWith("is")) {
						key = setMethodName.replaceFirst("is", "");
					}
					if (key != null) {
						Object initValue = method.invoke(KysFarmConfig);
						saveValue(key, initValue);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public <T> T getValue(Map<String, ?> configMap, String key, T value) {
		T result = null;
		Editor editor = getPreference().edit();
		if (configMap.containsKey(key)) {
			Object obj = configMap.get(key);
			if (obj.getClass() == value.getClass()) {
				result = (T) obj;
			} else {
				editor.remove(key);
			}
		}
		if (result == null) {

			if (value instanceof String)
				editor.putString(key, (String) value);
			else if (value instanceof Integer)
				editor.putInt(key, (Integer) value);
			else if (value instanceof Boolean)
				editor.putBoolean(key, (Boolean) value);
			else if (value instanceof Float)
				editor.putFloat(key, (Float) value);
			else if (value instanceof Long)
				editor.putLong(key, (Long) value);

			editor.commit();
			result = value;
		}
		return result;
	}

	/**
	 * @param key
	 * @param value
	 */
	public <T> void saveValue(String key, T value) {
		Editor editor = getPreference().edit();
		if (value instanceof String)
			editor.putString(key, (String) value);
		else if (value instanceof Integer)
			editor.putInt(key, (Integer) value);
		else if (value instanceof Boolean)
			editor.putBoolean(key, (Boolean) value);
		else if (value instanceof Float)
			editor.putFloat(key, (Float) value);
		else if (value instanceof Long)
			editor.putLong(key, (Long) value);
		editor.commit();
	}
}
