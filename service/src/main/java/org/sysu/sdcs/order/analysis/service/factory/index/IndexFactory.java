package org.sysu.sdcs.order.analysis.service.factory.index;

import java.util.List;

import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.index.OrderIndex;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractFactory;

@Component
public class IndexFactory extends AbstractFactory<OrderIndex, IndexType>{
	private OrderIndex customerIndex;
	private OrderIndex goodsIndex;
	private OrderIndex type2GoodsIndex;
	private OrderIndex supplierIndex;
	private OrderIndex goods2TypeIndex = new OrderIndex();

	public void initialOrderIndex() {
		customerIndex = new OrderIndex();
		goodsIndex = new OrderIndex();
		supplierIndex = new OrderIndex();
	}
	
	public void initialGoodsIndex() {
		type2GoodsIndex = new OrderIndex();
		goods2TypeIndex = new OrderIndex();
	}
	@Override
	public OrderIndex getInstance(IndexType indexType) {
		switch (indexType) {
		case Customer:
			return customerIndex;
		case Goods:
			return goodsIndex;
		case Type2Goods:
			return type2GoodsIndex;
		case Supplier:
			return supplierIndex;
		case Goods2Type:
			return goods2TypeIndex;
		default:
			throw new NullPointerException(String.format("There is not such cache: %s.", indexType.toString()));
		}
	}

	public void add(IndexType indexType, Long key, Long value) {
		getInstance(indexType).add(key, value);
	}

	public List<Long> get(IndexType indexType, Long key) {
		return getInstance(indexType).get(key);
	}
}
