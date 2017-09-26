package com.zq.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by 张少昆 on 2017/7/11 0011.
 */
@Configuration
public class RedisConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);
    // Redis服务器地址
    @Value( "${ctsi.redis.host}" )
    private String host;
    // Redis默认监听端口
    @Value( "${ctsi.redis.port}" )
    private int port;
    // 失效时间：0 - 从不过期（单位秒）
    @Value( "${ctsi.redis.expire}" )
    private int expire;
    //客户端闲置多少毫秒后，断开连接：0 - 从不断开（单位毫秒）
    @Value( "${ctsi.redis.timeout}" )
    private int timeout;
    @Value( "${ctsi.redis.password}" )
    private String password;
    @Value( "${ctsi.redis.maxTotal}" )
    private int maxTotal;
    @Value( "${ctsi.redis.maxIdle}" )
    private int maxIdle;
    @Value( "${ctsi.redis.minIdle}" )
    private int minIdle;
    @Value( "${ctsi.redis.numTestsPerEvictionRun}" )
    private int numTestsPerEvictionRun;
    @Value( "${ctsi.redis.timeBetweenEvictionRunsMillis}" )
    private int timeBetweenEvictionRunsMillis;
    @Value( "${ctsi.redis.minEvictableIdleTimeMillis}" )
    private int minEvictableIdleTimeMillis;
    @Value( "${ctsi.redis.softMinEvictableIdleTimeMillis}" )
    private int softMinEvictableIdleTimeMillis;
    @Value( "${ctsi.redis.maxWaitMillis}" )
    private int maxWaitMillis;


    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setEnableDefaultSerializer(true);//default true
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();//可以用mapper构造
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);//default jdk；
        //        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));//default jdk TODO WHAT? 旧的方式
        //		redisTemplate.setEnableTransactionSupport(true);//default false  这里不需要
        redisTemplate.setStringSerializer(new StringRedisSerializer());//default;  wtf? 就是new String(byte[])和String.getByte...
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        return redisTemplate;
    }
    @Bean( "strRedisTemplate" )
    public RedisTemplate strRedisTemplate(){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setEnableDefaultSerializer(true);//default true
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();//可以用mapper构造
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);//default jdk；
        //        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));//default jdk TODO WHAT? 旧的方式
        //		redisTemplate.setEnableTransactionSupport(true);//default false  这里不需要
        redisTemplate.setStringSerializer(new StringRedisSerializer());//default;  wtf? 就是new String(byte[])和String.getByte...
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        return redisTemplate;
    }
    @Bean( "intRedisTemplate" )
    public RedisTemplate intRedisTemplate(){
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setEnableDefaultSerializer(true);//default true
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();//可以用mapper构造
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);//default jdk；
        //        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));//default jdk TODO WHAT? 旧的方式
        //		redisTemplate.setEnableTransactionSupport(true);//default false  这里不需要
        redisTemplate.setStringSerializer(new StringRedisSerializer());//default;  wtf? 就是new String(byte[])和String.getByte...
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        return redisTemplate;
    }
    @Bean( "longRedisTemplate" )
    public RedisTemplate longRedisTemplate(){
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setEnableDefaultSerializer(true);//default true
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();//可以用mapper构造
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);//default jdk；
        //        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));//default jdk TODO WHAT? 旧的方式
        //		redisTemplate.setEnableTransactionSupport(true);//default false  这里不需要
        redisTemplate.setStringSerializer(new StringRedisSerializer());//default;  wtf? 就是new String(byte[])和String.getByte...
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        return redisTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();//默认localhost 6379 usePool timeout:2000
        //        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setTimeout(timeout); //default

        //    jedisConnectionFactory.setShardInfo(jedisShardInfo());//分布式信息

        jedisConnectionFactory.setUsePool(true);//default
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());

        jedisConnectionFactory.setUseSsl(false);//default


        return jedisConnectionFactory;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);//GenericObjectPoolConfig default -1L
        jedisPoolConfig.setBlockWhenExhausted(true);//GenericObjectPoolConfig default

        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");//GenericObjectPoolConfig default
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);//JedisPoolConfig 60000L; GenericObjectPoolConfig 1000L*60L*30L
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);//GenericObjectPoolConfig default -1
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);//30000L -1; GenericObjectPoolConfig 3
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);//JedisPoolConfig 30000L; GenericObjectPoolConfig -1L

        jedisPoolConfig.setTestOnBorrow(true);//GenericObjectPoolConfig default false
        jedisPoolConfig.setTestOnCreate(false);//GenericObjectPoolConfig default
        jedisPoolConfig.setTestOnReturn(false);//GenericObjectPoolConfig default
        jedisPoolConfig.setTestWhileIdle(true);//JedisPoolConfig true; GenericObjectPoolConfig false;

        jedisPoolConfig.setFairness(false);//GenericObjectPoolConfig default
        jedisPoolConfig.setJmxEnabled(true);//GenericObjectPoolConfig default
        jedisPoolConfig.setJmxNameBase(null);//GenericObjectPoolConfig default
        jedisPoolConfig.setJmxNamePrefix("pool");//GenericObjectPoolConfig default
        jedisPoolConfig.setLifo(true);//GenericObjectPoolConfig default

        return jedisPoolConfig;
    }
}
