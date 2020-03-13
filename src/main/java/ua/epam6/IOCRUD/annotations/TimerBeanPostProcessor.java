package ua.epam6.IOCRUD.annotations;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TimerBeanPostProcessor implements BeanPostProcessor {
    private static Map<String, List<Method>> beanToAnnotatedMethods = new HashMap<>();
    private static List<Method> methodList = new ArrayList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class type = bean.getClass();
        Method[] methods = type.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Timed.class)) {
                methodList.add(method);
            }
        }
        beanToAnnotatedMethods.put(beanName, methodList);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<Object> proxies = new ArrayList<>();
        if(beanToAnnotatedMethods.containsValue(beanName)) {
            for (Method method : beanToAnnotatedMethods.get(beanName)) {
                proxies.add(Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(),
                        getInvocationHandler(bean)));
            }
            return proxies;
        }
        return bean;
    }

    private InvocationHandler getInvocationHandler(Object bean){
        return (object, method, args) -> {
            if(beanToAnnotatedMethods.containsKey(method.getName())){
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