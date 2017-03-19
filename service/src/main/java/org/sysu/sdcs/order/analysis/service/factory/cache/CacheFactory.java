package org.sysu.sdcs.order.analysis.service.factory.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.enums.CacheType;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractFactory;
import org.sysu.sdcs.order.analysis.service.cache.CustomerCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsTypeCache;
import org.sysu.sdcs.order.analysis.service.cache.OrderCache;
import org.sysu.sdcs.order.analysis.service.cache.SupplierCache;

/**
 * Model cache
 * 
 * @author Zhuang Yixin
 *
 */
@Service
public class CacheFactory extends AbstractFactory<AbstractCache, CacheType> {
	@Autowired
	private CustomerCache customerCache;
	@Autowired
	private GoodsCache goodsCache;
	@Autowired
	private GoodsTypeCache goodsTypeCache;
	@Autowired
	private SupplierCache supplierCache;
	@Autowired
	private OrderCache orderCache;

	@Override
	@SuppressWarnings("rawtypes")
	public AbstractCache getInstance(CacheType cacheType) throws Exception {
		switch (cacheType) {
		case Customer:
			return customerCache;
		case Goods:
			return goodsCache;
		case GoodsType:
			return goodsTypeCache;
		case Supplier:
			return supplierCache;
		case Order:
			return orderCache;
		default:
			throw new NullPointerException(String.format("There is not such cache: %s.", cacheType.toString()));
		}
	}
}
