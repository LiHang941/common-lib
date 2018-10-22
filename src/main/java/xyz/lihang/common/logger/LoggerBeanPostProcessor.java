package xyz.lihang.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/9/3
 */
public class LoggerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), (Field field) -> {
            ReflectionUtils.makeAccessible(field);
            if (field.getType() == Logger.class && field.getAnnotation(Log.class) != null) {
                Logger logger = LoggerFactory.getLogger(bean.getClass().getName());
                field.set(bean, logger);
            }
        });
        return bean;
    }
}
