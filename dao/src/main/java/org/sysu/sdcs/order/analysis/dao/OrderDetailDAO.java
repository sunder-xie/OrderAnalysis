package org.sysu.sdcs.order.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderDetailMapper;
import org.sysu.sdcs.order.analysis.model.po.OrderDetail;

@Component
public class OrderDetailDAO {
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailDAO.class);

	public List<OrderDetail> findById(long id) {
		List<OrderDetail> orderDetail = orderDetailMapper.findById(id);
		return orderDetail;
	}

	public List<OrderDetail> findAll() {
		List<OrderDetail> allOrderDetail = orderDetailMapper.findAll();
		if (allOrderDetail == null) {
			allOrderDetail = new ArrayList<OrderDetail>();
			LOGGER.info("Order cache is empty.");
		}
		return allOrderDetail;
	}
}
