package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.po.Goods;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface GoodsMapper 
{
    public Goods findById(long id);
    public List<Goods> findAll();
}
