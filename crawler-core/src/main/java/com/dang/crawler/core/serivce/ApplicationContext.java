package com.dang.crawler.core.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.impl.LonelyCrawlerButler;
import com.dang.crawler.core.control.impl.LonelyJobCounter;
import com.dang.crawler.core.control.impl.LonelyJobNotice;
import com.dang.crawler.core.control.norm.Butler;
import com.dang.crawler.core.control.norm.Cache;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.control.norm.Notice;
import com.dang.crawler.core.script.norm.Script;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by dang on 17-5-11.
 */
public class ApplicationContext {
    private static final ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-*.xml"});
    public static boolean debug = true;
    public static Cache<JobCrawler,Script> scriptCache = (Cache<JobCrawler, Script>) getBean("nativeScriptCache");//脚本缓存
    public static Butler<Job,Crawler> crawlerButler = new LonelyCrawlerButler();//crawler存储器
    public static Notice<Job,Job> jobNotice = new LonelyJobNotice();            //job公示器
    public static JobCounter jobCounter = new LonelyJobCounter();               //job计数器

    public static Object getBean(String beanName){
        return classPathXmlApplicationContext.getBean(beanName);
    }
    ////////////////////////////////////////////////////////////////////////////////////////




}
