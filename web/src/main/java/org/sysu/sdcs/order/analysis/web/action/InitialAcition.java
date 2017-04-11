package org.sysu.sdcs.order.analysis.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.service.scheduler.RepositoryUpdateScheduler;
import org.sysu.sdcs.order.analysis.service.scheduler.ConfigureUpdateScheduler;

@Service
public class InitialAcition implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private RepositoryUpdateScheduler cacheUpdateScheduler;
	@Autowired
	private ConfigureUpdateScheduler configureUpdateScheduler;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			doInitial();
		}
	}

	private void doInitial() {
		//cacheUpdateScheduler.start();
		configureUpdateScheduler.start();
	}

}
