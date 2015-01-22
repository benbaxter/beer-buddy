package com.beerbuddy.core.utils;

import java.util.Map;

/**
 * A simple map utils for casting values in a Map<?, Objects>
 */
public interface MapUtils {

	public static String getString(Map<?, Object> map, Object key) {
		return get(map, key, String.class);
	}
	
	public static Integer getInteger(Map<?, Object> map, Object key) {
		return get(map, key, Integer.class);
	}
	
	public static Boolean getBoolean(Map<?, Object> map, Object key) {
		return get(map, key, Boolean.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Map<?, Object> map, Object key, Class<T> clazz) {
		return (T) map.get(key);
	}
		
}