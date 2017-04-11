package org.sysu.sdcs.order.analysis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.service.handler.ClusterProcessHandler;
import org.sysu.sdcs.order.analysis.service.handler.OrderProcessHandler;
import org.sysu.sdcs.order.analysis.service.handler.RepositoryUpdateHandler;
import org.sysu.sdcs.order.analysis.service.repository.ConfigureHelper;

@Controller
@RequestMapping("/handler")
public class HandlerController {
	@Autowired
	private ClusterProcessHandler clusterHandler;
	@Autowired
	private OrderProcessHandler orderHandler;
	@Autowired
	private RepositoryUpdateHandler repositoryHandler;
	@Autowired
	private ConfigureHelper configureHelper;
	private static final String PRODUCES = "text/json;charset=UTF-8";

	@ResponseBody
	@RequestMapping(value = "/cluster", method = RequestMethod.POST, produces = PRODUCES)
	public void cluster() throws Exception {
		clusterHandler.handle();
	}

	@ResponseBody
	@RequestMapping(value = "/order", method = RequestMethod.POST, produces = PRODUCES)
	public void order() throws Exception {
		orderHandler.handle();
	}

	@ResponseBody
	@RequestMapping(value = "/repository", method = RequestMethod.POST, produces = PRODUCES)
	public void repository() throws Exception {
		repositoryHandler.handle();
	}

	@ResponseBody
	@RequestMapping(value = "/configure", method = RequestMethod.POST, produces = PRODUCES)
	public void configure() throws Exception {
		configureHelper.update();
	}
}
