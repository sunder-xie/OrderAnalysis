package org.sysu.sdcs.order.analysis.cache;

import org.springframework.stereotype.Service;

/**
 * Cache store customer information
 * 
 * @author Zhuang Yixin
 *
 */
@Service
public class CustomerCache implements AbstractCache {
	public int size = 188;

	public void SetSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}

}