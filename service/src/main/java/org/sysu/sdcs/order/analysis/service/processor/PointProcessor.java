package org.sysu.sdcs.order.analysis.service.processor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Dimension;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Point;
import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.interfaces.Processor;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.service.calculate.VarMemorizer;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.service.repository.OrderRepository;


@Component
public class PointProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory.getLogger(PointProcessor.class);
	@Autowired
	private OrderRepository orderCache;
	@Autowired
	private VarMemorizer memorizer;
	@Autowired
	private IndexStore indexStore;

	@Override
	public void process() {
		try {
			LOGGER.info("Begin process point.");
			Collection<OrderModel> orders = orderCache.getContent().values();
			List<Point> points = memorizer.getPoints();
			for (OrderModel order : orders) {
				List<OrderDetailModel> details = order.getDetails();
				Counter<String> vector = new Counter<>();
				int count = 0;
				for (OrderDetailModel detail : details) {
					String type = indexStore.get(IndexType.Goods2Type, detail.getGoods()).get(0);
					vector.add(type);
					count++;
				}
				Set<Map.Entry<String, Integer>> items = vector.entrySet();
				Point point = new Point();
				for (Map.Entry<String, Integer> item : items) {
					Dimension dimension = new Dimension(item.getKey(), item.getValue());
					point.add(dimension);
				}
				point.setCount(count);
				points.add(point);
			}
			LOGGER.info("Process point sucess.");
		} catch (Exception ex) {
			LOGGER.error("Process point fail.", ex);
		}
	}

}
