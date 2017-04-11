package org.sysu.sdcs.order.analysis.service.executor;

import java.util.ArrayList;
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
import org.sysu.sdcs.order.analysis.model.local.object.CustomerModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.model.redis.entity.CustomerRedisEntity;
import org.sysu.sdcs.order.analysis.service.builder.CustomerRedisEntityBuilder;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.service.repository.CustomerRepository;
import org.sysu.sdcs.order.analysis.service.repository.OrderRepository;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;
import org.sysu.sdcs.order.analysis.utils.common.KeyBuilder;

@Service
public class CustomerAnalysisExecutor implements Executor {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerAnalysisExecutor.class);
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private IndexStore indexFactory;
	@Autowired
	private RedisDAO redisDAO;
	@Autowired
	private CustomerRedisEntityBuilder contextBuilder;

	@Override
	public void execute() throws Exception {
		Set<Entry<String, CustomerModel>> customers = customerRepository.getContent().entrySet();
		for (Entry<String, CustomerModel> customerMap : customers) {
			CustomerRedisEntity context = getContext(customerMap);
			String key = KeyBuilder.buildCustomerKey(customerMap.getKey());
			redisDAO.set(key, JSONUtil.serialize(context).getBytes());
			LOGGER.debug("Finish customer analysis, key: {}.", key);
		}
	}

	private CustomerRedisEntity getContext(Entry<String, CustomerModel> customerMap) {
		CustomerRedisEntity context = contextBuilder.getInstance();
		contextBuilder.addCustomer(context, customerMap.getValue());
		List<OrderModel> orders = getOrdersById(customerMap.getKey());
		for (OrderModel order : orders) {
			contextBuilder.addDate(context, order);
			contextBuilder.addOrder(context, order);
		}
		return context;
	}

	private List<OrderModel> getOrdersById(String customerId) {
		List<String> orderIds = indexFactory.get(IndexType.Customer, customerId);
		List<OrderModel> orders = new ArrayList<>();
		for (String id : orderIds) {
			try {
				orders.add(orderRepository.get(id));
			} catch (Exception e) {
				LOGGER.error("Get order list by customer id fail, customer id:{}.", id);
			}
		}
		return orders;
	}

}
