package com.dang.crawler.core.control.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dang on 2017/5/9.
 */
public class Crawler implements Serializable ,Cloneable {
    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);
    private String taskName = "";
    private String url = "";
    private Map<String, String> header = new LinkedHashMap<String, String>();
    private String body = null;
    private Map<String, Object> info = new HashMap();
    @Override
    public Crawler clone(){
        try {
            return (Crawler)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new Crawler();
    }
    public Crawler putHeader(String key, String value){
        header.put(key,value);
        return this;
    }
//    @Override
//    public Crawler clone() {
//        Crawler crawler = null;
//        try {
//            crawler = (Crawler) BeanUtils.clone(this);
//        } catch (Exception e) {
//            LOG.error("Crawler克隆失败：" + e);
//        }
//        return crawler;
//    }
    ////////////////////////////////////////////////////////////////////

    public static Logger getLOG() {
        return LOG;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public void appendBody(String body) {
        if(this.body ==null){
            this.body = body;
        }
        this.body += body;
    }
    public void appendUrl(String url) {
        this.url += url;
    }

    public Object get(String key) {
        return info.get(key);
    }
    public void put(String key,Object value) {
        this.info.put(key,value);
    }



    @Override
    public String toString() {
        return "Crawler{" +
                ", taskName='" + taskName + '\'' +
                ", url='" + url + '\'' +
                ", header=" + header +
                ", body='" + body + '\'' +
                ", info=" + info +
                '}';
    }
}
