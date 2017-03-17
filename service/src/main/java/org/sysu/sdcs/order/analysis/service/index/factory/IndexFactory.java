package org.sysu.sdcs.order.analysis.service.index.factory;

import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.index.OrderIndex;

@Component
public class IndexFactory {
	private OrderIndex customerIndex;
	private OrderIndex goodsIndex;
	private OrderIndex goodsTypeIndex;
	private OrderIndex supplierIndex;

	public void initial() {
		customerIndex = new OrderIndex();
		goodsIndex = new OrderIndex();
		goodsTypeIndex = new OrderIndex();
		supplierIndex = new OrderIndex();
	}

	public OrderIndex getIndex(IndexType indexType) {
		switch (indexType) {
		case Customer:
			return customerIndex;
		case Goods:
			return goodsIndex;
		case GoodsType:
			return goodsTypeIndex;
		case Supplier:
			return supplierIndex;
		default:
			throw new NullPointerException(String.format("There is not exist such cache: %s.", indexType.toString()));
		}
	}
}
