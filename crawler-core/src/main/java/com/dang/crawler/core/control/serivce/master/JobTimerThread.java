package com.dang.crawler.core.control.serivce.master;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.mysql.dao.CrawlerJobMapper;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dang on 17-5-11.
 */
@Service
public class JobTimerThread implements Runnable{
    private static Logger log = LoggerFactory.getLogger(JobTimerThread.class);
    @Resource
    private CrawlerJobMapper crawlerJobMapper;

    @Override
    public void run() {
        while (true) {
            CrawlerJob crawlerJob = new CrawlerJob();
            crawlerJob.setStatus(CrawlerJob.Status.standby.getName());
            crawlerJob.setNextStartDate(new Date());
            List<CrawlerJob> list = crawlerJobMapper.list(crawlerJob);
            if(list!=null){
                startJobList(list);
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startJobList(List<CrawlerJob> list) {
        for(CrawlerJob crawlerJob :list){
            log.info("startJob>>>"+crawlerJob.getJobId());
            crawlerJob.setNextStartDate(new Date(System.currentTimeMillis()+crawlerJob.getPeriod()));
            crawlerJobMapper.update(crawlerJob);
            Job job = new Job();
            job.setJobId(crawlerJob.getJobId());
            job.setMaxThread(crawlerJob.getMaxThread());
            job.setPriority(crawlerJob.getPriority());
            job.setFlag(new Date().getTime());
            Crawler crawler = new Crawler();
            crawler.setJobId(crawlerJob.getJobId());
            crawler.setTaskName("Init");
            ApplicationContext.crawlerButler().put(job,crawler);
            ApplicationContext.jobNotice().put(job,job);
        }
    }
}
