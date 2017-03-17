package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.database.entity.GoodsType;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface GoodsTypeMapper 
{
    public GoodsType findById(long id) throws Exception;
    public List<GoodsType> findAll() throws Exception;
}
