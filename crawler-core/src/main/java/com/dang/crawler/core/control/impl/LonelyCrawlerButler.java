package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.Butler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * Created by dang on 17-5-11.
 * 单机Crawler容器，负责存储多个job的海量crawler
 */
public class LonelyCrawlerButler implements Butler<Job,Crawler> {
    private static Logger log = LoggerFactory.getLogger(LonelyCrawlerButler.class);
    public Map<String,Stack<Crawler>> map = new HashMap<>();
    @Override
    public synchronized boolean put(Job job, Crawler crawler) {
        log.info("put>>>"+(1)+">>"+job.getJobId()+">"+crawler);
        Stack<Crawler> stack = map.get(getKey(job));
        boolean result = false;
        if(stack!=null){
            return stack.push(crawler)==null;
        }else {
            stack = new Stack<Crawler>();
            map.put(getKey(job),stack);
            return stack.push(crawler)==null;
        }
    }

    @Override
    public synchronized int putAll(Job job, Collection<? extends Crawler> crawlers) {
        Stack<Crawler> stack = map.get(getKey(job));
        boolean result = false;
        if(stack!=null){
            log.info("putAll>>>"+(crawlers.size())+">>"+job.getJobId());
            result = stack.addAll(crawlers);
        }else {
            stack = new Stack<Crawler>();
            map.put(getKey(job),stack);
            log.info("putAll>>>create>>"+crawlers.size()+">"+job.getJobId());
            result = stack.addAll(crawlers);
        }
        return result ? crawlers.size() : 0;
    }

    @Override
    public synchronized Crawler get(Job job) {
        Stack<Crawler> stack = map.get(getKey(job));
        if(stack!=null&&!stack.empty()){
            Crawler crawler = stack.pop();
            if(crawler!=null){
                return crawler;
            }
        }
        return null;
    }

    @Override
    public boolean remove(Job job) {
        map.remove(getKey(job));
        return true;
    }

    @Override
    public int size(Job job) {
        if(job==null){
            return map.size();
        }
        return map.get(getKey(job)).size();
    }

    @Override
    public int freeSize(Job job) {
        return Integer.MAX_VALUE;
    }



    private String getKey(Job job){
        return  job.getJobId();
    }

}
