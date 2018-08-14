package com.yichu.james.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ServiceBeanDefinitionParse implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ServiceBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);

        String intf = element.getAttribute("interface");
        String ref = element.getAttribute("ref");

        if(null == intf || "".equals(intf)) {
            throw new RuntimeException("Service interface 不能为空");
        }

        if(null == ref || "".equals(ref)) {
            throw new RuntimeException("Service ref 不能为空");
        }

        beanDefinition.getPropertyValues().addPropertyValue("intf",intf);
        beanDefinition.getPropertyValues().addPropertyValue("ref",ref);

        parserContext.getRegistry().registerBeanDefinition("Service",beanDefinition);
        return beanDefinition;
    }
}
