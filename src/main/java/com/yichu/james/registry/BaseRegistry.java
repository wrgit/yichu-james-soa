package com.yichu.james.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

public interface BaseRegistry {

    public boolean registry(String param, ApplicationContext applicationContext);

    List<String> getRegistry(String id, ApplicationContext application);
}
