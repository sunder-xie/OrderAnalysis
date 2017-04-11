package org.sysu.sdcs.order.analysis.service.executor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.redis.RedisDAO;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.interfaces.Executor;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.model.redis.entity.GoodsTypeRedisEntity;
import org.sysu.sdcs.order.analysis.service.builder.GoodsTypeRedisEntityBuilder;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.service.repository.GoodsTypeRepository;
import org.sysu.sdcs.order.analysis.service.repository.OrderRepository;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;
import org.sysu.sdcs.order.analysis.utils.common.KeyBuilder;

@Service
public class GoodsTypeAnalysisExecutor implements Executor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsTypeAnalysisExecutor.class);
	@Autowired
	private GoodsTypeRepository goodsTypeCache;
	@Autowired
	private OrderRepository orderCache;
	@Autowired
	private IndexStore indexFactory;
	@Autowired
	private RedisDAO redisDAO;
	@Autowired
	private GoodsTypeRedisEntityBuilder contextBuilder;

	@Override
	public void execute() throws Exception {
		Set<Entry<String, GoodsTypeModel>> goodsTypes = goodsTypeCache.getContent().entrySet();
		for (Entry<String, GoodsTypeModel> goodsTypeMap : goodsTypes) {
			GoodsTypeRedisEntity context = getContext(goodsTypeMap);
			String key = KeyBuilder.buildGoodsTypeKey(goodsTypeMap.getKey());
			redisDAO.set(key, JSONUtil.serialize(context).getBytes());
			LOGGER.debug("Finish goods type analysis, key: {}.", key);
		}
	}

	private GoodsTypeRedisEntity getContext(Entry<String, GoodsTypeModel> goodsTypeMap) {
		GoodsTypeRedisEntity context = contextBuilder.getInstance();
		contextBuilder.addGoodsType(context, goodsTypeMap.getValue());
		List<OrderModel> orders = getOrdersById(goodsTypeMap.getKey());
		for (OrderModel order : orders) {
			contextBuilder.addDate(context, order);
			contextBuilder.addOrder(context, order);
		}
		return context;
	}

	private List<OrderModel> getOrdersById(String goodsTypeId) {
		List<String> orderIndexes = indexFactory.get(IndexType.GoodsType, goodsTypeId);
		Set<String> orderIds = new HashSet<>();
		orderIds.addAll(orderIndexes);
		List<OrderModel> orders = new ArrayList<>();
		for (String id : orderIds) {
			try {
				orders.add(orderCache.get(id));
			} catch (Exception e) {
				LOGGER.error("Get order list by customer id fail, customer id:{}.", id);
			}
		}
		return orders;
	}

}
