package com.dang.crawler.core;

import com.dang.crawler.core.control.serivce.JobControl;
import com.dang.crawler.core.control.serivce.master.MasterControl;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.core.serivce.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Crawler启动类 会对全局变量进行设置 以及启动MasterControl JobControl SocketService
 */
public class StartCrawler {
    private static Logger logger = LoggerFactory.getLogger(StartCrawler.class);
    public static void main(String []args)  {
        logger.info("StartCrawler>>start");
        System.setProperty ("jsse.enableSNIExtension", "false");
        ApplicationContext.debug = true;
        ApplicationContext.isColony = false;
        MasterControl masterControl = (MasterControl) ApplicationContext.getBean("masterControl");
        Thread master = new Thread(masterControl);
        master.setName("MasterControl");
        master.start();
        JobControl jobControl = (JobControl) ApplicationContext.getBean("jobControl");
        Thread thread = new Thread(jobControl);
        thread.setName("JobControl");
        thread.start();
        SocketService socketService = (SocketService) ApplicationContext.getBean("socketService");
        Thread socket = new Thread(socketService);
        socket.setName("socketService");
        socket.start();
        logger.info("StartCrawler<<end");
    }
}
