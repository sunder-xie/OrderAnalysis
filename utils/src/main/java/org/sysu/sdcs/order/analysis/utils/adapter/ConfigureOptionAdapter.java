package org.sysu.sdcs.order.analysis.utils.adapter;

import org.sysu.sdcs.order.analysis.model.enums.ConfigureOption;
import org.sysu.sdcs.order.analysis.model.enums.ExecutorType;

public class ConfigureOptionAdapter {
	public static ConfigureOption convert(ExecutorType type) {
		switch (type) {
		case Customer:
			return ConfigureOption.CUSTOMER_ANALYSIS_SWITCH;
		case GoodsType:
			return ConfigureOption.GOODS_TYPE_ANALYSIS_SWITCH;
		default:
			throw new NullPointerException(String.format("There is not executorType: %s.", type.toString()));
		}
	}
}
