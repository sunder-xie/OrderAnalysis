package org.sysu.sdcs.order.analysis.model.database.entity;

import java.io.Serializable;

public class GoodsType implements Serializable {
	private static final long serialVersionUID = -208853013121699679L;
	private long id;
	private String desc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
