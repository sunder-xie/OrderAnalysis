package org.sysu.sdcs.order.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.mapper.SupplierMapper;
import org.sysu.sdcs.order.analysis.model.po.Supplier;

@Component
public class SupplierDAO {
	@Autowired
	private SupplierMapper supplierMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierDAO.class);
	
	public Supplier findById(long id) {
		Supplier supplier = supplierMapper.findById(id);
		return supplier;
	}

	public List<Supplier> findAll() {
		List<Supplier> allSupplier = supplierMapper.findAll();
		if(allSupplier == null) {
			allSupplier = new ArrayList<Supplier>();
			LOGGER.info("Supplier cache is empty.");
		}
		return allSupplier;
	}
}
