package com.yichu.james.registry;

import com.yichu.james.configBean.Registry;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

public class BaseRegistryDelegate {

    public static void registry(String ref, ApplicationContext applicationContext) {
        //从Registry中获取协议，采用什么方式注册
        Registry registry = applicationContext.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry baseRegistry = registry.getRegistryMap().get(protocol);
        baseRegistry.registry(ref, applicationContext);
    }

    public static List<String> getRegistry(String id,
                                           ApplicationContext application) {
        Registry registry = application.getBean(Registry.class);
        String protocol = registry.getProtocol();
        BaseRegistry registryBean = registry.getRegistryMap().get(protocol);

        return registryBean.getRegistry(id, application);
    }
}
