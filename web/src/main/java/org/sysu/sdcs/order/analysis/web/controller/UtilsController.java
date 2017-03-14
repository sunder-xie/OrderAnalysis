package org.sysu.sdcs.order.analysis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.model.vo.EmailRequest;
import org.sysu.sdcs.order.analysis.utils.EmailUtil;

@Controller
@RequestMapping("/utils")
public class UtilsController {
	@Autowired
	private EmailUtil emialUtil;

	@ResponseBody
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public void sendEmail(EmailRequest request) {
		emialUtil.send(request.getRecipients(), request.getTitle(), request.getContent());
	}
}
