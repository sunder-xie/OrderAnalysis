package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.po.OrderDetail;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface OrderDetailMapper 
{
    public List<OrderDetail> findById(long id) throws Exception;
    public List<OrderDetail> findAll() throws Exception;
}
