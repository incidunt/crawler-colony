package com.dang.crawler.core.control.serivce.master;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.control.serivce.JobControl;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.mysql.dao.CrawlerJobMapper;
import com.dang.crawler.resources.mysql.dao.CrawlerLogMapper;
import com.dang.crawler.resources.mysql.dao.JobTaskMapper;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.mysql.model.CrawlerLog;
import com.dang.crawler.resources.mysql.model.JobTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dang on 17-5-11.
 * job调度  参考crawler_job表启动相关job
 */
@Service
public class JobTimerThread implements Runnable{
    private static Logger log = LoggerFactory.getLogger(JobTimerThread.class);
    @Resource
    private CrawlerJobMapper crawlerJobMapper;
    @Resource
    private JobTaskMapper jobTaskMapper;
    @Resource
    private CrawlerLogMapper crawlerLogMapper;
    @Override
    public void run() {
        while (true) {
            CrawlerJob crawlerJob = new CrawlerJob();
            crawlerJob.setStatus(CrawlerJob.Status.standby.getName());
            crawlerJob.setNextStartDate(new Date());
            List<CrawlerJob> standbyList = crawlerJobMapper.list(crawlerJob);
            if(standbyList!=null&&standbyList.size()>0){
                endPrevious(standbyList);
                startJobList(standbyList);
            }
            crawlerJob.setStatus(CrawlerJob.Status.run.getName());
            List<CrawlerJob> runList = crawlerJobMapper.list(crawlerJob);
            if(runList!=null&&runList.size()>0){
                endPrevious(runList);
                startJobList(runList);
            }
            crawlerJob.setStatus(CrawlerJob.Status.stop.getName());
            crawlerJob.setNextStartDate(null);
            List<CrawlerJob> stopList = crawlerJobMapper.list(crawlerJob);
            if(stopList!=null&&stopList.size()>0){
                for(CrawlerJob stopJob :stopList){
                    Job job = new Job(stopJob,getMaxFlag(stopJob));
                    ApplicationContext.jobNotice.remove(job);
                    stopJob.setStatus(CrawlerJob.Status.stopped.getName());
                    crawlerJobMapper.update(stopJob);
                    log.info("stopJob>>>"+stopJob.getJobId());
                }
            }
            crawlerJob.setStatus(CrawlerJob.Status.kill.getName());
            crawlerJob.setNextStartDate(null);
            List<CrawlerJob> killList = crawlerJobMapper.list(crawlerJob);
            if(killList!=null&&killList.size()>0){
                for(CrawlerJob killJob :killList){
                    Job job = new Job(killJob,getMaxFlag(killJob));
                    ApplicationContext.jobNotice.remove(job);
                    ApplicationContext.crawlerButler.remove(job);
                    ApplicationContext.jobCounter.remove(job);
                    killJob.setStatus(CrawlerJob.Status.standby.getName());
                    crawlerJobMapper.update(killJob);
                    log.info("killJob>>>"+killJob.getJobId());
                }
            }
            crawlerJob.setStatus(CrawlerJob.Status.goOn.getName());
            List<CrawlerJob> goOnList = crawlerJobMapper.list(crawlerJob);
            for(CrawlerJob goOn:goOnList){
                Job job = new Job(goOn,getMaxFlag(goOn));
                ApplicationContext.jobNotice.put(job,job);
                goOn.setStatus(CrawlerJob.Status.run.getName());
                crawlerJobMapper.update(goOn);
                log.info("goOnJob>>>"+goOn.getJobId());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void endPrevious(List<CrawlerJob> list) {
        for(CrawlerJob crawlerJob :list) {
            Job job = new Job(crawlerJob,getMaxFlag(crawlerJob));
            JobControl.jobFinish(job);
        }
    }
    private long getMaxFlag(CrawlerJob crawlerJob){
        long flag = 0L;
        if(crawlerLogMapper.getMaxFlag(crawlerJob.getJobId())!=null) {
            flag = crawlerLogMapper.getMaxFlag(crawlerJob.getJobId());
        }
        return flag;
    }

    private void startJobList(List<CrawlerJob> list) {
        for(CrawlerJob crawlerJob :list){
            log.info("startJob>>>"+crawlerJob.getJobId());
            crawlerJob.setNextStartDate(new Date(System.currentTimeMillis()+crawlerJob.getPeriod()));
            crawlerJob.setStatus(CrawlerJob.Status.run.getName());
            crawlerJobMapper.update(crawlerJob);
            Job job = new Job(crawlerJob,0);
            job.setFlag(new Date().getTime());
            Crawler crawler = new Crawler();
            crawler.setTaskName("Init");
            ApplicationContext.crawlerButler.put(job,crawler);
            ApplicationContext.jobCounter.update(job, JobCounter.Name.crawler.getName(),1);
            ApplicationContext.jobCounter.update(job, JobCounter.Name.taskToDo.getName("Init"),1);
            ApplicationContext.jobNotice.put(job,job);
            createLog(job);
        }
    }

    private void createLog(Job job) {
        JobTask jobTask = new JobTask();
        jobTask.setJobId(job.getJobId());
        List<JobTask> taskList = jobTaskMapper.select(jobTask);
        for(JobTask t :taskList){
            CrawlerLog crawlerLog = new CrawlerLog();
            crawlerLog.setJobId(job.getJobId());
            crawlerLog.setTaskName(t.getTaskName());
            crawlerLog.setFlag(job.getFlag());
            crawlerLogMapper.insert(crawlerLog);
        }
    }
}
