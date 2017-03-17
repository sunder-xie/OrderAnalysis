package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderDetailMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Order;
import org.sysu.sdcs.order.analysis.model.database.entity.OrderDetail;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.service.abract.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abract.UpdateAble;
import org.sysu.sdcs.order.analysis.service.cache.factory.CacheFactory;
import org.sysu.sdcs.order.analysis.service.cache.factory.CacheType;
import org.sysu.sdcs.order.analysis.service.index.factory.IndexFactory;
import org.sysu.sdcs.order.analysis.service.index.factory.IndexType;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class OrderCache extends AbstractCache<OrderModel> implements UpdateAble {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCache.class);
	@Autowired
	private OrderMapper orderDAO;
	@Autowired
	private OrderDetailMapper orderDetailDAO;
	@Autowired
	private CacheFactory cacheFactory;
	@Autowired
	private IndexFactory indexFactory;

	public void update() {
		indexFactory.initial();
		try {
			LOGGER.info("Begin update order cache.");
			long beginTime = System.currentTimeMillis();
			List<Order> allOrder = orderDAO.findAll();
			if (allOrder == null || allOrder.isEmpty()) {
				LOGGER.warn("Order cache is empty.");
				return;
			}
			for (Order order : allOrder) {
				List<OrderDetail> orderDetail = orderDetailDAO.findById(order.getId());
				OrderModel orderModel = POAdapter.convert(order, orderDetail);
				loadIndex(order.getId(), orderModel);
				addOrUpdate(order.getId(), orderModel);
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update order cache, size {}, spend {}ms.", allOrder.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update order cache fail.", ex);
		}
	}

	private void loadIndex(long id, OrderModel orderModel) {
		indexFactory.getIndex(IndexType.Customer).add(orderModel.getCustomer(), id);
		List<OrderDetailModel> orderDetails = orderModel.getGoods();
		for (OrderDetailModel orderDetail : orderDetails) {
			long goodsId = orderDetail.getGoods();
			try {
				GoodsCache goodsCache = (GoodsCache) cacheFactory.getCache(CacheType.Goods);
				GoodsModel goodsModel = goodsCache.getCache().get(goodsId);
				indexFactory.getIndex(IndexType.Goods).add(goodsId, id);
				indexFactory.getIndex(IndexType.GoodsType).add(goodsModel.getType(), id);
				indexFactory.getIndex(IndexType.Supplier).add(goodsModel.getSupplier(), id);
			} catch (Exception e) {
				LOGGER.error("Put index error, order id: {}, goods id: {}.", id, goodsId);
			}
		}
	}

}
