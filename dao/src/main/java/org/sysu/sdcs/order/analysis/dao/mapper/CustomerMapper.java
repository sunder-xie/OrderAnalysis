package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.po.Customer;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface CustomerMapper 
{
    public Customer findById(long id);
    public List<Customer> findAll();
}
