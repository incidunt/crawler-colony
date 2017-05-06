package com.dang.crawler.resources.common;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangqiqi1 on 2016/8/3.
 */
public class ApplicationContextSingleton {
    private ApplicationContextSingleton(){

    }

    private static AbstractApplicationContext applicationContext = null;

    static {
        applicationContext = new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" });
        applicationContext.start();
    }

    public static AbstractApplicationContext getInstance(){
        return applicationContext;
    }

}
