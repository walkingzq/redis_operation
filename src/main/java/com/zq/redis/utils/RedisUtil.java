package com.zq.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static RedisTemplate<String, Object> template;


	@PostConstruct
	public void init(){
		template = redisTemplate;
	}

	/**
	 * set value用法，永久存储。
	 *
	 * @param key   键
	 * @param value 值
	 */
	public static void set(String key, Object value){
		template.opsForValue().set(key, value);
	}

	/**
	 * set value用法，有存活时间，默认时间单位为秒。务必注意，第三个参数不是offset！！！
	 *
	 * @param key     键
	 * @param value   值
	 * @param timeout 存活时间
	 */
	public static void set(String key, Object value, Long timeout){
		template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 存入多个键值对。
	 *
	 * @param map 多个键值对
	 */
	public static void multiSet(Map<String, Object> map){
		template.opsForValue().multiSet(map);
	}

	/**
	 * 完整的set value用法，有存活时间。
	 *
	 * @param key     键
	 * @param value   值
	 * @param timeout 存活时间
	 * @param unit    时间单位
	 */
	public static void set(String key, Object value, Long timeout, TimeUnit unit){
		template.opsForValue().set(key, value, timeout, unit);
	}

	/**
	 * 根据键获取值
	 *
	 * @param key 键
	 * @return 值
	 */
	public static Object get(String key){
		return template.opsForValue().get(key);
	}

	/**
	 * 根据多个键获取多个值
	 *
	 * @param keys 多个键
	 * @return 多个值
	 */
	public static List<Object> multiGet(List<String> keys){
		return template.opsForValue().multiGet(keys);
	}

	/**
	 * (原有值)++- 原子性操作。
	 *
	 * @param key 键
	 * @return 变化后的数值
	 */
	public static Long incr(String key){
		return template.opsForValue().increment(key, 1);
	}

	/**
	 * 原有值自增指定长度 - 原子性操作。
	 *
	 * @param key   键
	 * @param delta 增长长度
	 * @return 变化后的数值
	 */
	public static Long incr(String key, long delta){
		return template.opsForValue().increment(key, delta);
	}

	/**
	 * 获取键的存活时间TTL(Time to Live)，单位秒。
	 *
	 * @param key 键
	 * @return TTL
	 */
	public static Long getExpire(String key){
		return template.getExpire(key);
	}

	/**
	 * 获取键的存活时间TTL(Time to Live)
	 *
	 * @param key  键
	 * @param unit 时间单位
	 * @return TTL
	 */
	public static Long getExpire(String key, TimeUnit unit){
		return template.getExpire(key, unit);
	}

	/**
	 * 删除某个键
	 *
	 * @param key 要删除的键
	 */
	public static void delete(String key){
		template.delete(key);
	}

	/**
	 * 删除多个键
	 *
	 * @param keys 要删除的键列表
	 */
	public static void delete(List<String> keys){
		template.delete(keys);
	}

	/**
	 * 为键设置失效时间！到了指定时间就会失效。
	 *
	 * @param key  键
	 * @param date 指定时间
	 * @return 是否设置成功
	 */
	public static Boolean expire(String key, Date date){
		return template.expireAt(key, date);
	}

	/**
	 * 为键设置存活时间！
	 *
	 * @param key     键
	 * @param timeout TTL
	 * @param unit    时间单位
	 * @return 是否设置成功
	 */
	public static Boolean expire(String key, Long timeout, TimeUnit unit){
		return template.expire(key, timeout, unit);
	}

	/**
	 * 批量左侧推入，{1、2、3} -> 3、2、1
	 *
	 * @param key    键
	 * @param values 要推入的多个值
	 * @return 列表长度
	 */
	public static Long leftPushAll(String key, Object... values){
		return template.opsForList().leftPushAll(key, values);
	}

	/**
	 * 批量左侧推入，list{1、2、3} -> 3、2、1
	 *
	 * @param key    键
	 * @param values 要推入的值集合
	 * @return 列表长度
	 */
	public static Long leftPushAll(String key, Collection values){
		return template.opsForList().leftPushAll(key, values);
	}

	/**
	 * 左侧推入
	 *
	 * @param key   键
	 * @param value 值
	 * @return 列表长度
	 */
	public static Long leftPush(String key, Object value){
		return template.opsForList().leftPush(key, value);
	}


}
