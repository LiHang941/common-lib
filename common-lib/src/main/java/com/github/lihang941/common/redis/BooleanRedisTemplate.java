package com.github.lihang941.common.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author : lihang941
 * @since : 2019/3/25
 */
public class BooleanRedisTemplate extends RedisTemplate<String, Long> {

    public BooleanRedisTemplate(RedisConnectionFactory connectionFactory) {
        setConnectionFactory(connectionFactory);

        BooleanRedisSerializer booleanRedisSerializer = new BooleanRedisSerializer();
        setValueSerializer(booleanRedisSerializer);
        setHashValueSerializer(booleanRedisSerializer);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        setKeySerializer(stringRedisSerializer);
        setHashKeySerializer(stringRedisSerializer);
        afterPropertiesSet();
    }
}
