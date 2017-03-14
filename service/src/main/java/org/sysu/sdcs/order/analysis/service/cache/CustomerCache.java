package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.CustomerMapper;
import org.sysu.sdcs.order.analysis.model.bo.CustomerModel;
import org.sysu.sdcs.order.analysis.model.po.Customer;
import org.sysu.sdcs.order.analysis.service.abract.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abract.UpdateAble;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

/**
 * Cache store customer information
 * 
 * @author Zhuang Yixin
 *
 */
@Service
public class CustomerCache extends AbstractCache<CustomerModel> implements UpdateAble {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCache.class);
	@Autowired
	private CustomerMapper customerDAO;

	public void update() {
		LOGGER.info("Begin update customer cache");
		List<Customer> allCustomer = customerDAO.findAll();
		for (Customer customer : allCustomer) {
			addOrUpdate(customer.getId(), POAdapter.convert(customer));
		}
		LOGGER.info("Finish update customer cache, cache size:{}", allCustomer.size());
	}

}