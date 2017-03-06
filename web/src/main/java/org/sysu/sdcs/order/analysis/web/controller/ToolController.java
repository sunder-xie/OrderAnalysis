package org.sysu.sdcs.order.analysis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.cache.CacheFactory;
import org.sysu.sdcs.order.analysis.cache.CacheType;
import org.sysu.sdcs.order.analysis.cache.CustomerCache;

@Controller
@RequestMapping("/tools")
public class ToolController {
	@Autowired
	private CacheFactory cacheFactory;

	@ResponseBody
	@RequestMapping("/customer")
	public String getSize() throws Exception {
		CustomerCache cache = (CustomerCache) cacheFactory.getCache(CacheType.Customer);
		return Integer.valueOf(cache.getSize()).toString();
	}
}
