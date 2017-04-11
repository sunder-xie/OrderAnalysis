package org.sysu.sdcs.order.analysis.model.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Counter<T> {
	private Map<T, Integer> data;

	public Counter() {
		data = new HashMap<T, Integer>();
	}

	public void add(T index) {
		Integer count = data.get(index);
		if (count == null) {
			data.put(index, 1);
		} else {
			data.put(index, count + 1);
		}
	}

	public int get(T index) {
		Integer count = data.get(index);
		if (count == null) {
			return 0;
		} else {
			return count.intValue();
		}
	}

	public int size() {
		return data.size();
	}

	public Set<Entry<T, Integer>> entrySet() {
		return data.entrySet();
	}
}
