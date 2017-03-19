package org.sysu.sdcs.order.analysis.utils.common;

public class KeyBuilder {
	// customer analysis context redis key
	private static final String CUSTOMER_KEY_FORMAT = "Customer:%d";
	
	public static String buildCustomerKey(Long id) {
		return String.format(CUSTOMER_KEY_FORMAT, id);
	}
}
