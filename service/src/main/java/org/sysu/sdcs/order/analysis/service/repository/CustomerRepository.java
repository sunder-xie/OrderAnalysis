package org.sysu.sdcs.order.analysis.service.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.CustomerMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Customer;
import org.sysu.sdcs.order.analysis.model.interfaces.Update;
import org.sysu.sdcs.order.analysis.model.local.object.CustomerModel;
import org.sysu.sdcs.order.analysis.model.repository.AbstractRepository;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

/**
 * Cache store customer information
 * 
 * @author Zhuang Yixin
 *
 */
@Service
public class CustomerRepository extends AbstractRepository<CustomerModel> implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepository.class);
	@Autowired
	private CustomerMapper customerDAO;

	public void update() {
		try {
			LOGGER.info("Begin update customer repository.");
			long beginTime = System.currentTimeMillis();
			List<Customer> allCustomer = customerDAO.findAll();
			if (allCustomer == null || allCustomer.isEmpty()) {
				LOGGER.warn("Customer repository is empty.");
				return;
			}
			for (Customer customer : allCustomer) {
				addOrUpdate(customer.getId(), POAdapter.convert(customer));
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update customer repository, size {}, spend {}ms.", allCustomer.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update customer repository fail.", ex);
		}
	}
}