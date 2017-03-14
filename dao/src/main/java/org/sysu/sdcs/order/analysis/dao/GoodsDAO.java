package org.sysu.sdcs.order.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsMapper;
import org.sysu.sdcs.order.analysis.model.po.Goods;

@Component
public class GoodsDAO {
	@Autowired
	private GoodsMapper goodsMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsDAO.class);
	
	public Goods findById(long id) {
		Goods goods = goodsMapper.findById(id);
		return goods;
	}

	public List<Goods> findAll() {
		List<Goods> allGoods = goodsMapper.findAll();
		if(allGoods == null) {
			allGoods = new ArrayList<Goods>();
			LOGGER.info("Goods cache is empty.");
		}
		return allGoods;
	}
}
