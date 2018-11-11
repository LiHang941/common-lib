package xyz.lihang.common.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public abstract class JsonRedisTemplate<T> extends RedisTemplate<String,T> {

    public JsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type; // JsonRedisTemplate<Employee>
        Type types[] = pt.getActualTypeArguments();
        Class<T> clazz = (Class<T>) types[0];
        setConnectionFactory(redisConnectionFactory);
        FastJsonRedisSerializer<T> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(clazz);
        setValueSerializer(fastJsonRedisSerializer);
        setHashValueSerializer(fastJsonRedisSerializer);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        setKeySerializer(stringRedisSerializer);
        setHashKeySerializer(stringRedisSerializer);
    }

}
