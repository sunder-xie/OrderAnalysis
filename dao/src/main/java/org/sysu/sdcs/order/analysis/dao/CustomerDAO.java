package org.sysu.sdcs.order.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.mapper.CustomerMapper;
import org.sysu.sdcs.order.analysis.model.po.Customer;

@Component
public class CustomerDAO {
	@Autowired
	private CustomerMapper customerMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);
	
	public Customer findById(long id) {
		Customer customer = customerMapper.findById(id);
		return customer;
	}

	public List<Customer> findAll() {
		List<Customer> allCustomer = customerMapper.findAll();
		if(allCustomer == null) {
			allCustomer = new ArrayList<Customer>();
			LOGGER.info("Customer cache is empty.");
		}
		return allCustomer;
	}
}
