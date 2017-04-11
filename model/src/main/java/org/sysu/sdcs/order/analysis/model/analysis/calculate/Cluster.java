package org.sysu.sdcs.order.analysis.model.analysis.calculate;

import java.util.Set;

public class Cluster {
	private Set<Point> points;
	private Point center;

	public Set<Point> getPoints() {
		return points;
	}

	public void setPoints(Set<Point> points) {
		this.points = points;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
}
