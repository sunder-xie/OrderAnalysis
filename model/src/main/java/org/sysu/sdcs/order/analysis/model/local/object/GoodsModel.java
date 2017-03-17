package org.sysu.sdcs.order.analysis.model.local.object;

public class GoodsModel {
	private long type;
	private long supplier;
	private String name;
	private String desc;
	private double price;
	private int stock;

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getSupplier() {
		return supplier;
	}

	public void setSupplier(long supplier) {
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
