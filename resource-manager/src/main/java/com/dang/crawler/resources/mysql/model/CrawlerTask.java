package com.dang.crawler.resources.mysql.model;

import java.util.Date;

/**
 * Created by mi on 2017/5/3.
 */
public class CrawlerTask {
    private String taskId;
    private String name;
    private long period;
    private int projectId;
    private int sourceId;
    private Date createDate;
    private Date nextStartDate;
    private char status;

    //////////////////////////////////////////////////
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public Date getNextStartDate() {
        return nextStartDate;
    }

    public void setNextStartDate(Date nextStartDate) {
        this.nextStartDate = nextStartDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String toString() {
        return "CrawlerTask{" +
                "taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", period=" + period +
                ", projectId=" + projectId +
                ", sourceId=" + sourceId +
                ", createDate=" + createDate +
                ", nextStartDate=" + nextStartDate +
                ", status=" + status +
                '}';
    }
}
