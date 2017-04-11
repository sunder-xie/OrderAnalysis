package org.sysu.sdcs.order.analysis.service.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Cluster;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.ClusterModel;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Dimension;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Edge;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Point;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.PointStatus;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.TypeCounter;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.TypeCounterComparator;
import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.model.interfaces.Processor;
import org.sysu.sdcs.order.analysis.service.calculate.VarMemorizer;

@Component
public class ClusterProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClusterProcessor.class);

	private static final int MIN_DENSITY = 15;

	private static final double MIN_DISTANCE = 0.3;
	@Autowired
	private VarMemorizer memorizer;

	@Override
	public void process() {
		try {

			LOGGER.info("Begin process cluster.");
			List<Integer> pointIndexes = selectCenter();
			fixCluster(pointIndexes);
			processCluster();
			LOGGER.info("Finish process cluster.");
		} catch (Exception ex) {
			LOGGER.error("Process cluster fail.", ex);
		}
	}

	private void processCluster() {
		Set<Entry<Integer, Cluster>> clusterMaps = memorizer.getClusters().entrySet();
		for (Entry<Integer, Cluster> clusterMap : clusterMaps) {
			ClusterModel model = new ClusterModel();
			model.setCounter(new ArrayList<TypeCounter>());
			model.setTypes(new Counter<String>());
			Cluster cluster = clusterMap.getValue();
			Set<Point> points = cluster.getPoints();
			for (Point point : points) {
				List<Dimension> dimensions = point.getDimensions();
				for (Dimension dimension : dimensions) {
					for (int i = 0; i < dimension.getLength(); i++) {
						model.getTypes().add(dimension.getType());
					}
				}
			}
			Set<Entry<String, Integer>> counters = model.getTypes().entrySet();
			for (Entry<String, Integer> counter : counters) {
				TypeCounter typeCounter = new TypeCounter(counter.getKey(), counter.getValue());
				model.getCounter().add(typeCounter);
			}
			Collections.sort(model.getCounter(), new TypeCounterComparator());
			memorizer.getClusterModel().add(model);
		}
	}

	private void fixCluster(List<Integer> pointIndexes) {
		Map<Integer, Cluster> clusters = new HashMap<>();
		List<Point> pointList = memorizer.getPoints();
		Set<Integer> hasTraverse = new HashSet<>();
		for (Integer pointIndex : pointIndexes) {
			if (hasTraverse.contains(pointIndex)) {
				continue;
			}
			Cluster cluster = new Cluster();
			Point center = pointList.get(pointIndex);
			cluster.setCenter(center);
			cluster.setPoints(new HashSet<Point>());
			insertPoint(cluster, pointIndex, hasTraverse);
			if (cluster.getPoints().size() > 5) {
				clusters.put(pointIndex, cluster);
			}
		}
		memorizer.setClusters(clusters);
	}

	private void insertPoint(Cluster cluster, Integer pointIndex, Set<Integer> hasTraverse) {
		Map<Integer, PointStatus> statusMaps = memorizer.getPointStatus();
		List<Point> points = memorizer.getPoints();
		PointStatus status = statusMaps.get(pointIndex);
		List<Edge> edges = status.getEdges();
		for (Edge edge : edges) {
			int index = edge.getPointPair().getAnother(pointIndex);
			if (hasTraverse.contains(index)) {
				continue;
			}
			hasTraverse.add(index);
			cluster.getPoints().add(points.get(index));
			insertPoint(cluster, index, hasTraverse);
		}
	}

	// private void insertPoint(int index, PointStatus status, int root, boolean
	// isFirst) {
	// Map<Integer, PointStatus> statusMaps = memorizer.getPointStatus();
	// List<Point> points = memorizer.getPoints();
	// Map<PointPair, Double> distances = memorizer.getDistances();
	// Map<Integer, Cluster> clusters = memorizer.getClusters();
	// if (index == root && !isFirst) {
	// double minDistance = Double.MAX_VALUE;
	// int minIndex = 0;
	// for (Entry<Integer, PointStatus> statusMap : statusMaps.entrySet()) {
	// if (index == statusMap.getKey()) {
	// continue;
	// }
	// PointPair pointPair = new PointPair(statusMap.getKey(), index);
	// double distance = distances.get(pointPair);
	// if (distance < minDistance) {
	// minIndex = statusMap.getKey();
	// minDistance = distance;
	// }
	// }
	// clusters.get(minIndex).getPoints().add(points.get(index));
	// }
	// PointStatus nextPoint = statusMaps.get(status.getClosestPoint());
	// if (nextPoint.getClosestPoint() == index) {
	// // return;
	// }
	// int closestPoint = status.getClosestPoint();
	// if (clusters.containsKey(closestPoint)) {
	// clusters.get(closestPoint).getPoints().add(points.get(closestPoint));
	// } else {
	// insertPoint(closestPoint, statusMaps.get(closestPoint), root, false);
	// }
	// }

	private List<Integer> selectCenter() {
		List<Integer> result = new ArrayList<>();
		Set<Map.Entry<Integer, PointStatus>> statusMaps = memorizer.getPointStatus().entrySet();
		for (Map.Entry<Integer, PointStatus> statusMap : statusMaps) {
			PointStatus status = statusMap.getValue();
			boolean isMatchCenter = isMatchCenter(status);
			if (isMatchCenter) {
				result.add(statusMap.getKey());
			}
		}
		return result;
	}

	private boolean isMatchCenter(PointStatus status) {
		if (status.getDensity() > MIN_DENSITY && status.getMinDistance() >= MIN_DISTANCE) {
			return true;
		}
		return false;
	}

}
