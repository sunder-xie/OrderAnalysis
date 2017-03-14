package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.po.Order;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface OrderMapper 
{
    public Order findById(long id);
    public List<Order> findAll();
}
