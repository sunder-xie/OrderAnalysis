package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.GoodsTypeMapper;
import org.sysu.sdcs.order.analysis.model.bo.GoodsTypeModel;
import org.sysu.sdcs.order.analysis.model.po.GoodsType;
import org.sysu.sdcs.order.analysis.service.abract.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abract.UpdateAble;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class GoodsTypeCache extends AbstractCache<GoodsTypeModel> implements UpdateAble {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsTypeCache.class);
	@Autowired
	private GoodsTypeMapper goodsTypeDAO;

	public void update() {
		LOGGER.info("Begin update goods type cache");
		List<GoodsType> allGoodsType = goodsTypeDAO.findAll();
		for (GoodsType goodsType : allGoodsType) {
			addOrUpdate(goodsType.getId(), POAdapter.convert(goodsType));
		}
		LOGGER.info("Finish update goods type cache, cache size:{}", allGoodsType.size());
	}

}
