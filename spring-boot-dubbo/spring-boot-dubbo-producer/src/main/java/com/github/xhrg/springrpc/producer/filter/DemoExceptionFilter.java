package com.github.xhrg.springrpc.producer.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * 可以参考：TimeoutFilter.java继承了2个接口
 */
@Activate(group = CommonConstants.PROVIDER)
public class DemoExceptionFilter implements Filter, Filter.Listener {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    /**
     * 根据是否有异常进行判断，Result的setValue就是return给调用者的信息
     */
    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        System.out.println("onResponse");
        System.out.println(invoker);
        System.out.println(invocation);
        if (appResponse.hasException() && GenericService.class != invoker.getInterface()) {
            try {
                RpcContext rpcContext = RpcContext.getContext();
                Class<?> rpcInterface = invoker.getInterface();
                String methodName = invocation.getMethodName();
                Throwable exception = appResponse.getException();
                System.out.println(rpcContext);
                System.out.println(rpcInterface);
                System.out.println(methodName);
                System.out.println(exception);
            } catch (Throwable e) {
                System.out.println(e);
            }
        }
    }

    /**
     * 这个方法打印下基本日志即可
     */
    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
        System.out.println("onError");
        System.out.println(t);
        System.out.println(invoker);
        System.out.println(invocation);
    }
}