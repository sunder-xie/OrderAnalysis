package org.sysu.sdcs.order.analysis.service.factory.index;

import java.util.List;

import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.index.OrderIndex;
import org.sysu.sdcs.order.analysis.model.local.object.OrderDetailModel;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractStore;

@Component
public class IndexStore extends AbstractStore<OrderIndex, IndexType> {
	// order index
	private OrderIndex customerIndex;
	private OrderIndex goodsIndex;
	private OrderIndex supplierIndex;
	private OrderIndex goodsTypeIndex;
	
	// goods and type transform index
	private OrderIndex type2GoodsIndex;
	private OrderIndex goods2TypeIndex;

	public void initialOrderIndex() {
		customerIndex = new OrderIndex();
		goodsIndex = new OrderIndex();
		supplierIndex = new OrderIndex();
		goodsTypeIndex = new OrderIndex();
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
		case GoodsType:
			return goodsTypeIndex;
		case Supplier:
			return supplierIndex;
		case Type2Goods:
			return type2GoodsIndex;
		case Goods2Type:
			return goods2TypeIndex;
		default:
			throw new NullPointerException(String.format("There is not such cache: %s.", indexType.toString()));
		}
	}

	public void add(IndexType indexType, String key, Long value) {
		getInstance(indexType).add(key, value);
	}

	public List<String> get(IndexType indexType, String key) {
		return getInstance(indexType).get(key);
	}
	
	public String getType(OrderDetailModel detail) {
		return get(IndexType.Goods2Type, detail.getGoods()).get(0);
	}
}
