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
import java.util.List;
import java.util.Queue;
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
    private Queue<JobCrawler> cacheQueue = new ArrayDeque<JobCrawler>(cacheSize);
    private int maxThread = PropertiesUtils.getInt("taskWorkControl.maxThread");
    private int threadCount = 0;
    ExecutorService executorService = Executors.newCachedThreadPool() ;
    //private Lock lock = new ReentrantLock();
    public TaskWorkControl(){
        //executorService.
    }
    public synchronized boolean addCrawler(Job job, Crawler crawler){
        if(cacheQueue.size()>=cacheSize){
            return false;
        }
        boolean result = cacheQueue.offer(new JobCrawler(job, crawler));
        if(threadCount<maxThread){
            TaskWork taskWork = new TaskWork(this);
            executorService.execute(taskWork);
            threadCount++;
        }
        ApplicationContext.jobCounter.update(job, JobCounter.Name.thread.getName(),1);//-------------crawler------------
        return result;
    }
    public synchronized JobCrawler consume(){
        return cacheQueue.poll();
    }
    public Script getScript(JobCrawler jobCrawler){
        return ApplicationContext.scriptCache.get(jobCrawler);
    }

    public void workSuccess(JobCrawler jobCrawler, List<Task> result) {
        if(result!=null&&result.size()>0) {
            for (Task task : result) {
                List<Crawler> crawlerList = task.getCrawlerList();
                for(int i=0;i<crawlerList.size() ;i++) {
                    Crawler crawler = crawlerList.get(i);
                    if(!check(jobCrawler,crawler)){
                        crawlerList.remove(i--);
                    }
                }
                ApplicationContext.crawlerButler.putAll(jobCrawler.getJob(), task.getCrawlerList());
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.crawler.getName(),task.getCrawlerList().size());
                ApplicationContext.jobCounter.update(jobCrawler.getJob(), JobCounter.Name.taskToDo.getName(task.getTaskName()),task.getCrawlerList().size());
            }
        }
        //TODO
    }


    public synchronized int threadEnd(){
        return threadCount--;
    }
    private boolean check(JobCrawler jobCrawler, Crawler crawler) {
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

    public void workfail(JobCrawler jobCrawler) {
        //TODO
    }
    //////////////////////////////////////////////

    public synchronized int freeSize() {
        return cacheSize-cacheQueue.size();
    }

}
