package com.dang.crawler.core.control.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.serivce.ApplicationContext;
import org.apache.commons.lang3.StringUtils;
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
                consume(jobCrawler);
            } catch (Exception e) {
                log.info("消费失败<<<" + jobCrawler.getJob().getJobId() + "<<"
                        + jobCrawler.getCrawler().getTaskName() + "<" + jobCrawler.getCrawler().toString(), e);
                consumeError(jobCrawler);
            } finally {
                consumeClean(jobCrawler);
            }
        }
        control.threadEnd();
    }

    /**
     * 消费
     * @param jobCrawler 消费的内容
     * @throws Exception
     */
    private void consume(JobCrawler jobCrawler) throws Exception {
        Script script = control.getScript(jobCrawler);
        Crawler crawler = jobCrawler.getCrawler();
        List<Task> result = script.work(crawler.clone(), jobCrawler.getJob());
        consumeSuccess(jobCrawler, result);
    }

    /**
     * 消费成功
     */
    public void consumeSuccess(JobCrawler jobCrawler, List<Task> result) {
        if(result!=null&&result.size()>0) {
            for (Task task : result) {
                List<Crawler> crawlerList = task.getCrawlerList();
                for(int i=0;i<crawlerList.size() ;i++) {
                    Crawler crawler = crawlerList.get(i);
                    if(!checkCorrect(jobCrawler,crawler)){
                        crawlerList.remove(i--);
                    }
                }
                int addsize = ApplicationContext.crawlerButler.putAll(jobCrawler.getJob(), task.getCrawlerList());
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.crawler.getName(),addsize);
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskToDo.getName(task.getTaskName()),addsize);
            }
        }
        ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskSuccess.getName(jobCrawler.getCrawler().getTaskName()), 1);
    }
    /**
     * 消费失败
     */
    private void consumeError(JobCrawler jobCrawler) {
        Integer reRun = (Integer) jobCrawler.getCrawler().get("#ReRun");
        if(reRun==null){
            reRun=0;
        }
        if(reRun < 3){
            jobCrawler.getCrawler().put("#ReRun",reRun+1);
            boolean add = ApplicationContext.crawlerButler.put(jobCrawler.getJob(),jobCrawler.getCrawler());
            if(add) {
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.crawler.getName(), 1);
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskToDo.getName(jobCrawler.getCrawler().getTaskName()), 1);
            }else {
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskFail.getName(jobCrawler.getCrawler().getTaskName()), 1);
            }
        }else {
            ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskFail.getName(jobCrawler.getCrawler().getTaskName()), 1);
        }
    }
    /**
     * 清理环境  更改计数器
     */
    private void consumeClean(JobCrawler jobCrawler){
        ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.thread.getName(), -1);
        int crawlerCount = ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.crawler.getName(), -1);
        ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskToDo.getName(jobCrawler.getCrawler().getTaskName()), -1);
        if (crawlerCount==0){
            finishJob(jobCrawler);
        }
    }
    /**
     * 结束job
     */
    private void finishJob(JobCrawler jobCrawler) {
        try {
            JobControl.jobFinish(jobCrawler.getJob());//job结束
        } catch (Exception e){
            log.error("结束job Error"+jobCrawler.getJob());
        }
    }
    /**
     * 验证和修正产品
     */
    private boolean checkCorrect(JobCrawler jobCrawler, Crawler crawler) {
        if(StringUtils.isEmpty(crawler.getUrl())||crawler.getUrl().length()<5){
            return false;
        }
        if(crawler.getUrl().contains("htt")){
            return true;
        }else {
            String host =jobCrawler.getCrawler().getUrl();
            if(host.substring(8).contains("/")) {
                host = host.substring(0,host.substring(8).indexOf("/")+8);
            }else if(host.contains("?")){
                host = host.substring(0, host.indexOf("?"));
            }
            String url = crawler.getUrl();
            if(url.substring(0,1).equals("/")){
                crawler.setUrl(host+url);
            }else {
                crawler.setUrl(host+"/"+url);
            }
            return true;
        }
    }
}
