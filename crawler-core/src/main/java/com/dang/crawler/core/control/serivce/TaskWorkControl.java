package com.dang.crawler.core.control.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dang on 17-5-10.
 * task 管理模块  负责taskWork的线程池  、需要消费的资源和消费完毕的后续处理
 */
@Service
public class TaskWorkControl {
    private static Logger log = LoggerFactory.getLogger(TaskWorkControl.class);
    //public static Cache<Crawler,Script> scriptCache = ApplicationContext.scriptCache();
    private int cacheSize = 1000;
    //private Queue<JobCrawler> cacheQueue = new ConcurrentLinkedQueue<JobCrawler>();
    // 容器集合
    private ConcurrentLinkedQueue<JobCrawler> taskQueue = new ConcurrentLinkedQueue<JobCrawler>();
    private int maxThread = PropertiesUtils.getInt("taskWorkControl.maxThread");
    private int threadCount = 0;
    ExecutorService executorService = Executors.newCachedThreadPool() ;
    //private Lock lock = new ReentrantLock();
    public TaskWorkControl(){
        //executorService.
    }
    public synchronized boolean addCrawler(Job job, Crawler crawler){
        if(taskQueue.size()>=cacheSize){
            return false;
        }
        boolean result = taskQueue.offer(new JobCrawler(job, crawler));
        if(threadCount<maxThread){
            TaskWork taskWork = new TaskWork(this);
            executorService.execute(taskWork);
            threadCount++;
        }
        ApplicationContext.jobCounter.update(job, JobCounter.Name.thread.getName(),1);//-------------crawler------------
        return result;
    }
    public synchronized JobCrawler consume(){
        return taskQueue.poll();
    }
    public Script getScript(JobCrawler jobCrawler){
        return ApplicationContext.scriptCache.get(jobCrawler);
    }



    public synchronized int threadEnd(){
        return threadCount--;
    }


    //////////////////////////////////////////////

    public synchronized int freeSize() {
        return cacheSize-taskQueue.size();
    }

}
