package org.sysu.sdcs.order.analysis.model.index;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OrderIndex extends HashMap<Long, Set<Long>> {
	public void add(Long key, Long value) {
		if (this.get(key) == null) {
			this.put(key, new HashSet<>());
		}
		this.get(key).add(value);
	}
}
