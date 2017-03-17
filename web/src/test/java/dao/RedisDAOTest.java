package dao;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sysu.sdcs.order.analysis.dao.redis.RedisDAO;

/**
 * Unit test for RedisDAO.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:spring/spring.xml",
		"classpath:spring/spring-redis.xml",
		"classpath:spring/spring-mybatis.xml" })
public class RedisDAOTest {
	@Resource
	private RedisDAO redisDAO;
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDAOTest.class);
	private static final String TEST_KEY = "test key";
	private static final String TEST_HASH_KEY = "test hash key";
	private static final String TEST_HASH_FIELD = "test hash field";
	private static final String TEST_VALUE = "test value";

	@Test
	public void testSet() {
		boolean isSetSuccess = redisDAO.set(TEST_KEY, TEST_VALUE.getBytes());
		LOGGER.info("Redis set: {}.", isSetSuccess);
		boolean isHSetSuccess = redisDAO.hSet(TEST_HASH_KEY, TEST_HASH_FIELD, TEST_VALUE.getBytes());
		LOGGER.info("Redis hSet: {}.", isHSetSuccess);
	}

	@Test
	public void testGet() {
		String value = new String(redisDAO.get(TEST_KEY));
		LOGGER.info("Redis get: {}.", value);
		value = new String(redisDAO.hGet(TEST_HASH_KEY, TEST_HASH_FIELD));
		LOGGER.info("Redis hGet: {}.", value);
		Map<byte[],byte[]> map= redisDAO.hGetAll(TEST_HASH_KEY);
	}
}
