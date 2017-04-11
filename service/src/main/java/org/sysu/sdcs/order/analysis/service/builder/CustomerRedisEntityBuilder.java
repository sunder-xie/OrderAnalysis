package org.sysu.sdcs.order.analysis.service.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.model.local.object.CustomerModel;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsModel;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.model.redis.entity.CustomerRedisEntity;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.service.repository.GoodsRepository;
import org.sysu.sdcs.order.analysis.service.repository.GoodsTypeRepository;
import org.sysu.sdcs.order.analysis.utils.adapter.PriceQuantifier;
import org.sysu.sdcs.order.analysis.utils.common.DateUtil;

@Component
public class CustomerRedisEntityBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRedisEntityBuilder.class);
	@Autowired
	private IndexStore indexFactory;
	@Autowired
	private GoodsTypeRepository goodsTypeCache;
	@Autowired
	private GoodsRepository goodsCache;

	public CustomerRedisEntity addDate(CustomerRedisEntity context, OrderModel order) {
		String dateMark = DateUtil.format(order.getTime());
		context.getDateGroup().add(dateMark);
		return context;
	}

	public CustomerRedisEntity addOrder(CustomerRedisEntity context, OrderModel order) {
		List<OrderDetailModel> orderDetails = order.getDetails();
		try {
			Map<String, Counter<Integer>> priceLevels = new HashMap<>();
			for (OrderDetailModel orderDetail : orderDetails) {
				priceLevels = addPriceLevel(priceLevels, orderDetail);
				context = addSupplier(context, orderDetail);
			}
			context.getTypeGroup().add(priceLevels);
		} catch (Exception ex) {
			LOGGER.error(String.format("Add goods type distribution fail, customer id: %d.", order.getCustomer()), ex);
		}
		return context;
	}

	private CustomerRedisEntity addSupplier(CustomerRedisEntity context, OrderDetailModel orderDetail) {
		Counter<String> suppliers = context.getSupplierGroup();
		String goodsId = orderDetail.getGoods();
		GoodsModel goods = goodsCache.get(goodsId);
		String supplier = goods.getSupplier();
		suppliers.add(supplier);
		return context;
	}

	private Map<String, Counter<Integer>> addPriceLevel(Map<String, Counter<Integer>> priceLevels,
			OrderDetailModel orderDetail) {
		String goodsTypeId = indexFactory.getType(orderDetail);

		GoodsTypeModel goodsType = goodsTypeCache.get(goodsTypeId);
		double price = orderDetail.getPrice();

		Integer level = PriceQuantifier.getLevel(goodsType, price);

		Counter<Integer> priceLevel = priceLevels.get(goodsTypeId);
		if (priceLevel == null) {
			priceLevel = new Counter<>();
		}
		priceLevel.add(level);
		priceLevels.put(goodsTypeId, priceLevel);
		return priceLevels;
	}

	public CustomerRedisEntity addCustomer(CustomerRedisEntity context, CustomerModel value) {
		context.setCustomer(value);
		return context;
	}

	public CustomerRedisEntity getInstance() {
		CustomerRedisEntity context = new CustomerRedisEntity();
		Counter<String> dateDistribution = new Counter<>();
		List<Map<String, Counter<Integer>>> typeGroup = new ArrayList<>();
		Counter<String> supplierGroup = new Counter<>();

		context.setDateGroup(dateDistribution);
		context.setTypeGroup(typeGroup);
		context.setSupplierGroup(supplierGroup);
		return context;
	}

}
