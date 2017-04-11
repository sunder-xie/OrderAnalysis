package util;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;
import org.sysu.sdcs.order.analysis.model.common.PointPair;

public class ConcurrentMapTest {
	@Test
	public void test() {
		PointPair point = new PointPair(1, 2);
		ConcurrentHashMap<PointPair, String> map = new ConcurrentHashMap<>();
		map.put(point, "test");
		String test =map.get(new PointPair(1,2));
		Assert.assertNotNull(test);
	}
}
