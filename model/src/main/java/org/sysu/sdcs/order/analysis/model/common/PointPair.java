package org.sysu.sdcs.order.analysis.model.common;

public class PointPair {
	private int first;
	private int second;

	public PointPair(int first, int second) {
		this.first = first;
		this.second = second;
	}

	public int getAnother(int index) {
		return first + second - index;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object pointPair) {
		if (pointPair instanceof PointPair) {
			PointPair obj = (PointPair) pointPair;
			return this.toString().equals(obj.toString());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return String.format("%d:%d", first, second);
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
}
