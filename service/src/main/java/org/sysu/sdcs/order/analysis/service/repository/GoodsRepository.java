package org.sysu.sdcs.order.analysis.service.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Goods;
import org.sysu.sdcs.order.analysis.model.enums.IndexType;
import org.sysu.sdcs.order.analysis.model.interfaces.Update;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsModel;
import org.sysu.sdcs.order.analysis.model.repository.AbstractRepository;
import org.sysu.sdcs.order.analysis.service.factory.index.IndexStore;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class GoodsRepository extends AbstractRepository<GoodsModel> implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRepository.class);
	@Autowired
	private GoodsMapper goodsDAO;
	@Autowired
	private IndexStore indexStore;

	public void update() {
		try {
			indexStore.initialGoodsIndex();
			LOGGER.info("Begin update goods repository.");
			long beginTime = System.currentTimeMillis();
			List<Goods> allGoods = goodsDAO.findAll();
			if (allGoods == null || allGoods.isEmpty()) {
				LOGGER.warn("Goods repository is empty.");
				return;
			}
			for (Goods goods : allGoods) {
				addOrUpdate(goods.getId(), POAdapter.convert(goods));
				loadIndex(goods);
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update goods repository, size {}, spend {}ms.", allGoods.size(), endTime - beginTime);

		} catch (Exception ex) {
			LOGGER.error("Update goods repository fail.", ex);
		}
	}

	private void loadIndex(Goods goods) {
		indexStore.add(IndexType.Goods2Type, String.valueOf(goods.getId()), goods.getType());
		indexStore.add(IndexType.Type2Goods, String.valueOf(goods.getType()), goods.getId());
	}

}
