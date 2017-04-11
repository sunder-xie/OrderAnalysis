package org.sysu.sdcs.order.analysis.model.analysis.calculate;

public class TypeCounter implements Comparable<TypeCounter> {
	public TypeCounter(String type, int size) {
		super();
		this.type = type;
		this.size = size;
	}

	private String type;
	private int size;

	@Override
	public int compareTo(TypeCounter obj) {
		if (size > obj.size) {
			return -1;
		} else if (size < obj.size) {
			return 1;
		}
		return 0;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
}
