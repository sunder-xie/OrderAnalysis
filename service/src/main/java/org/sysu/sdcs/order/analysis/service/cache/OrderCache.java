package org.sysu.sdcs.order.analysis.service.cache;

import java.util.ArrayList;
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
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.service.basic.AbstractCache;
import org.sysu.sdcs.order.analysis.service.factory.cache.CacheFactory;
import org.sysu.sdcs.order.analysis.service.factory.cache.CacheType;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexFactory;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexType;
import org.sysu.sdcs.order.analysis.service.interfaces.Update;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class OrderCache extends AbstractCache<OrderModel> implements Update {
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
				OrderModel orderModel = getOrderModel(order);
				loadIndex(order.getId(), orderModel);
				addOrUpdate(order.getId(), orderModel);
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update order cache, size {}, spend {}ms.", allOrder.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update order cache fail.", ex);
		}
	}

	private OrderModel getOrderModel(Order order) throws Exception {
		OrderModel orderModel = POAdapter.convert(order);
		List<OrderDetail> orderDetails = orderDetailDAO.findById(order.getId());
		List<OrderDetailModel> orderDetailModels = new ArrayList<>();
		for (OrderDetail orderDetail : orderDetails) {
			OrderDetailModel orderDetailModel = POAdapter.convert(orderDetail);
			orderDetailModels.add(orderDetailModel);
		}
		orderModel.setDetails(orderDetailModels);
		return orderModel;
	}

	private void loadIndex(long id, OrderModel orderModel) {
		indexFactory.add(IndexType.Customer, orderModel.getCustomer(), id);
		List<OrderDetailModel> orderDetails = orderModel.getDetails();
		for (OrderDetailModel orderDetail : orderDetails) {
			long goodsId = orderDetail.getGoods();
			try {
				GoodsCache goodsCache = (GoodsCache) cacheFactory.getInstance(CacheType.Goods);
				GoodsModel goodsModel = goodsCache.get(goodsId);
				refreshGoodsTypePrice(goodsModel);
				indexFactory.add(IndexType.Goods, goodsId, id);
				indexFactory.add(IndexType.GoodsType, goodsModel.getType(), goodsId);
				indexFactory.add(IndexType.Supplier, goodsModel.getSupplier(), id);
			} catch (Exception e) {
				LOGGER.error("Put index error, order id: {}, goods id: {}.", id, goodsId);
			}
		}
	}

	private void refreshGoodsTypePrice(GoodsModel goodsModel) {
		try {
			GoodsTypeCache goodsTypeCache = (GoodsTypeCache) cacheFactory.getInstance(CacheType.GoodsType);
			GoodsTypeModel goodsType = goodsTypeCache.get(goodsModel.getType());
			double price = goodsModel.getPrice();
			Double max = goodsType.getMaxPrice();
			Double min = goodsType.getMinPrice();
			if (min == null || min.isNaN()) {
				goodsType.setMinPrice(goodsModel.getPrice());
			} else if (min > price) {
				goodsType.setMinPrice(price);
			}
			if (max == null || max.isNaN()) {
				goodsType.setMaxPrice(goodsModel.getPrice());
			} else if (max < price) {
				goodsType.setMaxPrice(price);
			}
		} catch (Exception ex) {
			LOGGER.error("Refresh goods type price fail.", ex);
		}

	}

}
