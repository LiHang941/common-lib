package com.github.lihang941.common.rpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.AbstractStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : lihang1329@gmail.com
 * @since : 2018/11/2
 */
public class GrpcClientRegister implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(GrpcClientRegister.class);

    public static class ManagedChannelConfig {
        private String address;
        private int port;
        private String serverName;

        public String getAddress() {
            return address;
        }

        public ManagedChannelConfig setAddress(String address) {
            this.address = address;
            return this;
        }

        public int getPort() {
            return port;
        }

        public ManagedChannelConfig setPort(int port) {
            this.port = port;
            return this;
        }

        public String getServerName() {
            return serverName;
        }

        public ManagedChannelConfig setServerName(String serverName) {
            this.serverName = serverName;
            return this;
        }
    }

    private Map<String, ManagedChannelConfig> managedChannelConfigMap;

    public GrpcClientRegister(List<ManagedChannelConfig> managedChannelConfigs) {
        Assert.notEmpty(managedChannelConfigs, "rpc 服务内容不能为Null");
        this.managedChannelConfigMap = new HashMap<>();
        for (ManagedChannelConfig managedChannelConfig : managedChannelConfigs) {
            this.managedChannelConfigMap.put(managedChannelConfig.getServerName(), managedChannelConfig);
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
                    logger.error("class:{} 不是 AbstractStub 的子类", field.getType().getName());
                    throw e;
                }
                GrpcClient grpcClient = field.getAnnotation(GrpcClient.class);
                ManagedChannelConfig managedChannelConfig = managedChannelConfigMap.get(grpcClient.serverName());
                Assert.notNull(managedChannelConfig, "rpc server name [" + grpcClient.serverName() + "] 不存在");
                ManagedChannel managedChannel = ManagedChannelBuilder
                        .forAddress(managedChannelConfig.getAddress(), managedChannelConfig.getPort())
                        .usePlaintext(true)
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
