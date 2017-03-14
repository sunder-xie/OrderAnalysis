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
		LOGGER.info("Begin update customer cache.");
		long beginTime = System.currentTimeMillis();
		customerCache.update();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update customer cache, spend:{}ms.", finishTime - beginTime);
	}

	@Scheduled(cron = "${cache.goods.refresh.interval}")
	public void updateGoodsCache() {
		LOGGER.info("Begin update goods cache.");
		long beginTime = System.currentTimeMillis();
		goodsCache.update();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update goods cache, spend:{}ms.", finishTime - beginTime);
	}

	@Scheduled(cron = "${cache.goods.type.refresh.interval}")
	public void updateGoodsTypeCache() {
		LOGGER.info("Begin update goods type cache.");
		long beginTime = System.currentTimeMillis();
		goodsTypeCache.update();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update goods type cache, spend:{}ms.", finishTime - beginTime);
	}

	@Scheduled(cron = "${cache.supplier.refresh.interval}")
	public void updateSupplierCache() {
		LOGGER.info("Begin update supplier cache.");
		long beginTime = System.currentTimeMillis();
		supplierCache.update();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update supplier cache, spend:{}ms.", finishTime - beginTime);
	}
	
	@Scheduled(cron = "${cache.order.refresh.interval}")
	public void updateOrderCache() {
		LOGGER.info("Begin update order cache.");
		long beginTime = System.currentTimeMillis();
		orderCache.update();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update order cache, spend:{}ms.", finishTime - beginTime);
	}
}
