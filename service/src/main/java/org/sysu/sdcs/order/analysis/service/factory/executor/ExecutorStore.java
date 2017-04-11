package org.sysu.sdcs.order.analysis.service.factory.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.enums.ExecutorType;
import org.sysu.sdcs.order.analysis.model.interfaces.Executor;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractStore;
import org.sysu.sdcs.order.analysis.service.executor.CustomerAnalysisExecutor;
import org.sysu.sdcs.order.analysis.service.executor.GoodsTypeAnalysisExecutor;

@Service
public class ExecutorStore extends AbstractStore<Executor, ExecutorType> {
	@Autowired
	private CustomerAnalysisExecutor customerExecutor;
	@Autowired
	private GoodsTypeAnalysisExecutor goodsTypeExecutor;

	@Override
	public Executor getInstance(ExecutorType type) throws Exception {
		switch (type) {
		case Customer:
			return customerExecutor;
		case GoodsType:
			return goodsTypeExecutor;
		default:
			throw new NullPointerException(String.format("There is not such Executor Type:%s", type.toString()));
		}
	}

}
