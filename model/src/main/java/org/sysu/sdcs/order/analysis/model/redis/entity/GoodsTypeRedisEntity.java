package org.sysu.sdcs.order.analysis.model.redis.entity;

import java.io.Serializable;
import java.util.Set;

import org.sysu.sdcs.order.analysis.model.common.Counter;
import org.sysu.sdcs.order.analysis.model.local.object.GoodsTypeModel;

public class GoodsTypeRedisEntity implements Serializable {

	private static final long serialVersionUID = 7172617660125474598L;
	private GoodsTypeModel type;
	private Counter<String> dateGroup;
	private Counter<Integer> priceGroup;
	private Counter<Set<String>> typeGroup;

	public GoodsTypeModel getType() {
		return type;
	}

	public void setType(GoodsTypeModel type) {
		this.type = type;
	}

	public Counter<String> getDateGroup() {
		return dateGroup;
	}

	public void setDateGroup(Counter<String> dateGroup) {
		this.dateGroup = dateGroup;
	}

	public Counter<Integer> getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(Counter<Integer> priceGroup) {
		this.priceGroup = priceGroup;
	}

	public Counter<Set<String>> getTypeGroup() {
		return typeGroup;
	}

	public void setTypeGroup(Counter<Set<String>> typeGroup) {
		this.typeGroup = typeGroup;
	}

}
