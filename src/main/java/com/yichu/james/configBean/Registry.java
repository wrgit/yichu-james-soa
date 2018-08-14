package com.yichu.james.configBean;

import com.yichu.james.registry.BaseRegistry;
import com.yichu.james.registry.RedisRegistry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Registry implements Serializable {

    private String protocol;
    private String address;

    private static Map<String,BaseRegistry> registryMap = new HashMap<>();

    static {
        registryMap.put("redis",new RedisRegistry());
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, BaseRegistry> getRegistryMap() {
        return registryMap;
    }

    public void setRegistryMap(Map<String, BaseRegistry> registryMap) {
        Registry.registryMap = registryMap;
    }
}
