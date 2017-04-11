package org.sysu.sdcs.order.analysis.model.analysis.calculate;

public class Dimension {
	private String type;
	private int length;

	public Dimension(String type, int length) {
		this.type = type;
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return String.format("%d:%d", type, length);
	}
}
