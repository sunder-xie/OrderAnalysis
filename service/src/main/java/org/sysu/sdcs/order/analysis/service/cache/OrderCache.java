package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderDetailMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderMapper;
import org.sysu.sdcs.order.analysis.model.bo.OrderModel;
import org.sysu.sdcs.order.analysis.model.po.Order;
import org.sysu.sdcs.order.analysis.model.po.OrderDetail;
import org.sysu.sdcs.order.analysis.service.abract.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abract.UpdateAble;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class OrderCache extends AbstractCache<OrderModel> implements UpdateAble {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCache.class);
	@Autowired
	private OrderMapper orderDAO;
	@Autowired
	private OrderDetailMapper orderDetailDAO;

	public void update() {
		LOGGER.info("Begin update order cache");
		List<Order> allOrder = orderDAO.findAll();
		for (Order order : allOrder) {
			List<OrderDetail> orderDetail = orderDetailDAO.findById(order.getId());
			addOrUpdate(order.getId(), POAdapter.convert(order,orderDetail));
		}
		LOGGER.info("Finish update goods cache, cache size:{}", allOrder.size());
	}

}
