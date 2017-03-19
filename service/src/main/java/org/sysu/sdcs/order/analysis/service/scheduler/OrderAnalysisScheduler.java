package org.sysu.sdcs.order.analysis.service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.service.handler.AnalysisHandler;
import org.sysu.sdcs.order.analysis.service.interfaces.Scheduler;

@Service
public class OrderAnalysisScheduler implements Scheduler {
	@Autowired
	private AnalysisHandler handler;

	@Scheduled(cron = "${order.analysis.interval}")
	@Override
	public void start() {
		handler.handle();
	}

}
