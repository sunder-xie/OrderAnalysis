package org.sysu.sdcs.order.analysis.utils.adapter;

import java.util.ArrayList;
import java.util.List;

import org.sysu.sdcs.order.analysis.model.database.entity.Customer;
import org.sysu.sdcs.order.analysis.model.database.entity.Goods;
import org.sysu.sdcs.order.analysis.model.database.entity.GoodsType;
import org.sysu.sdcs.order.analysis.model.database.entity.Order;
import org.sysu.sdcs.order.analysis.model.database.entity.OrderDetail;
import org.sysu.sdcs.order.analysis.model.database.entity.Supplier;
import org.sysu.sdcs.order.analysis.model.local.object.CustomerModel;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsModel;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.model.local.object.OrderModel;
import org.sysu.sdcs.order.analysis.model.local.object.SupplierModel;

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

	public static OrderModel convert(Order order) {
		OrderModel model = new OrderModel();
		model.setCustomer(order.getCustomer());
		model.setTime(order.getTime());
		
		return model;
	}
	
	public static OrderDetailModel convert(OrderDetail orderDetail) {
		OrderDetailModel model = new OrderDetailModel();
		model.setCount(orderDetail.getCount());
		model.setGoods(orderDetail.getGoods());
		model.setPrice(orderDetail.getPrice());
		return model;
	}
	
	public static List<OrderDetailModel> convert(List<OrderDetail> orderDetails) {
		List<OrderDetailModel> models = new ArrayList<>();
		for(OrderDetail orderDetail : orderDetails) {
			OrderDetailModel model = convert(orderDetail);
			models.add(model);
		}
		return models;
	}
}
