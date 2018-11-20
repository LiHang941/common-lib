package com.github.lihang941.common.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/6
 */
public abstract class LongRedisTemplate extends RedisTemplate<String, Long> {

    public LongRedisTemplate(RedisConnectionFactory connectionFactory) {
        setConnectionFactory(connectionFactory);

        LongRedisSerializer longRedisSerializer = new LongRedisSerializer();
        setValueSerializer(longRedisSerializer);
        setHashValueSerializer(longRedisSerializer);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        setKeySerializer(stringRedisSerializer);
        setHashKeySerializer(stringRedisSerializer);

    }
}
