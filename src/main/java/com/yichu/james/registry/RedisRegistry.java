package com.yichu.james.registry;

import com.alibaba.fastjson.JSONObject;
import com.yichu.james.configBean.Protocol;
import com.yichu.james.configBean.Registry;
import com.yichu.james.configBean.Service;
import com.yichu.james.redis.RedisApi;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis 注册中心处理类
 */
public class RedisRegistry implements BaseRegistry {

    /** redis中注册的json格式如下，因为生产者是分布式，会有多个。
     {
         "192.168.200.111:27017": {
             "protocol": {
                 "host": "127.0.0.1",
                 "name": "http",
                 "port": "27017"
             },
             "service": {
                 "intf": "com.dongnao.jack.test.service.TestService",
                 "protocol": "rmi",
                 "ref": "testServiceImpl1"
             }
         },
         "192.168.200.112:27017": {
             "protocol": {
                 "host": "127.0.0.1",
                 "name": "http",
                 "port": "27017"
             },
             "service": {
                 "intf": "com.dongnao.jack.test.service.TestService",
                 "protocol": "rmi",
                 "ref": "testServiceImpl1"
             }
         },
     }
     */
    public boolean registry(String ref, ApplicationContext application) {
        try {
            Protocol protocol = application.getBean(Protocol.class);
            Map<String, Service> services = application.getBeansOfType(Service.class);

            Registry registry = application.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());

            /* 构造的json格式如下
                ref:{
                  "192.168.200.111:27017": {
                    "protocol": {
                      "host": "127.0.0.1",
                      "name": "http",
                      "port": "27017"
                    },
                    "service": {
                      "intf": "com.yichu.james.test.service.TestService",
                      "protocol": "rmi",
                      "ref": "testServiceImpl1"
                    }
                  },
                  "192.168.200.112:27017": {
                    "protocol": {
                      "host": "127.0.0.1",
                      "name": "http",
                      "port": "27017"
                    },
                    "service": {
                      "intf": "com.yichu.james.test.service.TestService",
                      "protocol": "rmi",
                      "ref": "testServiceImpl1"
                    }
                  },
                }
              }*/
            for (Map.Entry<String, Service> entry : services.entrySet()) {
                if (entry.getValue().getRef().equals(ref)) {
                    JSONObject jo = new JSONObject();
                    jo.put("protocol", JSONObject.toJSONString(protocol));
                    jo.put("service", JSONObject.toJSONString(entry.getValue()));

                    JSONObject ipport = new JSONObject();
                    ipport.put(protocol.getHost() + ":" + protocol.getPort(), jo);
                    //去重 如果生产者启动多次，如果不去重，redis ip:port集合下就会有重复
                    lpush(ipport, ref);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void lpush(JSONObject ipport, String key) {
        if (RedisApi.exists(key)) {
            Set<String> keys = ipport.keySet();
            String ipportStr = "";
            //这个循环里面只会循环一次， 因为 Set集合中，只有一个key 就是 ip:port 字符串
            for (String kk : keys) {
                ipportStr = kk;
            }

            //拿redis对应key里面的 内容
            List<String> registryInfo = RedisApi.lrange(key);
            List<String> newRegistry = new ArrayList<String>();

            boolean isold = false;

            for (String node : registryInfo) {
                JSONObject jo = JSONObject.parseObject(node);
                if (jo.containsKey(ipportStr)) {
                    newRegistry.add(ipport.toJSONString());
                    isold = true;
                } else {
                    newRegistry.add(node);
                }
            }

            if (isold) {
                //这里是老机器启动去重: 防止重复启动，重复注册
                if (newRegistry.size() > 0) {
                    RedisApi.del(key);
                    String[] newReStr = new String[newRegistry.size()];
                    for (int i = 0; i < newRegistry.size(); i++) {
                        newReStr[i] = newRegistry.get(i);
                    }
                    RedisApi.lpush(key, newReStr);
                }
            } else {
                //这里是加入新启动的机器
                RedisApi.lpush(key, ipport.toJSONString());
            }
        } else {
            //所有的都是第一次启动
            RedisApi.lpush(key, ipport.toJSONString());
        }
    }

    public List<String> getRegistry(String id, ApplicationContext application) {
        try {
            Registry registry = application.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            if (RedisApi.exists(id)) {
                //拿key对应的list
                return RedisApi.lrange(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
