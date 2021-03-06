package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.database.entity.Order;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface OrderMapper 
{
    public Order findById(long id) throws Exception;
    public List<Order> findAll() throws Exception;
    public int add(Order order) throws Exception;
}
