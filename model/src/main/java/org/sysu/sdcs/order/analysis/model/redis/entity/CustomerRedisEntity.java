package org.sysu.sdcs.order.analysis.model.redis.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.model.local.object.CustomerModel;

public class CustomerRedisEntity implements Serializable {

	private static final long serialVersionUID = 9048358474082641243L;
	private CustomerModel customer;
	private Counter<String> dateGroup;
	private List<Map<String, Counter<Integer>>> typeGroup;
	private Counter<String> supplierGroup;

	public CustomerModel getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}

	public Counter<String> getDateGroup() {
		return dateGroup;
	}

	public void setDateGroup(Counter<String> dateGroup) {
		this.dateGroup = dateGroup;
	}

	public List<Map<String, Counter<Integer>>> getTypeGroup() {
		return typeGroup;
	}

	public void setTypeGroup(List<Map<String, Counter<Integer>>> typeGroup) {
		this.typeGroup = typeGroup;
	}

	public Counter<String> getSupplierGroup() {
		return supplierGroup;
	}

	public void setSupplierGroup(Counter<String> supplierGroup) {
		this.supplierGroup = supplierGroup;
	}

}
