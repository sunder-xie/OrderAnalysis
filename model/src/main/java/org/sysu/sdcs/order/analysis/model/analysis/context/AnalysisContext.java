package org.sysu.sdcs.order.analysis.model.analysis.context;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalysisContext implements Serializable {

	private static final long serialVersionUID = 9048358474082641243L;
	
	private Map<String, Integer> dateDistribution;
	private Map<Long, List<Double>> typePriceDistribution;
	private List<Set<Long>> typeDistribution;

	public Map<String, Integer> getDateDistribution() {
		return dateDistribution;
	}

	public void setDateDistribution(Map<String, Integer> dateDistribution) {
		this.dateDistribution = dateDistribution;
	}

	public Map<Long, List<Double>> getTypePriceDistribution() {
		return typePriceDistribution;
	}

	public void setTypePriceDistribution(Map<Long, List<Double>> typePriceDistribution) {
		this.typePriceDistribution = typePriceDistribution;
	}

	public List<Set<Long>> getTypeDistribution() {
		return typeDistribution;
	}

	public void setTypeDistribution(List<Set<Long>> typeDistribution) {
		this.typeDistribution = typeDistribution;
	}

}
