package com.yichu.james.configBean;

import com.yichu.james.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

public class Service implements Serializable,ApplicationContextAware,InitializingBean {

    private String intf;
    private String ref;

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        /* 存入到redis中的结构
        {ref:{
           "192.168.200.111:27017": {
                "protocol": { },
                "service": { }
            },
            "192.168.200.112:27017": {
                "protocol": { },
                "service": { }
            },
            }
        }*/
        BaseRegistryDelegate.registry(ref,applicationContext);
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
