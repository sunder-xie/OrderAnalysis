package org.sysu.sdcs.order.analysis.service.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.analysis.context.AnalysisContext;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexFactory;
import org.sysu.sdcs.order.analysis.utils.common.DateUtil;

@Component
public class AnalysisContextBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisContextBuilder.class);
	@Autowired
	private IndexFactory indexFactory;

	public void addDate(AnalysisContext context, OrderModel order) {
		if (context.getDateDistribution() == null) {
			context.setDateDistribution(new HashMap<String, Integer>());
		}
		Map<String, Integer> distribution = context.getDateDistribution();
		String dateMark = DateUtil.format(order.getTime());
		if (distribution.containsKey(dateMark)) {
			int count = distribution.get(dateMark).intValue();
			distribution.put(dateMark, new Integer(++count));
		} else {
			distribution.put(dateMark, new Integer(1));
		}
	}

	public void addType(AnalysisContext context, OrderModel order) {
		checkContextNotNull(context);
		List<OrderDetailModel> orderDetails = order.getDetails();
		Set<Long> goodsTypeDistribution = new HashSet<>();
		try {
			for (OrderDetailModel orderDetail : orderDetails) {
				Long goodType = addTypePriceDistribution(context, orderDetail);
				goodsTypeDistribution.add(goodType);
			}
			context.getTypeDistribution().add(goodsTypeDistribution);
		} catch (Exception ex) {
			LOGGER.error(String.format("Add goods type distribution fail, customer id: %d.", order.getCustomer()), ex);
		}
	}

	private Long getType(OrderDetailModel orderDetail) {
		return indexFactory.get(IndexType.Goods2Type, orderDetail.getGoods()).get(0);
	}

	private void checkContextNotNull(AnalysisContext context) {
		if (context.getTypePriceDistribution() == null) {
			Map<Long, List<Double>> typePriceDistribution = new HashMap<>();
			context.setTypePriceDistribution(typePriceDistribution);
		}
		if (context.getTypeDistribution() == null) {
			List<Set<Long>> typeDistribution = new ArrayList<Set<Long>>();
			context.setTypeDistribution(typeDistribution);
		}
	}

	private Long addTypePriceDistribution(AnalysisContext context, OrderDetailModel orderDetail) {
		Map<Long, List<Double>> distribution = context.getTypePriceDistribution();
		long goodsTypeId = getType(orderDetail);
		if (distribution.containsKey(goodsTypeId)) {
			distribution.get(goodsTypeId).add(orderDetail.getPrice());
		} else {
			List<Double> prices = new ArrayList<Double>();
			prices.add(orderDetail.getPrice());
			distribution.put(goodsTypeId, prices);
		}
		return goodsTypeId;
	}

}
