package com.bohaigaoke.android.config;

import android.content.Context;
import android.os.Environment;

import com.bohaigaoke.android.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigUtil {

	public static String getPropertyFromFile(Context context, String filename,
			String name) {
		Properties properties = new Properties();
		try {
			InputStream in = context.getAssets().open(filename);
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String value = properties.getProperty(name);
		return value;
	}

	public static String getSdCardPath() {
		String path = "";

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			File sdDir = Environment.getExternalStorageDirectory();
			path = sdDir.toString();
		}
		return path;
	}

	public static void copyAssetFileToDisk(Context context, String filename,
			String desPath) {
		FileUtil.mkDirs(desPath);
		String desFilePath = desPath + (desPath.endsWith("/") ? "" : "/")
				+ filename;

		InputStream in;
		try {
			in = context.getAssets().open(filename);
			if (!FileUtil.isFileExit(desFilePath)) {
				FileUtil.copyFile(in, desFilePath);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
