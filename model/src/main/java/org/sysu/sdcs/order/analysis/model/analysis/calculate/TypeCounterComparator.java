package org.sysu.sdcs.order.analysis.model.analysis.calculate;

import java.util.Comparator;

public class TypeCounterComparator implements Comparator<TypeCounter>{
	@Override
	public int compare(TypeCounter o1, TypeCounter o2) {
		return o1.compareTo(o2);
	}

}
