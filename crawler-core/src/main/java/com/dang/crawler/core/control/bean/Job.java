package com.dang.crawler.core.control.bean;

import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.mysql.model.CrawlerJob;

/**
 * Created by dang on 17-5-10.
 */
public class Job {
    public String jobId;
    private int priority;
    private int maxThread;
    private long flag;
    private int projectId;
    //private int CrawlerCounter;
    //private int workThread;

    public Job(){}
    public Job(CrawlerJob crawlerJob,long flag){
        this.jobId = crawlerJob.getJobId();
        this.priority = crawlerJob.getPriority();
        this.maxThread = crawlerJob.getMaxThread();
        this.flag = flag;
        projectId = crawlerJob.getProjectId();
    }
    //////////////////////////////////////
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMaxThread() {
        return maxThread;
    }

    public void setMaxThread(int maxThread) {
        this.maxThread = maxThread;
    }

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    public int getWorkThread() {
        return ApplicationContext.jobCounter.get(this, JobCounter.Name.thread.getName());
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
