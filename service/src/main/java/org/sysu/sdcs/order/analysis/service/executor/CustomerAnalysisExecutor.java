package org.sysu.sdcs.order.analysis.service.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.redis.RedisDAO;
import org.sysu.sdcs.order.analysis.model.analysis.context.AnalysisContext;
import org.sysu.sdcs.order.analysis.model.enums.CacheType;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractExecutor;
import org.sysu.sdcs.order.analysis.service.builder.AnalysisContextBuilder;
import org.sysu.sdcs.order.analysis.service.cache.CustomerCache;
import org.sysu.sdcs.order.analysis.service.cache.OrderCache;
import org.sysu.sdcs.order.analysis.service.factory.cache.CacheFactory;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexFactory;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;
import org.sysu.sdcs.order.analysis.utils.common.KeyBuilder;

@Service
public class CustomerAnalysisExecutor extends AbstractExecutor {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAnalysisExecutor.class);
	@Autowired
	private CacheFactory cacheFactory;
	@Autowired
	private IndexFactory indexFactory;
	@Autowired
	private RedisDAO redisDAO;
	@Autowired
	private AnalysisContextBuilder contextBuilder;

	@Override
	public void execute() throws Exception {
		CustomerCache cacheCache = (CustomerCache) cacheFactory.getInstance(CacheType.Customer);
		Set<Long> customerIds = cacheCache.getCache().keySet();
		for (Long customerId : customerIds) {
			AnalysisContext context = getContext(customerId);
			String key = KeyBuilder.buildCustomerKey(customerId);
			redisDAO.set(key, JSONUtil.serialize(context).getBytes());
			LOGGER.debug("Finish customer analysis, key: {}.", key);
		}
	}

	private AnalysisContext getContext(Long customerId) {
		AnalysisContext context = new AnalysisContext();
		List<OrderModel> orders = getOrdersById(customerId);
		for (OrderModel order : orders) {
			contextBuilder.addDate(context, order);
			contextBuilder.addType(context, order);
		}
		return context;
	}

	private List<OrderModel> getOrdersById(long customerId) {
		List<Long> orderIds = indexFactory.get(IndexType.Customer, customerId);
		List<OrderModel> orders = new ArrayList<>();
		for (Long id : orderIds) {
			try {
				OrderCache orderCache = (OrderCache) cacheFactory.getInstance(CacheType.Order);
				orders.add(orderCache.get(id));
			} catch (Exception e) {
				LOGGER.error("Get order list by customer id fail, customer id:{}.", id);
			}
		}
		return orders;
	}

}
