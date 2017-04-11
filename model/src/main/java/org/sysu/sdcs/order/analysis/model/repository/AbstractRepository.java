package org.sysu.sdcs.order.analysis.model.repository;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository<T> {
	private Map<String, T> cache = new HashMap<String, T>();

	public void addOrUpdate(long key, T value) {
		cache.put(String.valueOf(key), value);
	}

	public void clear() {
		cache.clear();
	}

	public void delete(String key) {
		if (cache.containsKey(key)) {
			cache.remove(key);
		}
	}

	public Map<String, T> getContent() {
		return cache;
	}

	public T get(String key) {
		return cache.get(key);
	}

	public void add(String key, T value) {
		cache.put(key, value);
	}
}
