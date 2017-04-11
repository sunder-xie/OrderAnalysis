package org.sysu.sdcs.order.analysis.service.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Cluster;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.ClusterModel;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Point;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.PointStatus;

@Component
public class VarMemorizer {
	private List<Point> points;
	private Map<String, Double> distances;
	private Map<Integer, PointStatus> pointStatus;
	private Map<Integer, Cluster> clusters;
	private List<ClusterModel> clusterModel;

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Map<String, Double> getDistances() {
		return distances;
	}

	public void setDistances(Map<String, Double> distances) {
		this.distances = distances;
	}

	public Map<Integer, PointStatus> getPointStatus() {
		return pointStatus;
	}

	public void setPointStatus(Map<Integer, PointStatus> pointStatus) {
		this.pointStatus = pointStatus;
	}

	public Map<Integer, Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(Map<Integer, Cluster> clusters) {
		this.clusters = clusters;
	}

	public void initial() {
		points = new ArrayList<>();
		distances = new HashMap<>();
		pointStatus = new HashMap<>();
		clusters = new HashMap<>();
		clusterModel = new ArrayList<>();
	}

	public List<ClusterModel> getClusterModel() {
		return clusterModel;
	}

	public void setClusterModel(List<ClusterModel> clusterModel) {
		this.clusterModel = clusterModel;
	}
}
