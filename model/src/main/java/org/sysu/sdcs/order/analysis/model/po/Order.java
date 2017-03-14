package org.sysu.sdcs.order.analysis.model.po;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
	private long id;
	private long customer;
	private Date time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
