package org.sysu.sdcs.order.analysis.service.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.interfaces.Scheduler;
import org.sysu.sdcs.order.analysis.service.handler.ClusterProcessHandler;
import org.sysu.sdcs.order.analysis.utils.common.DateUtil;

@Service
public class ClusterProcessorScheduler implements Scheduler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClusterProcessorScheduler.class);
	@Autowired
	private ClusterProcessHandler handler;

	@Scheduled(cron = "${cluster.process.interval}")
	@Override
	public void start() {
		LOGGER.info("Begin cluster process schedule.");
		handler.handle();
		LOGGER.info("Finish cluster process schedule, timestap: {}.", DateUtil.completeFormat(new Date()));
	}

}
