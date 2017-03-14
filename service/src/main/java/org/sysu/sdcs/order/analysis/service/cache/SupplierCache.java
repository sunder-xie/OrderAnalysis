package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.SupplierMapper;
import org.sysu.sdcs.order.analysis.model.bo.SupplierModel;
import org.sysu.sdcs.order.analysis.model.po.Supplier;
import org.sysu.sdcs.order.analysis.service.abract.AbstractCache;
import org.sysu.sdcs.order.analysis.service.abract.UpdateAble;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class SupplierCache extends AbstractCache<SupplierModel> implements UpdateAble {
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierCache.class);
	@Autowired
	private SupplierMapper supplierDAO;

	public void update() {
		LOGGER.info("Begin update supplier cache");
		List<Supplier> allSppulier = supplierDAO.findAll();
		for (Supplier supplier : allSppulier) {
			addOrUpdate(supplier.getId(), POAdapter.convert(supplier));
		}
		LOGGER.info("Finish update supplier cache, cache size:{}", allSppulier.size());
	}

}
