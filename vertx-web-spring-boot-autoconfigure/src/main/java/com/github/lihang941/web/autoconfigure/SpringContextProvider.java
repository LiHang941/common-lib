package com.github.lihang941.web.autoconfigure;

import com.github.lihang941.vertx.rest.ContextProvider;
import org.springframework.context.ApplicationContext;

/**
 * @author : lihang941
 * @since : 2019/1/15
 */
public class SpringContextProvider implements ContextProvider {

    private ApplicationContext applicationContext;

    public SpringContextProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <T> T provideContext(Class<T> clz) {
        if (clz.isInstance(applicationContext)) {
            return (T) applicationContext;
        }
        try {
            return applicationContext.getBean(clz);
        } catch (Exception e) {
            return null;
        }
    }
}
