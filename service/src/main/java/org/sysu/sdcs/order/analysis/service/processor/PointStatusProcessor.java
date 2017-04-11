package org.sysu.sdcs.order.analysis.service.processor;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Edge;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.PointStatus;
import org.sysu.sdcs.order.analysis.model.common.PointPair;
import org.sysu.sdcs.order.analysis.model.interfaces.Processor;
import org.sysu.sdcs.order.analysis.service.calculate.VarMemorizer;

@Component
public class PointStatusProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory.getLogger(PointStatusProcessor.class);
	@Autowired
	private VarMemorizer memorizer;
	private static double THRESHOLD = 0.20;

	@Override
	public void process() {
		try {
			LOGGER.info("Begin process point status.");
			Set<Map.Entry<String, Double>> distances = memorizer.getDistances().entrySet();
			for (Map.Entry<String, Double> distance : distances) {
				tryInsertStatus(distance);
			}
			fixDistance();
			LOGGER.info("Process point status success.");
		} catch (Exception ex) {
			LOGGER.error("Process point status fail.", ex);
		}
	}

	private void tryInsertStatus(Entry<String, Double> distance) {
		double gap = distance.getValue();
		if (distance.getValue() < THRESHOLD) {
			String[] key = distance.getKey().split("[:]");
			int first = Integer.parseInt(key[0]);
			int second = Integer.parseInt(key[1]);

			PointStatus firstStatus = getPointStatus(first);
			PointStatus secondeStatus = getPointStatus(second);

			firstStatus.addDensity();
			secondeStatus.addDensity();

			Edge edge = new Edge(gap, new PointPair(first, second));

			firstStatus.addEdge(edge);
			secondeStatus.addEdge(edge);

			if (firstStatus.getMinDistance() > gap) {
				firstStatus.setClosestPoint(second);
				// firstStatus.setMinDistance(gap);
			}

			if (secondeStatus.getMinDistance() > gap) {
				secondeStatus.setClosestPoint(first);
				// secondeStatus.setMinDistance(gap);
			}

			memorizer.getPointStatus().put(first, firstStatus);
			memorizer.getPointStatus().put(second, secondeStatus);
		}
	}

	private PointStatus getPointStatus(int index) {
		PointStatus status = memorizer.getPointStatus().get(index);
		if (status == null) {
			status = new PointStatus();
			status.setDensity(0);
			status.setMinDistance(Double.MAX_VALUE);
			status.setEdges(new ArrayList<Edge>());
		}
		return status;
	}

	private void fixDistance() {
		try {
			LOGGER.info("Begin fix point status distance.");
			Set<Map.Entry<Integer, PointStatus>> statusMaps = memorizer.getPointStatus().entrySet();
			for (Map.Entry<Integer, PointStatus> iStatusMap : statusMaps) {
				double minDistance = Double.MAX_VALUE;
				for (Map.Entry<Integer, PointStatus> jStatusMap : statusMaps) {
					if (iStatusMap.getKey().equals(jStatusMap.getKey())) {
						continue;
					}
					int iDensity = iStatusMap.getValue().getDensity();
					int jDensity = jStatusMap.getValue().getDensity();
					PointPair pointPair = getPointPair(iStatusMap.getKey(), jStatusMap.getKey());
					try {
						double jDistance = memorizer.getDistances().get(pointPair.toString());
						if (iDensity < jDensity && minDistance > jDistance) {
							minDistance = jDistance;
						}
					} catch (Exception ex) {
						LOGGER.error("Point status process get distance: {} fail.", pointPair.toString());
					}
				}
				iStatusMap.getValue().setMinDistance(minDistance);
			}
			LOGGER.info("Finish fix point status distance.");
		} catch (Exception ex) {
			LOGGER.error("Fix point status distance fail.", ex);
		}
	}

	private PointPair getPointPair(int first, int second) {
		int min = first < second ? first : second;
		int max = first >= second ? first : second;
		return new PointPair(min, max);
	}
}
