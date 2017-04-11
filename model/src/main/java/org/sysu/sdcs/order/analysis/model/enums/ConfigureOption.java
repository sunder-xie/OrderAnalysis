package org.sysu.sdcs.order.analysis.model.enums;

public enum ConfigureOption {

	CUSTOMER_ANALYSIS_SWITCH("switch.customer.analysis", "false"),
	GOODS_TYPE_ANALYSIS_SWITCH("switch.goos.type.analysis", "false"), 
	ORDER_ANALYSIS_SWITCH("switch.order.analysis", "false");

	private String name;
	private String content;

	ConfigureOption(String name, String content) {
		this.name = name;
		this.content = content;
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
