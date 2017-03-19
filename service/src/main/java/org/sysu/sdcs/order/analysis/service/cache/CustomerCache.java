package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.CustomerMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Customer;
import org.sysu.sdcs.order.analysis.model.local.object.CustomerModel;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractCache;
import org.sysu.sdcs.order.analysis.service.interfaces.Update;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

/**
 * Cache store customer information
 * 
 * @author Zhuang Yixin
 *
 */
@Service
public class CustomerCache extends AbstractCache<CustomerModel> implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCache.class);
	@Autowired
	private CustomerMapper customerDAO;

	public void update() {
		try {
			LOGGER.info("Begin update customer cache.");
			long beginTime = System.currentTimeMillis();
			List<Customer> allCustomer = customerDAO.findAll();
			if(allCustomer == null || allCustomer.isEmpty()) {
				LOGGER.warn("Customer cache is empty.");
				return;
			}
			for (Customer customer : allCustomer) {
				addOrUpdate(customer.getId(), POAdapter.convert(customer));
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update customer cache, size {}, spend {}ms.", allCustomer.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update customer cache fail.", ex);
		}
	}
}