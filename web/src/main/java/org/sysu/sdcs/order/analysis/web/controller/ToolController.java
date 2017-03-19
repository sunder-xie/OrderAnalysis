package org.sysu.sdcs.order.analysis.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.model.enums.CacheType;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.index.OrderIndex;
import org.sysu.sdcs.order.analysis.service.cache.ConfigureHelper;
import org.sysu.sdcs.order.analysis.service.cache.CustomerCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsCache;
import org.sysu.sdcs.order.analysis.service.cache.GoodsTypeCache;
import org.sysu.sdcs.order.analysis.service.cache.OrderCache;
import org.sysu.sdcs.order.analysis.service.cache.SupplierCache;
import org.sysu.sdcs.order.analysis.service.factory.cache.CacheFactory;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexFactory;
import org.sysu.sdcs.order.analysis.service.scheduler.CacheUpdateScheduler;
import org.sysu.sdcs.order.analysis.service.scheduler.ConfigureUpdateScheduler;
import org.sysu.sdcs.order.analysis.service.scheduler.OrderAnalysisScheduler;
import org.sysu.sdcs.order.analysis.utils.common.JSONUtil;

@Controller
@RequestMapping("/tools")
public class ToolController {
	@Autowired
	private CacheFactory cacheFactory;
	@Autowired
	private CacheUpdateScheduler cacheupdateScheduler;
	@Autowired
	private OrderAnalysisScheduler orderAnalysisScheduler;
	@Autowired
	private ConfigureUpdateScheduler configureUpdateScheduler;
	@Autowired
	private IndexFactory indexFactory;
	@Autowired
	private ConfigureHelper configureHelper;
	private static final String PRODUCES = "text/json;charset=UTF-8";

	@ResponseBody
	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllCustomer() throws Exception {
		CustomerCache cache = (CustomerCache) cacheFactory.getInstance(CacheType.Customer);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/goods", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllGoods() throws Exception {
		GoodsCache cache = (GoodsCache) cacheFactory.getInstance(CacheType.Goods);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/goodsType", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllGoodsType() throws Exception {
		GoodsTypeCache cache = (GoodsTypeCache) cacheFactory.getInstance(CacheType.GoodsType);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/supplier", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllSupplier() throws Exception {
		SupplierCache cache = (SupplierCache) cacheFactory.getInstance(CacheType.Supplier);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/order", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllOrder() throws Exception {
		OrderCache cache = (OrderCache) cacheFactory.getInstance(CacheType.Order);
		return JSONUtil.serialize(cache.getCache());
	}

	@ResponseBody
	@RequestMapping(value = "/index", method = RequestMethod.GET, produces = PRODUCES)
	public String getAllIndex() throws Exception {
		Map<IndexType, OrderIndex> allIndex = new HashMap<>();
		for (IndexType type : IndexType.values()) {
			allIndex.put(type, indexFactory.getInstance(type));
		}
		return JSONUtil.serialize(allIndex);
	}

	@RequestMapping("/updateCache")
	public void updateCache() throws Exception {
		cacheupdateScheduler.start();
	}

	@ResponseBody
	@RequestMapping(value = "/updateConfig", method = RequestMethod.GET, produces = PRODUCES)
	public String updateConfig() throws Exception {
		configureUpdateScheduler.start();
		return JSONUtil.serialize(configureHelper.getAllConfigure());
	}

	@RequestMapping("/analysis")
	public void analysis() throws Exception {
		orderAnalysisScheduler.start();
	}
}
