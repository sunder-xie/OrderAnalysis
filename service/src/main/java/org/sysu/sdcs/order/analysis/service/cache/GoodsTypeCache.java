package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsTypeMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.GoodsType;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.service.abract.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abract.UpdateAble;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class GoodsTypeCache extends AbstractCache<GoodsTypeModel> implements UpdateAble {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsTypeCache.class);
	@Autowired
	private GoodsTypeMapper goodsTypeDAO;

	public void update() {
		try {
			LOGGER.info("Begin update goods type cache.");
			long beginTime = System.currentTimeMillis();
			List<GoodsType> allGoodsType = goodsTypeDAO.findAll();
			if(allGoodsType == null || allGoodsType.isEmpty()) {
				LOGGER.warn("Goods type cache is empty.");
				return;
			}
			for (GoodsType goodsType : allGoodsType) {
				addOrUpdate(goodsType.getId(), POAdapter.convert(goodsType));
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update goods type cache, size {}, spend {}ms.", allGoodsType.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update goods cache fail.", ex);
		}
	}

}
