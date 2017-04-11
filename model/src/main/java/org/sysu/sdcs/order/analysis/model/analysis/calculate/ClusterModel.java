package org.sysu.sdcs.order.analysis.model.analysis.calculate;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.common.Counter;

public class ClusterModel {
	private Counter<String> types;
	private List<TypeCounter> counter;

	public Counter<String> getTypes() {
		return types;
	}

	public void setTypes(Counter<String> types) {
		this.types = types;
	}

	public List<TypeCounter> getCounter() {
		return counter;
	}

	public void setCounter(List<TypeCounter> counter) {
		this.counter = counter;
	}
}
