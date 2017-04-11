package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Cluster;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.ClusterModel;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Dimension;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Point;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.TypeCounter;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.TypeCounterComparator;
import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.service.email.TableBuilder;
import org.sysu.sdcs.order.analysis.utils.common.FileUtil;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;

import com.google.gson.reflect.TypeToken;

public class ClusterTest {
	private static final String ROOTPATH = "src\\main\\resources\\data\\";

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testReadFile() {
		String json = FileUtil.readAll(ROOTPATH + "cluster.json");
		Map<Integer, Cluster> cluster = JSONUtil.deserialize(json, new TypeToken<Map<Integer, Cluster>>() {
		}.getType());
		List<ClusterModel> model = getModel(cluster.entrySet());
		String html = new TableBuilder().getTable("Analysis", model);
		List<String> list = new ArrayList<String>();
		list.add(html);
		FileUtil.write(ROOTPATH + "clusterAnalysis.html", list);
	}

	private List<ClusterModel> getModel(Set<Entry<Integer, Cluster>> clusterMaps) {
		List<ClusterModel> result = new ArrayList<>();
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
			result.add(model);
		}
		return result;
	}

}
