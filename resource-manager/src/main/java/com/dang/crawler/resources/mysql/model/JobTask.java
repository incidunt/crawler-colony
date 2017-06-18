package com.dang.crawler.resources.mysql.model;


import java.util.Date;

/**
 * Created by mi on 2017/5/3.
 */
public class JobTask {
    private int id;
    private String jobId;
    private String taskName;
    private String code;
    private byte []bytes;
    private Date date;
    public JobTask(){}
    public JobTask(String id,String name){
        this.jobId = id;
        this.taskName = name;
    }
    ///////////////////////////////////////////


    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
