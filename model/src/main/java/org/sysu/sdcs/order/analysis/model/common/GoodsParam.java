package org.sysu.sdcs.order.analysis.model.common;

public class GoodsParam {
	public GoodsParam(long type, Range<Double> price, Range<Long> supplier) {
		super();
		this.type = type;
		this.price = price;
		this.supplier = supplier;
	}

	private long type;
	private Range<Double> price;
	private Range<Long> supplier;

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public Range<Double> getPrice() {
		return price;
	}

	public void setPrice(Range<Double> price) {
		this.price = price;
	}

	public Range<Long> getSupplier() {
		return supplier;
	}

	public void setSupplier(Range<Long> supplier) {
		this.supplier = supplier;
	}

}
