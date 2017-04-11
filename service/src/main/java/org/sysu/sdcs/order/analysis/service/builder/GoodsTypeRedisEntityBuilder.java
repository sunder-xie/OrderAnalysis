package org.sysu.sdcs.order.analysis.service.builder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.model.redis.entity.GoodsTypeRedisEntity;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.service.repository.GoodsTypeRepository;
import org.sysu.sdcs.order.analysis.utils.adapter.PriceQuantifier;
import org.sysu.sdcs.order.analysis.utils.common.DateUtil;

@Component
public class GoodsTypeRedisEntityBuilder {

	@Autowired
	private IndexStore indexFactory;
	@Autowired
	private GoodsTypeRepository goodsTypeCache;

	public GoodsTypeRedisEntity addGoodsType(GoodsTypeRedisEntity context, GoodsTypeModel model) {
		context.setType(model);
		return context;
	}

	public GoodsTypeRedisEntity addDate(GoodsTypeRedisEntity context, OrderModel order) {
		String dateMark = DateUtil.format(order.getTime());
		context.getDateGroup().add(dateMark);
		return context;
	}

	public GoodsTypeRedisEntity addOrder(GoodsTypeRedisEntity context, OrderModel order) {
		List<OrderDetailModel> details = order.getDetails();
		Set<String> typeGroup = new HashSet<>();
		for (OrderDetailModel detail : details) {
			String typeId = indexFactory.getType(detail);
			typeGroup.add(typeId);
			addPriceGroup(context, typeId, detail);
		}
		Counter<Set<String>> typeGroups = context.getTypeGroup();
		typeGroups.add(typeGroup);
		return context;
	}

	private void addPriceGroup(GoodsTypeRedisEntity context, String type, OrderDetailModel detail) {
		double price = detail.getPrice();
		GoodsTypeModel goodsType = goodsTypeCache.get(type);
		Integer level = PriceQuantifier.getLevel(goodsType, price);
		Counter<Integer> priceGroup = context.getPriceGroup();
		priceGroup.add(level);
	}

	public GoodsTypeRedisEntity getInstance() {
		GoodsTypeRedisEntity context = new GoodsTypeRedisEntity();

		Counter<String> dateGroup = new Counter<>();
		Counter<Integer> priceGroup = new Counter<>();
		Counter<Set<String>> typeGroup = new Counter<>();

		context.setDateGroup(dateGroup);
		context.setPriceGroup(priceGroup);
		context.setTypeGroup(typeGroup);

		return context;
	}
}
