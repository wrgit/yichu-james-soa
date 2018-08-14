package com.yichu.james.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


public class RegistryBeanDefinitionParse implements BeanDefinitionParser{

    //Registry
    private Class<?> beanClass;

    public RegistryBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");

        if(null == protocol || "".equals(protocol)) {
            throw new RuntimeException("Registry protocol 不能为空");
        }

        if(null == address || "".equals(address)) {
            throw new RuntimeException("Registry address 不能为空");
        }

        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address",address);

        parserContext.getRegistry().registerBeanDefinition("Registry",beanDefinition);
        return beanDefinition;
    }
}
