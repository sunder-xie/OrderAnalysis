package org.sysu.sdcs.order.analysis.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sysu.sdcs.order.analysis.dao.redis.RedisDAO;
import org.sysu.sdcs.order.analysis.model.mvc.request.EmailRequest;
import org.sysu.sdcs.order.analysis.utils.common.EmailUtil;

@Controller
@RequestMapping("/utils")
public class UtilsController {
	@Autowired
	private EmailUtil emialUtil;
	@Autowired
	private RedisDAO redisDAO;

	@ResponseBody
	@RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	public void sendEmail(EmailRequest request) {
		emialUtil.send(request.getRecipients(), request.getTitle(), request.getContent());
	}

	@ResponseBody
	@RequestMapping(value = "/redisSet/{key}/{value}", method = RequestMethod.GET)
	public boolean sendEmail(@PathVariable("key") String key, @PathVariable("value") String value) {
		return redisDAO.set(key, value.getBytes());
	}
}
