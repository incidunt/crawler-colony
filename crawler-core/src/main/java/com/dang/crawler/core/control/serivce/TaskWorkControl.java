package com.dang.crawler.core.control.serivce;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.norm.Cache;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.core.script.norm.Task;
import com.dang.crawler.core.serivce.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dang on 17-5-10.
 */
@Service
public class TaskWorkControl {
    //public static Cache<Crawler,Script> scriptCache = ApplicationContext.scriptCache();
    private int cacheSize = 1000;
    private Queue<JobCrawler> cacheQueue = new ArrayDeque<JobCrawler>(cacheSize);
    private int maxThread = 5;
    private int threadCount = 0;
    ExecutorService executorService = Executors.newFixedThreadPool(maxThread) ;
    //private Lock lock = new ReentrantLock();
    public TaskWorkControl(){}
    public synchronized boolean addCrawler(Job job, Crawler crawler){
        if(cacheQueue.size()>=cacheSize){
            return false;
        }
        boolean result = cacheQueue.offer(new JobCrawler(job, crawler));
        if(threadCount<maxThread){
            executorService.execute(new TaskWork(this));
            threadCount++;
        }
        return result;
    }
    public synchronized JobCrawler consume(){
        return cacheQueue.poll();
    }
    public Script getScript(JobCrawler jobCrawler){
        return ApplicationContext.scriptCache().get(jobCrawler);
    }

    public void workSuccess(JobCrawler jobCrawler, List<Task> result) {
        if(result!=null) {
            for (Task task : result) {
                ApplicationContext.crawlerButler().putAll(jobCrawler.getJob(), task.getCrawlerList());
            }
        }
        //TODO
    }

    public void workfail(JobCrawler jobCrawler) {
        //TODO
    }
    //////////////////////////////////////////////

    public synchronized int freeSize() {
        return cacheSize-cacheQueue.size();
    }

}
