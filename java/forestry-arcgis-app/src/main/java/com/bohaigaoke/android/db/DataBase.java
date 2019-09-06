package com.bohaigaoke.android.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bohaigaoke.forestry.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DataBase {
	private Context context = null;
	private SQLiteDatabase db = null;
	private final String DATABASE_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/bohaigaoke/db";
	private final String DATABASE_FILENAME = "gis_app_db.db";

	public DataBase(Context context) {
		this.context = context;
		try {
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			if (!dir.exists()) {
				dir.mkdirs(); // 创建目录
			}
			if (!(new File(databaseFilename)).exists()) {
				InputStream is = context.getResources().openRawResource(R.raw.gis_app_db);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	private SQLiteDatabase openDatabase() {
		try {
			String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);
			if (!dir.exists()) {
				dir.mkdirs(); // 创建目录
			}

			if (!(new File(databaseFilename)).exists()) {
				InputStream is = context.getResources().openRawResource(R.raw.gis_app_db);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} /*else {
				// 如果存在 则删除替换
				(new File(databaseFilename)).delete();
				InputStream is = context.getResources().openRawResource(
						R.raw.gis_app_db);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}*/
			if (db != null) {
				if (!db.isOpen()) {
					db = SQLiteDatabase.openOrCreateDatabase(databaseFilename,
							null);
				}
			} else {
				db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
			}

			return db;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 执行insert、update、delete等SQL语句
	public void execSQL(String sql, Object[] args) {
		try {
			db = this.openDatabase();
			db.execSQL(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	// 执行select语句
	public Cursor query(String sql, String[] args) {
		Cursor cursor = null;
		try {
			db = this.openDatabase();
			cursor = db.rawQuery(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.close(); db连接不能关闭
		}
		return cursor;
	}

}
