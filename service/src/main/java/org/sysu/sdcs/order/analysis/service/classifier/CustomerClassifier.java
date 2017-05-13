package org.sysu.sdcs.order.analysis.service.classifier;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.redis.RedisDAO;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Cluster;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Dimension;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Point;
import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.model.redis.entity.CustomerRedisEntity;
import org.sysu.sdcs.order.analysis.service.calculate.VarMemorizer;
import org.sysu.sdcs.order.analysis.utils.calculate.Calculator;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;
import org.sysu.sdcs.order.analysis.utils.common.KeyBuilder;

@Service
public class CustomerClassifier {
	@Autowired
	private RedisDAO redisDAO;
	@Autowired
	private VarMemorizer memorizer;

	public int getClusterByCustomer(int id) {
		Point standard = getPoint(id);
		Set<Entry<Integer, Cluster>> clusters = memorizer.getClusters().entrySet();
		double minDistance = Double.MAX_VALUE;
		int clusterNum = 0;
		for (Entry<Integer, Cluster> clusterSet : clusters) {
			Cluster cluster = clusterSet.getValue();
			Set<Point> points = cluster.getPoints();
			for (Point point : points) {
				double distance = Calculator.calDistance(point, standard);
				if (distance < minDistance) {
					minDistance = distance;
					clusterNum = clusterSet.getKey();
				}
			}
		}
		return clusterNum;
		//return memorizer.getClusters().get(clusterNum);
	}

	private Point getPoint(int id) {
		byte[] json = redisDAO.get(KeyBuilder.buildCustomerKey(String.valueOf(id)));
		String content = redisDAO.getSerializer().deserialize(json);
		CustomerRedisEntity entity = JSONUtil.deserialize(content, CustomerRedisEntity.class);
		List<Map<String, Counter<Integer>>> typeGroups = entity.getTypeGroup();
		Counter<String> counter = new Counter<>();
		for (Map<String, Counter<Integer>> types : typeGroups) {
			Set<Entry<String, Counter<Integer>>> typeSets = types.entrySet();
			for (Entry<String, Counter<Integer>> typeSet : typeSets) {
				int total = getTotal(typeSet.getValue());
				counter.add(typeSet.getKey(), total);
			}
		}
		Point point = buildPoint(counter);
		return point;
	}

	private Point buildPoint(Counter<String> counter) {
		Point point = new Point();
		Set<Entry<String, Integer>> counterSet = counter.entrySet();
		for (Entry<String, Integer> entry : counterSet) {
			Dimension dimension = new Dimension(entry.getKey(), entry.getValue());
			point.add(dimension);
			point.setCount(point.getCount() + entry.getValue());
		}
		return point;
	}

	private int getTotal(Counter<Integer> value) {
		int result = 0;
		Set<Entry<Integer, Integer>> counterSet = value.entrySet();
		for (Entry<Integer, Integer> entry : counterSet) {
			result += entry.getValue();
		}
		return result;
	}

}
