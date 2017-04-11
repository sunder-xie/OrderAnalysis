package org.sysu.sdcs.order.analysis.model.analysis.calculate;

import org.sysu.sdcs.order.analysis.model.common.PointPair;

public class Edge implements Comparable<Edge> {

	private double length;
	private PointPair pointPair;

	public Edge(double length, PointPair pointPair) {
		super();
		this.length = length;
		this.pointPair = pointPair;
	}

	@Override
	public int compareTo(Edge obj) {
		if (length < obj.length) {
			return -1;
		} else if (length > obj.length) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		return pointPair.hashCode();
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public PointPair getPointPair() {
		return pointPair;
	}

	public void setPointPair(PointPair pointPair) {
		this.pointPair = pointPair;
	}
}
