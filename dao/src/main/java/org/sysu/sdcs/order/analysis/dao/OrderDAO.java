package org.sysu.sdcs.order.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderMapper;
import org.sysu.sdcs.order.analysis.model.po.Order;

@Component
public class OrderDAO {
	@Autowired
	private OrderMapper orderMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDAO.class);

	public Order findById(long id) {
		Order order = orderMapper.findById(id);
		return order;
	}

	public List<Order> findAll() {
		List<Order> allOrder = orderMapper.findAll();
		if (allOrder == null) {
			allOrder = new ArrayList<Order>();
			LOGGER.info("Order cache is empty.");
		}
		return allOrder;
	}
}
