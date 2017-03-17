package org.sysu.sdcs.order.analysis.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.SupplierMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Supplier;
import org.sysu.sdcs.order.analysis.model.local.object.SupplierModel;
import org.sysu.sdcs.order.analysis.service.basic.AbstractCache;
import org.sysu.sdcs.order.analysis.service.interfaces.Update;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class SupplierCache extends AbstractCache<SupplierModel> implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierCache.class);
	@Autowired
	private SupplierMapper supplierDAO;

	public void update() {
		try {
			LOGGER.info("Begin update supplier cache.");
			long beginTime = System.currentTimeMillis();
			List<Supplier> allSppulier = supplierDAO.findAll();
			if(allSppulier == null || allSppulier.isEmpty()) {
				LOGGER.warn("Sppulier cache is empty.");
				return;
			}
			for (Supplier supplier : allSppulier) {
				addOrUpdate(supplier.getId(), POAdapter.convert(supplier));
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update supplier cache, size {}, spend {}ms.", allSppulier.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update order cache fail.", ex);
		}
	}

}
