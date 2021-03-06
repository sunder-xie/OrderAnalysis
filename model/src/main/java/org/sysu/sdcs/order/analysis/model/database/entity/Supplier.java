package org.sysu.sdcs.order.analysis.model.database.entity;

import java.io.Serializable;

public class Supplier implements Serializable {
	private static final long serialVersionUID = -6552675620832438869L;
	private long id;
	private String name;
	private String phone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
