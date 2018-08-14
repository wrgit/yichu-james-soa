package com.yichu.james.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ProtocolBeanDefinitionParse implements BeanDefinitionParser{

    //Protocol
    private Class<?> beanClass;

    public ProtocolBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);

        String name = element.getAttribute("name");
        String host = element.getAttribute("host");
        String port = element.getAttribute("port");

        if(null == name || "".equals(name)) {
            throw new RuntimeException("Protocol name 不能为空");
        }

        if(null == host || "".equals(host)) {
            throw new RuntimeException("Protocol host 不能为空");
        }

        if(null == port || "".equals(port)) {
            throw new RuntimeException("Protocol port 不能为空");
        }

        beanDefinition.getPropertyValues().addPropertyValue("name",name);
        beanDefinition.getPropertyValues().addPropertyValue("host",host);
        beanDefinition.getPropertyValues().addPropertyValue("port",port);

        parserContext.getRegistry().registerBeanDefinition("Protocol",beanDefinition);
        return beanDefinition;
    }
}
