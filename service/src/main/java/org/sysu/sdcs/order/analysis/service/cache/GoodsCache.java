package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Goods;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsModel;
import org.sysu.sdcs.order.analysis.service.abstracts.AbstractCache;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexFactory;
import org.sysu.sdcs.order.analysis.service.interfaces.Update;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class GoodsCache extends AbstractCache<GoodsModel> implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCache.class);
	@Autowired
	private GoodsMapper goodsDAO;
	@Autowired
	private IndexFactory indexFactory;

	public void update() {
		try {
			indexFactory.initialGoodsIndex();
			LOGGER.info("Begin update goods cache.");
			long beginTime = System.currentTimeMillis();
			List<Goods> allGoods = goodsDAO.findAll();
			if (allGoods == null || allGoods.isEmpty()) {
				LOGGER.warn("Goods cache is empty.");
				return;
			}
			for (Goods goods : allGoods) {
				addOrUpdate(goods.getId(), POAdapter.convert(goods));
				loadIndex(goods);
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update goods cache, size {}, spend {}ms.", allGoods.size(), endTime - beginTime);

		} catch (Exception ex) {
			LOGGER.error("Update goods cache fail.", ex);
		}
	}
	
	private void loadIndex(Goods goods) {
		indexFactory.add(IndexType.Goods2Type, goods.getId(), goods.getType());
		indexFactory.add(IndexType.Type2Goods, goods.getType(), goods.getId());
	}

}
