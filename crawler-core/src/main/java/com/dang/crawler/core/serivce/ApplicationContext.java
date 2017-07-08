package com.dang.crawler.core.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.impl.*;
import com.dang.crawler.core.control.norm.Butler;
import com.dang.crawler.core.control.norm.Cache;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.control.norm.Notice;
import com.dang.crawler.core.script.norm.Script;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 存放的应用程序 的全局变量
 */
public class ApplicationContext {
    private static final ClassPathXmlApplicationContext classPathXmlApplicationContext =
            new ClassPathXmlApplicationContext(new String[]{"classpath:crawler-spring-mybatis.xml"});
    public static boolean debug = false;//如果是DEBUG 用webDriver会使用谷歌浏览器
    public static boolean isColony = false;//如果是集群会使用Redis的容器实现
    public static Cache<JobCrawler,Script> scriptCache = (Cache<JobCrawler, Script>)
            getBean("nativeScriptCache");//脚本缓存
    public static Butler<Job,Crawler> crawlerButler = new LonelyCrawlerButler();//crawler存储器
    public static Notice<Job,Job> jobNotice = new LonelyJobNotice();            //job公示器
    public static JobCounter jobCounter = new LonelyJobCounter();               //job计数器
    static {
        if(isColony){
            crawlerButler = new RedisCrawlerButler();//crawler存储器
            jobNotice = new RedisJobNotice();            //job公示器
            jobCounter = new RedisJobCounter();               //job计数器
        }
    }
    public static Object getBean(String beanName){
        return classPathXmlApplicationContext.getBean(beanName);
    }
    ////////////////////////////////////////////////////////////////////////////////////////




}
