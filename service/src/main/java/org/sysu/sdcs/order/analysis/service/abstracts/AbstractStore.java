package org.sysu.sdcs.order.analysis.service.abstracts;

public abstract class AbstractStore<T, E> {
	public abstract T getInstance(E type) throws Exception;
}
