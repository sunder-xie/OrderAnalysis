package org.sysu.sdcs.order.analysis.service.abract;

import java.util.HashMap;
import java.util.Map;

public class AbstractCache<T> {
	private Map<Long, T> cache = new HashMap<Long, T>();

	public void addOrUpdate(Long key, T value) {
		cache.put(key, value);
	}

	public void clear() {
		cache.clear();
	}

	public void delete(Long key) {
		if (cache.containsKey(key)) {
			cache.remove(key);
		}
	}
	
	public Map<Long,T> getCache() {
		return cache;
	}
}
