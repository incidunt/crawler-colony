package com.dang.crawler.core.control.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.serivce.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by dang on 17-5-10.
 */
public class TaskWork implements Runnable{
    private static Logger log = LoggerFactory.getLogger(TaskWork.class);
    private TaskWorkControl control;
    public TaskWork(TaskWorkControl taskWorkControl){
        this.control = taskWorkControl;
    }
    @Override
    public void run() {
        JobCrawler jobCrawler = null;
        while ((jobCrawler = control.consume())!=null){
            log.info("开始消费>>>"+jobCrawler.getCrawler().getJobId()+">>"+jobCrawler.getCrawler().getTaskName());
            Script script = control.getScript(jobCrawler);
            if(script!=null){
                try {
                    Crawler crawler = jobCrawler.getCrawler();
                    crawler.setJobId(jobCrawler.getJob().getJobId());
                    List<Task> result = script.work(crawler.clone());
                    control.workSuccess(jobCrawler,result);
                } catch (Exception e) {
                    log.info("消费失败<<<"+jobCrawler.getCrawler().getJobId()+"<<"
                            +jobCrawler.getCrawler().getTaskName()+"<"+jobCrawler.getCrawler().toString(),e);
                    control.workfail(jobCrawler);
                }finally {
                    ApplicationContext.jobCounter.update(jobCrawler.getJob().getJobId(), JobCounter.Attribute.crawler.getName(),-1);//-------------crawler------------
                    ApplicationContext.jobCounter.update(jobCrawler.getJob().getJobId(), JobCounter.Attribute.thread.getName(),-1);//-------------crawler------------
                }
            }
        }
    }
}
