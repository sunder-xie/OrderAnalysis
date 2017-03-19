package org.sysu.sdcs.order.analysis.model.analysis.context;

import java.util.ArrayList;
import java.util.List;

import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;

public class GoodsTypeDistribution {
	private GoodsTypeModel type;
	private List<Double> prices;

	public GoodsTypeModel getType() {
		return type;
	}

	public void setType(GoodsTypeModel type) {
		this.type = type;
	}

	public List<Double> getPrices() {
		return prices;
	}

	public void setPrices(List<Double> price) {
		this.prices = price;
	}

	public void addPrice(Double price) {
		if (this.prices == null) {
			this.prices = new ArrayList<>();
		}
		this.prices.add(price);
	}
}
