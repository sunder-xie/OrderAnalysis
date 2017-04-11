package org.sysu.sdcs.order.analysis.utils.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sysu.sdcs.order.analysis.model.common.PriceUnit;
import org.sysu.sdcs.order.analysis.model.common.Range;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;

public class PriceQuantifier {

	private static final int LEVEL_COUNT = 5;
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceQuantifier.class);

	public static Integer getLevel(GoodsTypeModel type, Double price) {
		PriceUnit unit = getUnit(type);
		double base = unit.getBase();
		for (int i = 0; i < LEVEL_COUNT; i++) {
			base += unit.getInterval();
			if (price <= base) {
				return i;
			}
		}
		LOGGER.warn("The price more than the maximum value, good type: {}, price: {}, max: {}, min: {}.",
				type.getDesc(), price, type.getMaxPrice(), type.getMinPrice());
		return LEVEL_COUNT;
	}

	public static Range<Double> getRange(GoodsTypeModel type, int level) {
		PriceUnit unit = getUnit(type);
		Range<Double> range = new Range<>();
		range.setMin(unit.getBase() + unit.getInterval() * level);
		if (level < LEVEL_COUNT) {
			range.setMax(unit.getBase() + unit.getInterval() * (level + 1));
		} else {
			range.setMax(Double.MAX_VALUE);
		}
		return range;
	}

	private static PriceUnit getUnit(GoodsTypeModel type) {
		double range = type.getMaxPrice() - type.getMinPrice();
		double interval = range / LEVEL_COUNT;
		PriceUnit unit = new PriceUnit();
		unit.setBase(type.getMinPrice());
		unit.setInterval(interval);
		return unit;
	}

}
