package org.sysu.sdcs.order.analysis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.service.cache.CustomerCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsTypeCache;
import org.sysu.sdcs.order.analysis.service.cache.OrderCache;
import org.sysu.sdcs.order.analysis.service.cache.SupplierCache;
import org.sysu.sdcs.order.analysis.service.cache.factory.CacheFactory;
import org.sysu.sdcs.order.analysis.service.cache.factory.CacheType;
import org.sysu.sdcs.order.analysis.service.scheduler.CacheUpdateScheduler;
import org.sysu.sdcs.order.analysis.utils.JSONUtil;

@Controller
@RequestMapping("/tools")
public class ToolController {
	@Autowired
	private CacheFactory cacheFactory;
	@Autowired
	private CacheUpdateScheduler cacheupdateScheduler;

	@ResponseBody
	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String getAllCustomer() throws Exception {
		CustomerCache cache = (CustomerCache) cacheFactory.getCache(CacheType.Customer);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/goods", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String getAllGoods() throws Exception {
		GoodsCache cache = (GoodsCache) cacheFactory.getCache(CacheType.Goods);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/goodsType", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String getAllGoodsType() throws Exception {
		GoodsTypeCache cache = (GoodsTypeCache) cacheFactory.getCache(CacheType.GoodsType);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/supplier", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String getAllSupplier() throws Exception {
		SupplierCache cache = (SupplierCache) cacheFactory.getCache(CacheType.Supplier);
		return JSONUtil.serialize(cache.getCache());
	}
	
	@ResponseBody
	@RequestMapping(value = "/order", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String getAllOrder() throws Exception {
		OrderCache cache = (OrderCache) cacheFactory.getCache(CacheType.Order);
		return JSONUtil.serialize(cache.getCache());
	}

	@RequestMapping("/updateCache")
	public void updateCache() throws Exception {
		cacheupdateScheduler.start();
	}
}
