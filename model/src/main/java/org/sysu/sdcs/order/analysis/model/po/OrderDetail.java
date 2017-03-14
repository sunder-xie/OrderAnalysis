package org.sysu.sdcs.order.analysis.model.po;

import java.io.Serializable;

public class OrderDetail implements Serializable{
	private long id;
	private long order;
	private long goods;
	private int count;
	private double price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public long getGoods() {
		return goods;
	}

	public void setGoods(long goods) {
		this.goods = goods;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
