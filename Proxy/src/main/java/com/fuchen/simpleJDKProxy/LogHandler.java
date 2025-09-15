package com.fuchen.simpleJDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ClassName: LogHandler
 * Package: com.fuchen.simpleJDKProxy
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/15
 */
public class LogHandler implements InvocationHandler {
    private Object target;

    public LogHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();

        // 根据方法名定制逻辑
        if (methodName.equals("save")) {
            System.out.println("[日志] 开始执行保存操作");
        } else if (methodName.equals("delete")) {
            System.out.println("[警告] 删除操作需谨慎！");
        }

        // 统一的前置逻辑（所有方法都执行）
        System.out.println("方法调用前: " + methodName);

        // 调用目标方法
        Object result = method.invoke(target, args);

        // 统一的后置逻辑（所有方法都执行）
        System.out.println("方法调用后: " + methodName);

        return result;
    }
}
