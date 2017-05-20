package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dang on 17-5-11.
 * 单机Job容器，负责存储运行的job信息
 */
public class LonelyJobNotice implements Notice<Job,Job> {
    private static Logger log = LoggerFactory.getLogger(LonelyJobNotice.class);
    private Map<String,Job> map = new HashMap<String,Job>();
    private int count = 0;
    @Override
    public synchronized Job notice() {
        int index = 0;
        if(map.size()==0) return null;
        for (Map.Entry<String, Job> entry : map.entrySet()) {
            if((index++)==(count)%map.size()){
                count++;
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public synchronized Job put(Job key, Job job) {
        log.info("put>>"+key);
        return map.put(key.getJobId(),job);
    }

    @Override
    public Job remove(Job key) {
        return map.remove(key.getJobId());
    }
}
