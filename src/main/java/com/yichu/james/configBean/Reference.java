package com.yichu.james.configBean;

import com.yichu.james.invoke.HttpInvoke;
import com.yichu.james.invoke.Invoke;
import com.yichu.james.proxy.advice.InvokeInvocationHandler;
import com.yichu.james.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reference implements Serializable,FactoryBean,ApplicationContextAware,InitializingBean {

    private String id;
    private String intf;
    private String protocol;
    private Invoke invoke;
    private ApplicationContext applicationContext;

    private static Map<String,Invoke> invokeMap = new HashMap<String,Invoke>();

    /**
     * @Fields registryInfo 这个是生产者的多个服务的列表
     */
    private List<String> registryInfo = new ArrayList<String>();

    static {
        invokeMap.put("http",new HttpInvoke());
        invokeMap.put("rmi",null);
    }

    //获得spring上下文
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        registryInfo = BaseRegistryDelegate.getRegistry(id, applicationContext);
    }


    /*  拿到一个实例，这个方法是spring调用的，spring初始化的时候调用的，
        具体是getBean方法里面调用（当ApplicationContext.getBean("id")的时候其实调用的是 getObject）
        getObject这个方法的返回值，会交给spring容器进行管理
        在getObject这个方法里面，返回的是intf这个接口的代理*/
    public Object getObject() throws Exception {
        System.out.println("返回intf的代理对象");
        if(protocol != null && !"".equals(protocol)) {
            invoke = invokeMap.get(protocol);
        }else {
            Protocol pro = applicationContext.getBean(Protocol.class);
            if(pro.getName() != null && !pro.getName().equals("")) {
                invoke = invokeMap.get(pro.getName());
            }else {
                invoke = invokeMap.get("http");
            }
        }
        //返回代理实例
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[] {Class.forName(intf)},
                new InvokeInvocationHandler(invoke, this));
    }

    public Class<?> getObjectType() {
        if(intf != null || !"".equals(intf)) {
            try {
                return Class.forName(intf);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isSingleton() {
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
