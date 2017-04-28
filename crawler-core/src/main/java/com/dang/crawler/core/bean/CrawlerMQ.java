package com.dang.crawler.core.bean;

import com.dang.crawler.core.fetcher.bean.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by duang on 2017/4/27.
 */
public class CrawlerMQ {
    private static Logger logger = LoggerFactory.getLogger(CrawlerMQ.class);
    private Request request = new Request("");
    private String task;
    private Map<String, Object> extending = new HashMap();

    public CrawlerMQ myclone() {
        CrawlerMQ crawlerMQ = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            // 将流序列化成对象
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            crawlerMQ = (CrawlerMQ) ois.readObject();
        } catch (Exception e) {
            logger.error("Crawler克隆失败：" + e);
        }
        return crawlerMQ;
    }
    ////////////////////////////////////////////

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Map<String, Object> getExtending() {
        return extending;
    }

    public void setExtending(Map<String, Object> extending) {
        this.extending = extending;
    }
}
