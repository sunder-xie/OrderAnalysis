package web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger {
	@Test
	public void test() {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.warn("i am warn");
		logger.error("i am error");
		logger.info("i am info");
		logger.info("{}-{}","a","b");
	}
}
