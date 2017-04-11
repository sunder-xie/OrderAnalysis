package org.sysu.sdcs.order.analysis.service.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderDetailMapper;
import org.sysu.sdcs.order.analysis.dao.mapper.OrderMapper;
import org.sysu.sdcs.order.analysis.model.common.Range;
import org.sysu.sdcs.order.analysis.model.database.entity.Order;
import org.sysu.sdcs.order.analysis.model.database.entity.OrderDetail;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.interfaces.Update;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsModel;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.model.repository.AbstractRepository;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class OrderRepository extends AbstractRepository<OrderModel> implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepository.class);
	@Autowired
	private OrderMapper orderDAO;
	@Autowired
	private OrderDetailMapper orderDetailDAO;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private GoodsTypeRepository goodsTypeRepository;
	@Autowired
	private IndexStore indexStore;

	public void update() {
		indexStore.initialOrderIndex();
		try {
			LOGGER.info("Begin update order repository.");
			long beginTime = System.currentTimeMillis();
			List<Order> allOrder = orderDAO.findAll();
			if (allOrder == null || allOrder.isEmpty()) {
				LOGGER.warn("Order repository is empty.");
				return;
			}
			for (Order order : allOrder) {
				OrderModel orderModel = getOrderModel(order);
				loadIndex(order.getId(), orderModel);
				addOrUpdate(order.getId(), orderModel);
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update order repository, size {}, spend {}ms.", allOrder.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update order repository fail.", ex);
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
		indexStore.add(IndexType.Customer, orderModel.getCustomer(), id);
		List<OrderDetailModel> orderDetails = orderModel.getDetails();
		for (OrderDetailModel orderDetail : orderDetails) {
			String goodsId = orderDetail.getGoods();
			try {
				GoodsModel goodsModel = goodsRepository.get(goodsId);
				refreshGoodsTypePrice(goodsModel);
				indexStore.add(IndexType.Goods, goodsId, id);
				indexStore.add(IndexType.GoodsType, goodsModel.getType(), id);
				indexStore.add(IndexType.Supplier, goodsModel.getSupplier(), id);
			} catch (Exception e) {
				LOGGER.error("Put index error, order id: {}, goods id: {}.", id, goodsId);
			}
		}
	}

	private void refreshGoodsTypePrice(GoodsModel goodsModel) {
		try {
			GoodsTypeModel goodsType = goodsTypeRepository.get(goodsModel.getType());
			double price = goodsModel.getPrice();
			if (goodsType.getPrice() == null) {
				goodsType.setPrice(new Range<Double>(price, price));
				return;
			}
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
