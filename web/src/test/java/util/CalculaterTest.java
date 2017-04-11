package util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.sysu.sdcs.order.analysis.utils.calculate.Calculator;

public class CalculaterTest {
	@Test
	public void test_GetInterval() {
		List<Integer> result = Calculator.calInterval(10000, 5);
		Assert.assertNotNull(result);
	}
}
