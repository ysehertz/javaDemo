package com.fuchen;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName: StringReflection
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/13
 */
@Slf4j
public class StringReflection {
    public String invokeValueOf(Integer digit){
        log.info("测试能否通过反射调用Spring的valueOf方法");
        try {
            Class clazz = Class.forName("java.lang.String");
            Method method = clazz.getMethod("valueOf", int.class);
            method.setAccessible(true);
            return (String) method.invoke(null, digit);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException  e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


