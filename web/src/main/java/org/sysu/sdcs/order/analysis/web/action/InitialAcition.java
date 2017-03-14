package org.sysu.sdcs.order.analysis.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.service.scheduler.CacheUpdateScheduler;

@Service
public class InitialAcition implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private CacheUpdateScheduler cacheUpdateScheduler;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		cacheUpdateScheduler.start();
	}

}
