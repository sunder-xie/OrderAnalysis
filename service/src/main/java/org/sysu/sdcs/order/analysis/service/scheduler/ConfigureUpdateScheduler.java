package org.sysu.sdcs.order.analysis.service.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.interfaces.Scheduler;
import org.sysu.sdcs.order.analysis.service.repository.ConfigureHelper;
import org.sysu.sdcs.order.analysis.utils.common.DateUtil;

@Service
public class ConfigureUpdateScheduler implements Scheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigureUpdateScheduler.class);
	@Autowired
	private ConfigureHelper configureHelper;

	@Scheduled(cron = "${configure.refresh.interval}")
	public void start() {
		LOGGER.info("Begin update configure scheduler.");
		configureHelper.update();
		LOGGER.info("Finish update Configure scheduler, timestamp: {}.", DateUtil.completeFormat(new Date()));
	}
}
