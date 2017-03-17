package org.sysu.sdcs.order.analysis.service.factory.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.index.OrderIndex;
import org.sysu.sdcs.order.analysis.service.basic.AbstractFactory;

@Component
public class IndexFactory extends AbstractFactory<OrderIndex, IndexType>{
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
	@Override
	public OrderIndex getInstance(IndexType indexType) {
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

	public void add(IndexType indexType, Long key, Long value) {
		getInstance(indexType).add(key, value);
	}

	public List<Long> get(IndexType indexType, Long key) {
		Set<Long> set = getInstance(indexType).get(key);
		List<Long> result = new ArrayList<>(set);
		return result;
	}
}
