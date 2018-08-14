package com.yichu.james.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ReferenceBeanDefinitionParse implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ReferenceBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);

        String id = element.getAttribute("id");
        String intf = element.getAttribute("interface");
        String protocol = element.getAttribute("protocol");

        if(null == id || "".equals(id)) {
            throw new RuntimeException("Reference id 不能为空");
        }

        if(null == intf || "".equals(intf)) {
            throw new RuntimeException("Reference interface 不能为空");
        }

        if(null == protocol || "".equals(protocol)) {
            throw new RuntimeException("Reference protocol 不能为空");
        }

        beanDefinition.getPropertyValues().addPropertyValue("id",id);
        beanDefinition.getPropertyValues().addPropertyValue("intf",intf);
        beanDefinition.getPropertyValues().addPropertyValue("id",id);

        parserContext.getRegistry().registerBeanDefinition("reference",beanDefinition);
        return beanDefinition;
    }
}
