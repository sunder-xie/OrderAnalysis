package org.sysu.sdcs.order.analysis.model.analysis.calculate;

import java.util.ArrayList;
import java.util.List;

public class Point {
	private List<Dimension> dimensions;
	private int count;

	public Point() {
		dimensions = new ArrayList<>();
	}

	public void add(Dimension dimension) {
		dimensions.add(dimension);
	}

	public List<Dimension> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<Dimension> dimensions) {
		this.dimensions = dimensions;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		for (Dimension dimension : dimensions) {
			hashCode &= dimension.getType().hashCode();
		}
		return hashCode;
	}
}
