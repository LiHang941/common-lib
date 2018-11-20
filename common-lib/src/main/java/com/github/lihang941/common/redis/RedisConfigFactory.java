package com.github.lihang941.common.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/11
 */
public class RedisConfigFactory {

    /**
     * 缓存管理
     * @param duration
     * @param redisConnectionFactory
     * @return
     */
    public static CacheManager defaultCacheManager(Duration duration,RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(duration)
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class)));
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }


    /**
     * cache key生成
     * @return
     */
    public static KeyGenerator cacheGenerator(){
        return (target, method, params) -> {
            StringBuilder key = new StringBuilder();
            key.append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");
            if (params.length == 0) {
                return key.append("NO_PARAM_KEY").toString();
            }
            for (Object param : params) {
                if (param == null) {
                    key.append("NULL_PARAM_KEY");
                } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                    int length = Array.getLength(param);
                    for (int i = 0; i < length; i++) {
                        key.append(Array.get(param, i));
                        key.append(',');
                    }
                } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass()) || param instanceof String) {
                    key.append(param);
                } else {
                    key.append(param.hashCode());
                }
                key.append('-');
            }
            return key.toString();
        };
    }


}
