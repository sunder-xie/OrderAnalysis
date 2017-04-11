package org.sysu.sdcs.order.analysis.model.local.object;

import org.sysu.sdcs.order.analysis.model.common.Range;

public class GoodsTypeModel {
	private String desc;
	private Range<Double> price;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getMaxPrice() {
		return price.getMax();
	}

	public Double getMinPrice() {
		return price.getMin();
	}

	public void setMaxPrice(double max) {
		price.setMax(new Double(max));
	}

	public void setMinPrice(double min) {
		price.setMin(new Double(min));
	}

	public Range<Double> getPrice() {
		return price;
	}

	public void setPrice(Range<Double> price) {
		this.price = price;
	}
}
