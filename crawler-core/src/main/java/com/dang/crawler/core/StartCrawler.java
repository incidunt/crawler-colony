package com.dang.crawler.core;

import com.dang.crawler.core.control.serivce.JobControl;
import com.dang.crawler.core.control.serivce.master.MasterControl;
import com.dang.crawler.core.serivce.ApplicationContext;
/**
 * Created by duang on 2017/5/1.
 */
public class StartCrawler {
    public static void main(String []args){
        ApplicationContext.debug = false;
        MasterControl masterControl = (MasterControl) ApplicationContext.getBean("masterControl");
        Thread master = new Thread(masterControl);
        master.setName("MasterControl");
        master.start();
        JobControl jobControl = (JobControl) ApplicationContext.getBean("jobControl");
        Thread thread = new Thread(jobControl);
        thread.setName("JobControl");
        thread.start();
    }
}
