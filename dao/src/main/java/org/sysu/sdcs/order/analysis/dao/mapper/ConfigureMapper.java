package org.sysu.sdcs.order.analysis.dao.mapper;

import java.util.List;

import org.sysu.sdcs.order.analysis.model.database.entity.Configure;

/**
 * Get customer information
 * @author Zhuang Yixin
 */
public interface ConfigureMapper 
{
    public List<Configure> findAll() throws Exception;
}
