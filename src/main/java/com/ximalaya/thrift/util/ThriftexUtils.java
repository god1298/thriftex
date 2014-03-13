/*
 * 文件名称: ThriftExtUtils.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.util;

import java.util.Set;

import org.apache.thrift.TProcessor;
import org.springframework.util.ClassUtils;

/**
 * thrift-ext utility class
 * 
 * @author ted wang
 * @author gavin lu
 * @since 1.0
 */
public final class ThriftExtUtils {
    private ThriftExtUtils() {
    }

    /**
     * Get the Iface interface of implClass
     * 
     * @param implClass
     * @return
     */
    public static Class<?> getIfaceClass(Class<?> implClass) {
        @SuppressWarnings("rawtypes")
        Set<Class> clazzArr = ClassUtils.getAllInterfacesForClassAsSet(implClass);
        Class<?> ifaceClass = null;
        for (Class<?> clazz : clazzArr) {
            String simpleName = clazz.getSimpleName();
            if (simpleName.equals("Iface")) {
                Class<?> declaringClass = clazz.getDeclaringClass();
                if (declaringClass != null) {
                    ifaceClass = clazz;
                    break;
                }
            }
        }
        return ifaceClass;
    }

    /**
     * Get service class with given Iface interface
     * 
     * @param ifaceInterface
     * @return
     */
    public static Class<?> getServiceClass(Class<?> ifaceInterface) {
        return ifaceInterface.getDeclaringClass();
    }

    /**
     * Get processor class with given service class
     * 
     * @param serviceClazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Class<TProcessor> getProcessorClass(Class<?> serviceClazz) {
        Class<?>[] declaredClasses = serviceClazz.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses) {
            Class<?>[] interfaceClasses = declaredClass.getInterfaces();
            for (Class<?> interfaceClass : interfaceClasses) {
                if (interfaceClass == TProcessor.class) {
                    return (Class<TProcessor>) declaredClass;
                }
            }
        }
        return null;
    }

    public static ClassLoader getCurrentClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
        }
        if (cl == null) {
            cl = ThriftExtUtils.class.getClassLoader();
        }
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        return cl;
    }
}
