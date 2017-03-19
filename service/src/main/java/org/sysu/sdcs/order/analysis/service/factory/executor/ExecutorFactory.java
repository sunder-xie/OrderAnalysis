package org.sysu.sdcs.order.analysis.service.factory.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.enums.ExecutorType;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractExecutor;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractFactory;
import org.sysu.sdcs.order.analysis.service.executor.CustomerAnalysisExecutor;

@Service
public class ExecutorFactory extends AbstractFactory<AbstractExecutor, ExecutorType> {
	@Autowired
	private CustomerAnalysisExecutor customerExecutor;

	@Override
	public AbstractExecutor getInstance(ExecutorType type) throws Exception {
		switch (type) {
		case Customer:
			return customerExecutor;
		default:
			throw new NullPointerException(String.format("There is not such Executor Type:%s", type.toString()));
		}
	}

}
