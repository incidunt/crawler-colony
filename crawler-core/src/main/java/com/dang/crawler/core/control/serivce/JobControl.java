package com.dang.crawler.core.control.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.Butler;
import com.dang.crawler.core.control.norm.Notice;
import com.dang.crawler.core.serivce.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Created by mi on 2017/5/9.
 */
@Service
public class JobControl implements Runnable {
    private static Logger log = LoggerFactory.getLogger(JobControl.class);
    @Resource
    private TaskWorkControl taskWorkControl;
    //private Notice<Job, Job> jobNotice = ApplicationContext.jobNotice();
    public JobControl(){
    }
    @Override
    public void run() {
        while (true){
            Job job = ApplicationContext.jobNotice().notice();
            if(taskWorkControl.freeSize()>0&&job!=null){
                load(job);
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.info("sleep error"+e);
                }
            }
        }
    }

    private void load(Job job) {
        if(job==null)return;
        for(int i =0;i<job.getPriority()&&taskWorkControl.freeSize()>0;i++) {
            Crawler crawler = ApplicationContext.crawlerButler().get(job);
            if(crawler!=null) {
                taskWorkControl.addCrawler(job,crawler);
            }else {
                return;
            }
        }
    }
}
