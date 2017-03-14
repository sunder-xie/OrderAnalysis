package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.po.Supplier;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface SupplierMapper 
{
    public Supplier findById(long id);
    public List<Supplier> findAll();
}