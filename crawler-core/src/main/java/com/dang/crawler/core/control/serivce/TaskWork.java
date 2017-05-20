package com.dang.crawler.core.control.serivce;

import com.dang.crawler.core.control.bean.Crawler;
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
 * 负责crawler的消费 并更新job的相关计数器用以统计
 */
public class TaskWork implements Runnable {
    private static Logger log = LoggerFactory.getLogger(TaskWork.class);
    private TaskWorkControl control;

    public TaskWork(TaskWorkControl taskWorkControl) {
        this.control = taskWorkControl;
    }

    @Override
    public void run() {
        JobCrawler jobCrawler = null;
        while ((jobCrawler = control.consume()) != null) {
            log.info("开始消费>>>" + jobCrawler.getJob().getJobId() + ">>" + jobCrawler.getCrawler().getTaskName() + ">" + jobCrawler.getCrawler().getUrl());
            try {
                Script script = control.getScript(jobCrawler);
                Crawler crawler = jobCrawler.getCrawler();
                List<Task> result = script.work(crawler.clone(), jobCrawler.getJob());
                control.workSuccess(jobCrawler, result);
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskSuccess.getName(jobCrawler.getCrawler().getTaskName()), 1);
            } catch (Exception e) {
                log.info("消费失败<<<" + jobCrawler.getJob().getJobId() + "<<"
                        + jobCrawler.getCrawler().getTaskName() + "<" + jobCrawler.getCrawler().toString(), e);
                control.workfail(jobCrawler);
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskFail.getName(jobCrawler.getCrawler().getTaskName()), 1);
            } finally {
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.thread.getName(), -1);
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.crawler.getName(), -1);
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskToDo.getName(jobCrawler.getCrawler().getTaskName()), -1);
            }
            try {
                if (0 == ApplicationContext.jobCounter.get(jobCrawler.getJob(), JobCounter.Name.crawler.getName())) {
                    JobControl.jobFinish(jobCrawler.getJob());//job结束
                }
            } catch (Exception e){
                log.error("结束job Error"+jobCrawler.getJob());
            }

        }
        control.threadEnd();
    }
}
