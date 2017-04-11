package org.sysu.sdcs.order.analysis.model.database.entity;

import java.io.Serializable;

public class Configure implements Serializable {
	private static final long serialVersionUID = -740099623280028279L;
	private int id;
	private String name;
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
