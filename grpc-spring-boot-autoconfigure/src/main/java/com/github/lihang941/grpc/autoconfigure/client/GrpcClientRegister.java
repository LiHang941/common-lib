package com.github.lihang941.grpc.autoconfigure.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/2
 */
public class GrpcClientRegister implements BeanPostProcessor {

    private static final Logger logger = Logger.getLogger("GrpcClientRegister");

    private Map<String, ClientServiceProperties> managedChannelConfigMap;

    public GrpcClientRegister(List<ClientServiceProperties> clientServiceProperties) {
        this.managedChannelConfigMap = new HashMap<>();
        if (clientServiceProperties == null || clientServiceProperties.size() < 0) {
            logger.warning("Grpc Client Server Config Cannot be null");
            return;
        }
        init(clientServiceProperties);
    }


    public void init(List<ClientServiceProperties> clientServiceProperties) {
        for (ClientServiceProperties properties : clientServiceProperties) {
            this.managedChannelConfigMap.put(properties.getName(), properties);
        }
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            if (field.getAnnotation(GrpcClient.class) != null && field.getType().getSuperclass() == AbstractStub.class) {
                try {
                    field.getType().asSubclass(AbstractStub.class);
                } catch (ClassCastException e) {
                    logger.warning(MessageFormat.format("class:{0} 不是 AbstractStub 的子类", field.getType().getName()));
                    throw e;
                }
                GrpcClient grpcClient = field.getAnnotation(GrpcClient.class);
                ClientServiceProperties clientServiceProperties = managedChannelConfigMap.get(grpcClient.serverName());
                Assert.notNull(clientServiceProperties, "rpc server name [" + grpcClient.serverName() + "] 不存在");
                ManagedChannel managedChannel = ManagedChannelBuilder
                        .forAddress(clientServiceProperties.getAddress(), clientServiceProperties.getPort())
                        .usePlaintext()
                        .build();
                try {
                    Class<?> type = field.getType();
                    String name = type.getName();
                    Object object = Class.forName(name.substring(0, name.indexOf("$")))
                            .getMethod("newBlockingStub", Channel.class)
                            .invoke(null, managedChannel);
                    field.set(bean, object);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return bean;
    }

}
