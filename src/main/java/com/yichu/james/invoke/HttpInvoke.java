package com.yichu.james.invoke;

public class HttpInvoke implements Invoke {

    public String invoke(Invocation invocation) {
        System.out.println("httpInvoke 被调用");
        return null;
    }
}
