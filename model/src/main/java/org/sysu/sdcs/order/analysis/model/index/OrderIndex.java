package org.sysu.sdcs.order.analysis.model.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderIndex extends HashMap<String, Set<String>> {
	private static final long serialVersionUID = 604516952680909996L;

	public void add(String key, long value) {
		if (!super.containsKey(key)) {
			super.put(key, new HashSet<>());
		}
		super.get(key).add(String.valueOf(value));
	}

	public List<String> get(String key) {
		Set<String> result = super.get(key);
		if(result == null){
			return new ArrayList<String>();
		}
		return new ArrayList<String>(result);
	}
}
