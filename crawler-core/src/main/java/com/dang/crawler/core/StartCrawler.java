package com.dang.crawler.core;

import com.dang.crawler.core.control.serivce.JobControl;
import com.dang.crawler.core.control.serivce.master.MasterControl;
import com.dang.crawler.core.serivce.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by duang on 2017/5/1.
 */
public class StartCrawler {
    private static Logger logger = LoggerFactory.getLogger(StartCrawler.class);
    public static void main(String []args) throws InterruptedException {
        logger.info("StartCrawler>>start1");
        ApplicationContext.debug = false;
        ApplicationContext.isColony = false;
        MasterControl masterControl = (MasterControl) ApplicationContext.getBean("masterControl");
        Thread master = new Thread(masterControl);
        master.setName("MasterControl");
        master.start();
        JobControl jobControl = (JobControl) ApplicationContext.getBean("jobControl");
        Thread thread = new Thread(jobControl);
        thread.setName("JobControl");
        thread.start();
        logger.info("StartCrawler<<end");
    }
}
