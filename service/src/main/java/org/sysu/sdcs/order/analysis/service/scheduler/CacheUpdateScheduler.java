package org.sysu.sdcs.order.analysis.service.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.service.abract.AbstractScheduler;
import org.sysu.sdcs.order.analysis.service.cache.CustomerCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsTypeCache;
import org.sysu.sdcs.order.analysis.service.cache.OrderCache;
import org.sysu.sdcs.order.analysis.service.cache.SupplierCache;

@Service
public class CacheUpdateScheduler implements AbstractScheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheUpdateScheduler.class);
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

	public void start() {
		LOGGER.info("Begin update cache.");
		long beginTime = System.currentTimeMillis();
		updateCustomerCache();
		updateGoodsCache();
		updateGoodsTypeCache();
		updateSupplierCache();
		updateOrderCache();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update cache, spend:{}ms.", finishTime - beginTime);
	}

	@Scheduled(cron = "${cache.customer.refresh.interval}")
	public void updateCustomerCache() {
		customerCache.update();
	}

	@Scheduled(cron = "${cache.goods.refresh.interval}")
	public void updateGoodsCache() {
		goodsCache.update();
	}

	@Scheduled(cron = "${cache.goods.type.refresh.interval}")
	public void updateGoodsTypeCache() {
		goodsTypeCache.update();
	}

	@Scheduled(cron = "${cache.supplier.refresh.interval}")
	public void updateSupplierCache() {
		supplierCache.update();
	}

	@Scheduled(cron = "${cache.order.refresh.interval}")
	public void updateOrderCache() {
		orderCache.update();
	}
}
