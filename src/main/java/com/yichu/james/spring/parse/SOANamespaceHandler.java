package com.yichu.james.spring.parse;

import com.yichu.james.configBean.Protocol;
import com.yichu.james.configBean.Reference;
import com.yichu.james.configBean.Registry;
import com.yichu.james.configBean.Service;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


public class SOANamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        registerBeanDefinitionParser("registry",new RegistryBeanDefinitionParse(Registry.class));
        registerBeanDefinitionParser("protocol",new ProtocolBeanDefinitionParse(Protocol.class));
        registerBeanDefinitionParser("service",new ServiceBeanDefinitionParse(Service.class));
        registerBeanDefinitionParser("reference",new ReferenceBeanDefinitionParse(Reference.class));
    }
}
