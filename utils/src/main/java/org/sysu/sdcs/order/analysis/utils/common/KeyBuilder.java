package org.sysu.sdcs.order.analysis.utils.common;

public class KeyBuilder {
	// customer analysis context redis key
	private static final String CUSTOMER_KEY_FORMAT = "Customer:%s";
	private static final String GOODSTYPE_KEY_FORMAT = "GoodsType:%s";

	public static String buildCustomerKey(String id) {
		return String.format(CUSTOMER_KEY_FORMAT, id);
	}

	public static String buildGoodsTypeKey(String id) {
		return String.format(GOODSTYPE_KEY_FORMAT, id);
	}
}
