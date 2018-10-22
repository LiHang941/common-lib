package xyz.lihang.common.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.UnsupportedEncodingException;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public class LongRedisSerializer implements RedisSerializer<Long> {


    @Override
    public byte[] serialize(Long l) throws SerializationException {
        try {
            return l == null ? new byte[0] : l.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
           return new byte[0] ;
        }
    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
       if (bytes == null){
           return null;
       }else {
           try {
               String s = new String(bytes, "UTF-8");
               long l = Long.parseLong(s);
               return l;
           } catch (UnsupportedEncodingException  | NumberFormatException e) {
               e.printStackTrace();
               return null;
           }
       }
    }
}
