package com.dynamic.orderflowapp.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContext {

    private static final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(HibernateConfig.class);

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }
}