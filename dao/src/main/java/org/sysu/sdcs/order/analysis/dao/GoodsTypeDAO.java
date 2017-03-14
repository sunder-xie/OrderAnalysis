package org.sysu.sdcs.order.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsTypeMapper;
import org.sysu.sdcs.order.analysis.model.po.GoodsType;

@Component
public class GoodsTypeDAO {
	@Autowired
	private GoodsTypeMapper goodsTypeMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsTypeDAO.class);
	
	public GoodsType findById(long id) {
		GoodsType goodsType = goodsTypeMapper.findById(id);
		return goodsType;
	}

	public List<GoodsType> findAll() {
		List<GoodsType> allGoodsType = goodsTypeMapper.findAll();
		if(allGoodsType == null) {
			allGoodsType = new ArrayList<GoodsType>();
			LOGGER.info("Goods type cache is empty.");
		}
		return allGoodsType;
	}
}
