package org.sysu.sdcs.order.analysis.utils.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sysu.sdcs.order.analysis.model.analysis.calculate.Dimension;
import org.sysu.sdcs.order.analysis.model.analysis.calculate.Point;

public class Calculator {
	public static double calDistance(Point first, Point second) {
		Map<String, Double> points = new HashMap<>();
		List<Dimension> firstDimension = first.getDimensions();
		for (Dimension point : firstDimension) {
			Double value = new Double(point.getLength());
			value = value / first.getCount();
			points.put(point.getType(), value);
		}
		List<Dimension> secondDimension = second.getDimensions();
		for (Dimension point : secondDimension) {
			Double length = points.get(point.getType());
			if (length == null) {
				length = 0.0;
			}
			double value = new Double(point.getLength());
			value = value / second.getCount();
			length = length - value;
			points.put(point.getType(), length);
		}
		double total = 0;
		for (Map.Entry<String, Double> item : points.entrySet()) {
			total += item.getValue() * item.getValue();
		}
		total = Math.sqrt(total / 2);
		return total;
	}

	public static List<Integer> calInterval(int n, int size) {
		int total = calTotal(5, 1);
		int interval = n / total;
		List<Integer> result = new ArrayList<>();
		result.add(0);
		for (int i = 1; i < size; i++) {
			result.add(interval * calTotal(i, 1));
		}
		return result;
	}

	public static int calTotal(int max, int min) {
		return (max + min) * (max - min + 1) / 2;
	}
}
