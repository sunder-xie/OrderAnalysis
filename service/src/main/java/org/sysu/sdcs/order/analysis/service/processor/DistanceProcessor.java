package org.sysu.sdcs.order.analysis.service.processor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Point;
import org.sysu.sdcs.order.analysis.model.common.PointPair;
import org.sysu.sdcs.order.analysis.model.interfaces.Processor;
import org.sysu.sdcs.order.analysis.service.calculate.VarMemorizer;
import org.sysu.sdcs.order.analysis.utils.calculate.Calculator;

@Component
public class DistanceProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DistanceProcessor.class);
	@Autowired
	private VarMemorizer memorizer;

	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

	@Override
	public void process() {
		try {
			LOGGER.info("Begin process distance.");
			int pointsSize = memorizer.getPoints().size();
			Map<String, Double> distances = new ConcurrentHashMap<>();
			int i = 0;
			int j = 1;
			List<Integer> intervals = Calculator.calInterval(pointsSize, 5);
			int begin = intervals.get(i);
			int end = intervals.get(j);
			fixedThreadPool = Executors.newFixedThreadPool(5);
			while (true) {
				calDistance(begin, end, distances);
				LOGGER.info("Calculate distance, begin: {}, end: {}.", begin, end);
				i++;
				j++;
				if (i >= intervals.size()) {
					break;
				} else {
					begin = intervals.get(i);
				}
				if (j >= intervals.size()) {
					end = pointsSize - 1;
				} else {
					end = intervals.get(j);
				}

			}
			fixedThreadPool.shutdown();
			while (!fixedThreadPool.awaitTermination(10, TimeUnit.SECONDS)) {
			}
			memorizer.setDistances(distances);
			LOGGER.info("Process distance sucess.");
		} catch (Exception ex) {
			LOGGER.error("Process distance fail.", ex);
		}
	}

	private void calDistance(int begin, int end, Map<String, Double> distances) {
		int pointsSize = memorizer.getPoints().size();
		List<Point> points = memorizer.getPoints();
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				for (int i = begin; i < end; i++) {
					if (i >= pointsSize) {
						break;
					}
					try {
						for (int j = i + 1; j < pointsSize; j++) {
							PointPair pointPair = new PointPair(i, j);
							double distance = Calculator.calDistance(points.get(pointPair.getFirst()),
									points.get(pointPair.getSecond()));
							distances.put(pointPair.toString(), distance);
						}
						LOGGER.info("Finish Point: {} calculation.", i);
					} catch (Exception ex) {
						LOGGER.error(String.format("Execute fail type: %s.", "Distance"), ex);
					}
				}
			}
		});
	}

	public ExecutorService getFixedThreadPool() {
		return fixedThreadPool;
	}

	public void setFixedThreadPool(ExecutorService fixedThreadPool) {
		this.fixedThreadPool = fixedThreadPool;
	}

}
