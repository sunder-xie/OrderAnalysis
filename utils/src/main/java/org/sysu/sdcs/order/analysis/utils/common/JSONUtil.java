package org.sysu.sdcs.order.analysis.utils.common;


import java.lang.reflect.Type;

import org.sysu.sdcs.order.analysis.model.redis.entity.CustomerRedisEntity;

import com.google.gson.Gson;

public class JSONUtil {
	private static Gson gson = new Gson();
	
	public static String serialize(Object obj) {
		return gson.toJson(obj);
	}
	public static <T> T deserialize(String json,Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	public static <T> T deserialize(String json,Type classOfT) {
		return gson.fromJson(json, classOfT);
	}

}
