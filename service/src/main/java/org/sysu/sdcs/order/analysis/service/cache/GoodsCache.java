package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsMapper;
import org.sysu.sdcs.order.analysis.model.bo.GoodsModel;
import org.sysu.sdcs.order.analysis.model.po.Goods;
import org.sysu.sdcs.order.analysis.service.abract.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abract.UpdateAble;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class GoodsCache extends AbstractCache<GoodsModel> implements UpdateAble {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCache.class);
	@Autowired
	private GoodsMapper goodsDAO;

	public void update() {
		LOGGER.info("Begin update goods cache");
		List<Goods> allGoods = goodsDAO.findAll();
		for (Goods goods : allGoods) {
			addOrUpdate(goods.getId(), POAdapter.convert(goods));
		}
		LOGGER.info("Finish update goods cache, cache size:{}", allGoods.size());
	}

}
