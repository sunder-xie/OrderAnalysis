package org.sysu.sdcs.order.analysis.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web Page index controller Path: webapp/WEB-INF/views
 * 
 * @author Zhuang Yixin
 */
@Controller
@RequestMapping("/")
public class PageController {

	@RequestMapping("/checkHealth")
	public String checkHealth() {
		return "checkHealth";
	}
	@RequestMapping("/emailSender")
	public String emailSender() {
		return "emailSender";
	}
}
