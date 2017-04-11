package org.sysu.sdcs.order.analysis.model.local.object;

import java.util.Date;
import java.util.List;

public class OrderModel {
	private String customer;
	private Date time;
	private List<OrderDetailModel> details;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<OrderDetailModel> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetailModel> details) {
		this.details = details;
	}
}
