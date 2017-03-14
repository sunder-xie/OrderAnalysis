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
		try {
			LOGGER.info("Begin update goods cache.");
			long beginTime = System.currentTimeMillis();
			List<Goods> allGoods = goodsDAO.findAll();
			if(allGoods == null || allGoods.isEmpty()) {
				LOGGER.warn("Goods cache is empty.");
				return;
			}
			for (Goods goods : allGoods) {
				addOrUpdate(goods.getId(), POAdapter.convert(goods));
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update goods cache, size {}, spend {}ms.", allGoods.size(), endTime - beginTime);

		} catch (Exception ex) {
			LOGGER.error("Update goods cache fail.", ex);
		}
	}

}
