package org.sysu.sdcs.order.analysis.utils.adapter;

import java.util.ArrayList;
import java.util.List;

import org.sysu.sdcs.order.analysis.model.bo.CustomerModel;
import org.sysu.sdcs.order.analysis.model.bo.GoodsModel;
import org.sysu.sdcs.order.analysis.model.bo.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.bo.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.bo.OrderModel;
import org.sysu.sdcs.order.analysis.model.bo.SupplierModel;
import org.sysu.sdcs.order.analysis.model.po.Customer;
import org.sysu.sdcs.order.analysis.model.po.Goods;
import org.sysu.sdcs.order.analysis.model.po.GoodsType;
import org.sysu.sdcs.order.analysis.model.po.Order;
import org.sysu.sdcs.order.analysis.model.po.OrderDetail;
import org.sysu.sdcs.order.analysis.model.po.Supplier;

public class POAdapter {
	public static CustomerModel convert(Customer customer) {
		CustomerModel model = new CustomerModel();
		model.setAddress(customer.getAddress());
		model.setAge(customer.getAge());
		model.setName(customer.getName());
		model.setSex(customer.getSex());
		return model;
	}
	
	public static GoodsTypeModel convert(GoodsType goodsType) {
		GoodsTypeModel model = new GoodsTypeModel();
		model.setDesc(goodsType.getDesc());
		return model;
	}
	
	public static SupplierModel convert(Supplier supplier) {
		SupplierModel model = new SupplierModel();
		model.setName(supplier.getName());
		model.setPhone(supplier.getPhone());
		return model;
	}
	
	public static GoodsModel convert(Goods goods) {
		GoodsModel model = new GoodsModel();
		model.setDesc(goods.getDesc());
		model.setName(goods.getName());
		model.setPrice(goods.getPrice());
		model.setStock(goods.getStock());
		model.setSupplier(goods.getSupplier());
		model.setType(goods.getType());
		return model;
	}

	public static OrderModel convert(Order order, List<OrderDetail> orderDetails) {
		OrderModel model = new OrderModel();
		model.setCustomer(order.getCustomer());
		model.setTime(order.getTime());
		List<OrderDetailModel> goods = new ArrayList<>();
		for(OrderDetail orderDetail : orderDetails) {
			OrderDetailModel goodsModel = new OrderDetailModel();
			goodsModel.setCount(orderDetail.getCount());
			goodsModel.setGoods(orderDetail.getGoods());
			goodsModel.setPrice(orderDetail.getPrice());
			goods.add(goodsModel);
		}
		model.setGoods(goods);
		return model;
	}
}
