package org.sysu.sdcs.order.analysis.service.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.interfaces.Scheduler;
import org.sysu.sdcs.order.analysis.service.handler.RepositoryUpdateHandler;
import org.sysu.sdcs.order.analysis.utils.common.DateUtil;

@Service
public class RepositoryUpdateScheduler implements Scheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUpdateScheduler.class);
	@Autowired
	private RepositoryUpdateHandler handler;

	@Scheduled(cron = "${repository.refresh.interval}")
	public void start() {
		LOGGER.info("Begin repository update scheduler.");
		handler.handle();
		LOGGER.info("Finish repository update scheduler, timestamp: {}.", DateUtil.completeFormat(new Date()));
	}

}
