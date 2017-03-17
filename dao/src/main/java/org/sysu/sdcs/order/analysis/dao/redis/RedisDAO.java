package org.sysu.sdcs.order.analysis.dao.redis;

import java.util.Map;

public interface RedisDAO {
	boolean set(String key, byte[] value);

	boolean del(String key);

	byte[] get(String key);

	boolean hSet(String key, String hashKey, byte[] value);

	long hDel(String key, String hashKey);

	byte[] hGet(String key, String hashKey);

	Map<byte[], byte[]> hGetAll(String key);
}
