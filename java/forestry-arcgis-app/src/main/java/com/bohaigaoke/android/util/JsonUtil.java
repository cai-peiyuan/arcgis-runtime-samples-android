package com.bohaigaoke.android.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;


/**
 * <b>JSON资料格式处理类</b>
 * 
 * @author cai_peiyuan
 * @since 1.0
 * @date 2016-8-26 11:23:18
 */
public class JsonUtil {


	private static Gson gson;

	static {
		GsonBuilder builder = new GsonBuilder();
		gson = builder.create();
	}

	
	/**
	 * 将Json字符串转换为Java对象
	 * 
	 * @param json
	 * @param type
	 *            如果Java对象是一个普通类则直接JsonUtils.fromJson(json,
	 *            HashDto.class);即可。如果是一个泛型类(如一个dto集合类)则需要
	 *            使用如下方式传参：JsonUtils.fromJson(json, new TypeToken<List
	 *            <HashDto>>() {}.getType());
	 * @return
	 */
	public static final <T> T fromJson(String json, Type type) {
		T list = gson.fromJson(json, type);
		return list;
	}

	
}