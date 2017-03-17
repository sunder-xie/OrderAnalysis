package org.sysu.sdcs.order.analysis.service.basic;

public abstract class AbstractFactory<T, E> {
	public abstract T getInstance(E type) throws Exception;
}
