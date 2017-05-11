package com.dang.crawler.core.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.impl.LonelyCrawlerButler;
import com.dang.crawler.core.control.impl.LonelyJobNotice;
import com.dang.crawler.core.control.norm.Butler;
import com.dang.crawler.core.control.norm.Cache;
import com.dang.crawler.core.control.norm.Notice;
import com.dang.crawler.core.script.norm.Script;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by dang on 17-5-11.
 */
public class ApplicationContext {
    private static final ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-*.xml"});
    public static Cache<JobCrawler,Script> scriptCache = (Cache<JobCrawler, Script>) getBean("nativeScriptCache");
    public static boolean debug = true;
    public static Butler<Job,Crawler> crawlerButler = new LonelyCrawlerButler();
    public static Notice<Job,Job> jobNotice = new LonelyJobNotice();

    public static Object getBean(String beanName){
        return classPathXmlApplicationContext.getBean(beanName);
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    public static ClassPathXmlApplicationContext getClassPathXmlApplicationContext() {
        return classPathXmlApplicationContext;
    }

    public static Cache<JobCrawler, Script> scriptCache() {
        return scriptCache;
    }



    public static Butler<Job, Crawler> crawlerButler() {
        return crawlerButler;
    }


    public static Notice<Job, Job> jobNotice() {
        return jobNotice;
    }

}
