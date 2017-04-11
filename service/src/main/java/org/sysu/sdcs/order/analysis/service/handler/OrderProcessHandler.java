package org.sysu.sdcs.order.analysis.service.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.enums.ConfigureOption;
import org.sysu.sdcs.order.analysis.model.enums.ExecutorType;
import org.sysu.sdcs.order.analysis.model.interfaces.Executor;
import org.sysu.sdcs.order.analysis.model.interfaces.Handler;
import org.sysu.sdcs.order.analysis.service.factory.executor.ExecutorStore;
import org.sysu.sdcs.order.analysis.service.repository.ConfigureHelper;
import org.sysu.sdcs.order.analysis.utils.adapter.ConfigureOptionAdapter;

@Service
public class OrderProcessHandler implements Handler {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessHandler.class);
	@Autowired
	private ExecutorStore executorFactory;
	@Autowired
	private ConfigureHelper configureHelper;
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

	@Override
	public void handle() {
		for (ExecutorType type : ExecutorType.values()) {
			if (isExecute(type)) {
				addTask(type);
			}
		}
	}

	private boolean isExecute(ExecutorType type) {
		ConfigureOption option = ConfigureOptionAdapter.convert(type);
		return configureHelper.getConfig(option.getName(), option.getContent());
	}

	private void addTask(ExecutorType type) {
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					LOGGER.info("Begin run executor: {}.", type.toString());
					Executor executor = executorFactory.getInstance(type);
					executor.execute();
					LOGGER.info("Finish run executor: {}.", type.toString());
				} catch (Exception ex) {
					LOGGER.error(String.format("Execute fail type: %s.", type.toString()), ex);
				}
			}
		});
	}
}
