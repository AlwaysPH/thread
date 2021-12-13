package com.ph.thread.activeObject.resue;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/***
 * activeObject模式proxy参与者的可复用实现
 */
public abstract class ActiveObjectProxy {

    private static class DispatchInvocationHandler implements InvocationHandler{

        private final Object delegate;

        private final ExecutorService scheduler;

        public DispatchInvocationHandler(Object delegate, ExecutorService scheduler) {
            this.delegate = delegate;
            this.scheduler = scheduler;
        }

        private String makeDelegateMethodName(final Method method, final Object[] arg){
            String name = method.getName();
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            return name;
        }

        @Override
        public Object invoke(final Object proxy, Method method, final Object[] args) throws Throwable {
            Object returnValue = null;
            final Object delegate = this.delegate;
            final Method delegateMethod;
            //若拦截的请求方法是异步方法，则进行转发至doXX方法
            if(Future.class.isAssignableFrom(method.getReturnType())){
                delegateMethod = delegate.getClass().getMethod(makeDelegateMethodName(method, args), method.getParameterTypes());
                final ExecutorService scheduler = this.scheduler;

                final Callable<Object> methodRequest = new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return delegateMethod.invoke(delegate, args);
                    }
                };
                Future<Object> future = scheduler.submit(methodRequest);
                returnValue = future.get();
            }else {
                //若拦截到的方法不是异步方法，则直接转发
                delegateMethod = delegate.getClass().getMethod(method.getName(), method.getParameterTypes());
                returnValue = delegateMethod.invoke(delegate, args);
            }
            return returnValue;
        }
    }

    /***
     * 生成一个实现指定接口的active object proxy实例
     * 对interf锁定义的异步方法的调用会被转发到servant的相应的doxxx方法上
     * @param interf 要实现Active Object接口
     * @param servant   Active Object的servant实例
     * @param scheduler Active Object的scheduler参与者实例
     * @param <T>   Active Object的proxy参与者实例
     * @return
     */
    public static <T> T newInstance(Class<T> interf, Object servant, ExecutorService scheduler){
        T f = (T) Proxy.newProxyInstance(interf.getClassLoader(), new Class[]{interf}, new DispatchInvocationHandler(servant, scheduler));
        return f;
    }
}
