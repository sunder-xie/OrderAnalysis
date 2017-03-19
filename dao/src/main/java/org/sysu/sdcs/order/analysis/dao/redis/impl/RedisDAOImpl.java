package org.sysu.sdcs.order.analysis.dao.redis.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.sysu.sdcs.order.analysis.dao.redis.RedisDAO;

@Component
public class RedisDAOImpl implements RedisDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDAOImpl.class);

	@Autowired
	private StringRedisTemplate template;

	@Override
	public boolean set(String key, byte[] value) {
		return template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] redisKey = getSerializer().serialize(key);
					connection.set(redisKey, value);
					return true;
				} catch (Exception ex) {
					LOGGER.error("Redis set fail.", ex);
					return false;
				}
			}
		});
	}

	@Override
	public boolean del(String key) {
		return template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] redisKey = getSerializer().serialize(key);
					connection.del(redisKey);
					return true;
				} catch (Exception ex) {
					LOGGER.error("Redis del fail.", ex);
					return false;
				}
			}
		});
	}

	@Override
	public byte[] get(String key) {
		return template.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] redisKey = getSerializer().serialize(key);
					byte[] value = connection.get(redisKey);
					return value;
				} catch (Exception ex) {
					LOGGER.error("Redis get fail.", ex);
					return null;
				}
			}
		});
	}

	@Override
	public boolean hSet(String key, String hashKey, byte[] value) {
		return template.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] redisKey = getSerializer().serialize(key);
					byte[] redisHashKey = getSerializer().serialize(hashKey);
					return connection.hSet(redisKey, redisHashKey, value);
				} catch (Exception ex) {
					LOGGER.error("Redis hSet fail.", ex);
					return false;
				}
			}
		});
	}

	@Override
	public long hDel(String key, String hashKey) {
		return template.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] redisKey = getSerializer().serialize(key);
					byte[] redisHashKey = getSerializer().serialize(hashKey);
					return connection.hDel(redisKey, redisHashKey);
				} catch (Exception ex) {
					LOGGER.error("Redis hDel fail.", ex);
					return 0L;
				}
			}
		});
	}

	@Override
	public byte[] hGet(String key, String hashKey) {
		return template.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] redisKey = getSerializer().serialize(key);
					byte[] redisHashKey = getSerializer().serialize(hashKey);
					byte[] value = connection.hGet(redisKey, redisHashKey);
					return value;
				} catch (Exception ex) {
					LOGGER.error("Redis hGet fail.", ex);
					return null;
				}
			}
		});
	}

	@Override
	public Map<byte[], byte[]> hGetAll(String key) {
		return template.execute(new RedisCallback<Map<byte[], byte[]>>() {
			@Override
			public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] redisKey = getSerializer().serialize(key);
					Map<byte[], byte[]> value = connection.hGetAll(redisKey);
					return value;
				} catch (Exception ex) {
					LOGGER.error("Redis hGetAll fail.", ex);
					return null;
				}
			}
		});
	}

	@Override
	public RedisSerializer<String> getSerializer() {
		return template.getStringSerializer();
	}
}
