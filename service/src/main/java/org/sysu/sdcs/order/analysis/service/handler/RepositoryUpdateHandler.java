package org.sysu.sdcs.order.analysis.service.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.model.interfaces.Handler;
import org.sysu.sdcs.order.analysis.service.repository.CustomerRepository;
import org.sysu.sdcs.order.analysis.service.repository.GoodsRepository;
import org.sysu.sdcs.order.analysis.service.repository.GoodsTypeRepository;
import org.sysu.sdcs.order.analysis.service.repository.OrderRepository;
import org.sysu.sdcs.order.analysis.service.repository.SupplierRepository;

@Service
public class RepositoryUpdateHandler implements Handler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUpdateHandler.class);
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private GoodsRepository goodsRepository;
	@Autowired
	private GoodsTypeRepository goodsTypeRepository;
	@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void handle() {
		LOGGER.info("Begin update all repository.");
		long beginTime = System.currentTimeMillis();
		customerRepository.update();
		goodsTypeRepository.update();
		supplierRepository.update();
		goodsRepository.update();
		orderRepository.update();
		long finishTime = System.currentTimeMillis();
		LOGGER.info("Finish update all repository, spend {}ms.", finishTime - beginTime);

	}

}
