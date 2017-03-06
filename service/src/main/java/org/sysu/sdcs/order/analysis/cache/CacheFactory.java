package org.sysu.sdcs.order.analysis.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Model cache
 * 
 * @author Zhuang Yixin
 *
 */
@Service
public class CacheFactory {
	@Autowired
	private CustomerCache customerCache;

	public AbstractCache getCache(CacheType cacheType) throws Exception {
		switch (cacheType) {
			case Customer:
				return customerCache;
			default:
				throw new Exception(cacheType.toString());
		}
	}
}
