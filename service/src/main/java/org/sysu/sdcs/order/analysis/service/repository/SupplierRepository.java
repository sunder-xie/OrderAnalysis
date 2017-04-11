package org.sysu.sdcs.order.analysis.service.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sysu.sdcs.order.analysis.dao.mapper.SupplierMapper;
import org.sysu.sdcs.order.analysis.model.database.entity.Supplier;
import org.sysu.sdcs.order.analysis.model.interfaces.Update;
import org.sysu.sdcs.order.analysis.model.local.object.SupplierModel;
import org.sysu.sdcs.order.analysis.model.repository.AbstractRepository;
import org.sysu.sdcs.order.analysis.utils.adapter.POAdapter;

@Service
public class SupplierRepository extends AbstractRepository<SupplierModel> implements Update {
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierRepository.class);
	@Autowired
	private SupplierMapper supplierDAO;

	public void update() {
		try {
			LOGGER.info("Begin update supplier repository.");
			long beginTime = System.currentTimeMillis();
			List<Supplier> allSppulier = supplierDAO.findAll();
			if (allSppulier == null || allSppulier.isEmpty()) {
				LOGGER.warn("Sppulier repository is empty.");
				return;
			}
			for (Supplier supplier : allSppulier) {
				addOrUpdate(supplier.getId(), POAdapter.convert(supplier));
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Finish update supplier repository, size {}, spend {}ms.", allSppulier.size(), endTime - beginTime);
		} catch (Exception ex) {
			LOGGER.error("Update order repository fail.", ex);
		}
	}

}
