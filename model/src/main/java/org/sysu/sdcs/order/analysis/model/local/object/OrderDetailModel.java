package org.sysu.sdcs.order.analysis.model.local.object;

public class OrderDetailModel {
	private long goods;
	private double price;
	private int count;

	public long getGoods() {
		return goods;
	}

	public void setGoods(long goods) {
		this.goods = goods;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
