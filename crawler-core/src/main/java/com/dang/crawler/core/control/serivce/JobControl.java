package com.dang.crawler.core.control.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.mysql.dao.CrawlerJobMapper;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * Created by dang on 2017/5/9.
 * 负责读取正在运行的job信息  并且拉去该job的crawler到本机的消费池中
 */
@Service
public class JobControl implements Runnable {
    private static Logger log = LoggerFactory.getLogger(JobControl.class);
    private static CrawlerJobMapper crawlerJobMapper;
    @Resource
    private TaskWorkControl taskWorkControl;
    //private Notice<Job, Job> jobNotice = ApplicationContext.jobNotice();
    private int count = 0;
    public JobControl(){
    }
    @Override
    public void run() {
        while (true){
            try {
                Job job = ApplicationContext.jobNotice.notice();
                if (taskWorkControl.freeSize() > 0 && job != null && job.getWorkThread() < job.getMaxThread()) {
                    load(job);
                } else {
                    Thread.sleep(1000);
                    if((count++)%10==0) {
                        log.info("no job");
                    }
                }
            }catch (Exception e){
                log.error("",e);
            }
        }
    }

    private void load(Job job) {
        if(job==null)return;
        for(int i =0;i<job.getPriority()&&taskWorkControl.freeSize()>0&&job.getWorkThread()<job.getMaxThread();i++) {
            Crawler crawler = ApplicationContext.crawlerButler.get(job);
            if(crawler!=null) {
                taskWorkControl.addCrawler(job,crawler);
            }else {
                return;
            }
        }
    }
    public static void jobFinish(Job job){
        log.info("jobFinish<<"+job.getJobId()+"<"+job.getFlag());
        ApplicationContext.jobCounter.flush();
        ApplicationContext.jobNotice.remove(job);
        ApplicationContext.crawlerButler.remove(job);
        CrawlerJob crawlerJob = new CrawlerJob();
        crawlerJob.setJobId(job.getJobId());
        crawlerJob.setStatus(CrawlerJob.Status.standby.getName());
        if(crawlerJobMapper == null){
           crawlerJobMapper = (CrawlerJobMapper) ApplicationContext.getBean("crawlerJobMapper");
        }
        crawlerJobMapper.update(crawlerJob);
    }
}
