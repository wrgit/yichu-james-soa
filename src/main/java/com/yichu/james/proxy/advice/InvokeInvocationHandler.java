package com.yichu.james.proxy.advice;

import com.yichu.james.configBean.Reference;
import com.yichu.james.invoke.Invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvokeInvocationHandler implements InvocationHandler {

    private Invoke invoke;
    private Reference reference;

    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在这个invoke里面最终要调用多个远程的privoder
        System.out.println("已经取到代理实例，InvokeInvocationHandler的invoke方法被调用");

        return null;
    }
}
