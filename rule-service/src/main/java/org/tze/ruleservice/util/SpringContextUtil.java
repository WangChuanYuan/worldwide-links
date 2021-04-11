package org.tze.ruleservice.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        @SuppressWarnings("rawtypes")
        Map beanMaps = applicationContext.getBeansOfType(clazz);
        if (beanMaps != null && !beanMaps.isEmpty()) {
            return (T) beanMaps.values().iterator().next();
        } else {
            return null;
        }
    }

    /**
     * 根据配置的class信息得到其在spring中的名字，如org.tze.ruleservice.action.impl.MailActionImpl会返回mailAction。
     * 采用约定大于配置的原则，如果包含Impl，则去掉Impl，并将首字母小写，如果不包含Impl，直接将首字母小写
     */
    public static String getBeanNameOfClazzAsConvention(String clazzName) {
        int index = clazzName.lastIndexOf(".");
        String name = clazzName.substring(index + 1);
        if (name.endsWith("Impl")) {
            name = name.replace("Impl", "");
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
            return name;
        } else {
            return clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1);
        }
    }

}
