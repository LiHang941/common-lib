package xyz.lihang.common.redis;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public class CacheGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
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
    }
}
