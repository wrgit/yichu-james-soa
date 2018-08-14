package com.yichu.james.test;

import com.yichu.james.test.service.TestService;
import com.yichu.james.test.service.TestServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("mytest.xml");

        // xml 中 通过  bean 定义
        /*TestServiceImpl bean = (TestServiceImpl)context.getBean("testServiceImpl");
        bean.eat("吃饭");*/

        //注释xml中的bean定义， 下面方式 通过 getObject 方式获取，获得是 代理实例
        TestService bean = context.getBean(TestService.class);
        bean.eat("吃饭");




    }

}
