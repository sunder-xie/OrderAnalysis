package org.sysu.sdcs.order.analysis.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.model.index.OrderIndex;
import org.sysu.sdcs.order.analysis.service.cache.CustomerCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsTypeCache;
import org.sysu.sdcs.order.analysis.service.cache.OrderCache;
import org.sysu.sdcs.order.analysis.service.cache.SupplierCache;
import org.sysu.sdcs.order.analysis.service.cache.factory.CacheFactory;
import org.sysu.sdcs.order.analysis.service.cache.factory.CacheType;
import org.sysu.sdcs.order.analysis.service.index.factory.IndexFactory;
import org.sysu.sdcs.order.analysis.service.index.factory.IndexType;
import org.sysu.sdcs.order.analysis.service.scheduler.CacheUpdateScheduler;
import org.sysu.sdcs.order.analysis.utils.JSONUtil;

@Controller
@RequestMapping("/tools")
public class ToolController {
	@Autowired
	private CacheFactory cacheFactory;
	@Autowired
	private CacheUpdateScheduler cacheupdateScheduler;
	@Autowired
	private IndexFactory indexFactory;

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

	@ResponseBody
	@RequestMapping(value = "/allIndex", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String getAllIndex() throws Exception {
		Map<IndexType,OrderIndex> allIndex = new HashMap<>();
		for(IndexType type : IndexType.values()){
			allIndex.put(type,indexFactory.getIndex(type));
		}
		return JSONUtil.serialize(allIndex);
	}

	@RequestMapping("/updateCache")
	public void updateCache() throws Exception {
		cacheupdateScheduler.start();
	}
}
