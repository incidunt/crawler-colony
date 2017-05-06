package com.dang.crawler.resources.mysql.model;

import jdk.nashorn.internal.scripts.JO;

import java.util.Date;

/**
 * Created by mi on 2017/5/3.
 */
public class JobCode {
    private String taskId;
    private String name;
    private String code;
    private byte []cls;
    private Date date;
    public JobCode(){}
    public JobCode(String taskId,String name){
        this.taskId = taskId;
        this.name = name;
    }
    ///////////////////////////////////////////


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getCls() {
        return cls;
    }

    public void setCls(byte[] cls) {
        this.cls = cls;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
