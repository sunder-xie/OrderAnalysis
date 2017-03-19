package org.sysu.sdcs.order.analysis.model.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderIndex extends HashMap<Long, Set<Long>> {
	public void add(Long key, Long value) {
		if (!super.containsKey(key)) {
			super.put(key, new HashSet<>());
		}
		super.get(key).add(value);
	}
	public List<Long> get(Long key) {
		return new ArrayList<Long>(super.get(key));
	}
}
