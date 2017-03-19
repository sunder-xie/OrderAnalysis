package org.sysu.sdcs.order.analysis.service.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.service.cache.ConfigureHelper;
import org.sysu.sdcs.order.analysis.service.interfaces.Scheduler;

@Service
public class ConfigureUpdateScheduler implements Scheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigureUpdateScheduler.class);
	@Autowired
	private ConfigureHelper configureHelper;

	@Scheduled(cron = "${cache.refresh.interval}")
	public void start() {
		LOGGER.info("Begin update cache.");
		long beginTime = System.currentTimeMillis();
		configureHelper.update();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update cache, spend:{}ms.", finishTime - beginTime);
	}
}
