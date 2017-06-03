package com.dang.crawler.core.script.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***
 * 实体注解接口
 */
//@Target(value = {ElementType.PACKAGE})//注解可以被添加在属性上
@Retention(RetentionPolicy.RUNTIME)
public @interface JobInfo{
    /***
     * jobID默认为空
     * @return String
     */
    String id() default "";
    String name() default "";
    long period() default 30*24*60*60*1000;
    int priority() default 10;
    int maxThread() default  10;
    String note() default "";
    int projectId() default 0;
    int sourceId() default 0;


}
