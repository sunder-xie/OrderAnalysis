package org.sysu.sdcs.order.analysis.service.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.service.cache.CustomerCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsTypeCache;
import org.sysu.sdcs.order.analysis.service.cache.OrderCache;
import org.sysu.sdcs.order.analysis.service.cache.SupplierCache;
import org.sysu.sdcs.order.analysis.service.interfaces.Scheduler;

@Service
public class CacheUpdateScheduler implements Scheduler {
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

	@Scheduled(cron = "${cache.refresh.interval}")
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

	public void updateCustomerCache() {
		customerCache.update();
	}

	public void updateGoodsCache() {
		goodsCache.update();
	}

	public void updateGoodsTypeCache() {
		goodsTypeCache.update();
	}

	public void updateSupplierCache() {
		supplierCache.update();
	}

	public void updateOrderCache() {
		orderCache.update();
	}
}
