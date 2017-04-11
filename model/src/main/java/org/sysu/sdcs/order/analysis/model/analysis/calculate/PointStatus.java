package org.sysu.sdcs.order.analysis.model.analysis.calculate;

import java.util.List;

public class PointStatus {
	private int density;
	private double minDistance;
	private int closestPoint;
	private List<Edge> edges;

	public int getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public void addDensity() {
		this.density++;
	}

	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	public double getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(double minDistance) {
		this.minDistance = minDistance;
	}

	public int getClosestPoint() {
		return closestPoint;
	}

	public void setClosestPoint(int closestPoint) {
		this.closestPoint = closestPoint;
	}

}
