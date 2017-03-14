package org.sysu.sdcs.order.analysis.utils;


import com.google.gson.Gson;

public class JSONUtil {
	private static Gson gson = new Gson();
	
	public static String serialize(Object obj) {
		return gson.toJson(obj);
	}
	public static <T> T deserialize(String json,Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
}
