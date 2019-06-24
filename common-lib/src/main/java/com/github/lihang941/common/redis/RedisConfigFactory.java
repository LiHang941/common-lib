package com.github.lihang941.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Map;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/11
 */
public class RedisConfigFactory {


    /**
     * 缓存管理
     *
     * @param duration
     * @param redisConnectionFactory
     * @return
     */
    public static CacheManager defaultCacheManager(Duration duration, Map<String, RedisCacheConfiguration> redisCacheConfigurationMap, RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        if (redisCacheConfigurationMap == null) {
            return new RedisCacheManager(redisCacheWriter,
                    getRedisCacheConfigurationWithTtl(duration));
        } else {
            return new RedisCacheManager(redisCacheWriter,
                    getRedisCacheConfigurationWithTtl(duration),
                    redisCacheConfigurationMap);
        }
    }

    /**
     * 获取缓存配置
     *
     * @param duration
     * @return
     */
    public static RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Duration duration) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(
                new ObjectMapper().setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                        .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        );

        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)); // 设置value序列化方式
        if (duration != null) {
            defaultCacheConfig = defaultCacheConfig.entryTtl(duration);
        }
        return defaultCacheConfig;
    }


    /**
     * cache key生成
     *
     * @return
     */
    public static KeyGenerator cacheGenerator() {
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
