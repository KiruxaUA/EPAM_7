package ua.epam6.IOCRUD.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TimerBeanPostProcessor implements BeanPostProcessor {
    private static Map<String, String> methodsThatHaveAnnotationTime = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class type = bean.getClass();
        Method[] methods = type.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if(methods[i].isAnnotationPresent(Timed.class)){
                methodsThatHaveAnnotationTime.put(methods[i].getName(), beanName);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(methodsThatHaveAnnotationTime.containsValue(beanName)){
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                    getInvocationHandler(bean));
        }
        return bean;
    }

    private InvocationHandler getInvocationHandler(Object bean){
        return (object, method, args) -> {
            if(methodsThatHaveAnnotationTime.containsKey(method.getName())){
                long before = System.nanoTime();
                Object invoke = method.invoke(bean, args);
                long after = System.nanoTime();
                System.out.println("Method " + method.getName() + " has been working for: " + (after - before));
                return invoke;
            }
            return method.invoke(bean, args);
        };
    }
}