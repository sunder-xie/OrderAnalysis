package org.sysu.sdcs.order.analysis.model.common;

public class Range<T> {

	private T max;
	private T min;

	public Range() {
	}

	public Range(T min, T max) {
		this.max = max;
		this.min = min;
	}

	public T getMax() {
		return max;
	}

	public T getMin() {
		return min;
	}

	public void setMax(T max) {
		this.max = max;
	}

	public void setMin(T min) {
		this.min = min;
	}
}
