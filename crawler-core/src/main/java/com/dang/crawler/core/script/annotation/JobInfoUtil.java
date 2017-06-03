package com.dang.crawler.core.script.annotation;

/**
 * Created by mi on 2017/5/31.
 */
public class JobInfoUtil {
    public static JobInfo getFruitInfo(Class<?> clazz) {
        if(clazz.isAnnotationPresent(JobInfo.class)){
            JobInfo jobInfo = clazz.getAnnotation(JobInfo.class);
            return jobInfo;
        }
        return null;
    }
}
