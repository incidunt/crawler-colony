package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.Butler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * Created by dang on 17-5-11.
 */
public class
LonelyCrawlerButler implements Butler<Job,Crawler> {
    private static Logger log = LoggerFactory.getLogger(LonelyCrawlerButler.class);
    public Map<Job,Queue<Crawler>> map = new HashMap<>();
    @Override
    public synchronized boolean put(Job job, Crawler crawler) {
        Queue<Crawler> queue = map.get(getKey(job));
        boolean result = false;
        if(queue!=null){
            log.info("put>>>"+(queue.size()+1)+">>"+job.getJobId());
            return queue.offer(crawler);
        }else {
            queue = new ArrayDeque<Crawler>();
            map.put(job,queue);
            log.info("put>>>create>>"+crawler.getJobId());
            return queue.offer(crawler);
        }
    }

    @Override
    public int putAll(Job job, Collection<? extends Crawler> crawlers) {
        Queue<Crawler> queue = map.get(getKey(job));
        boolean result = false;
        if(queue!=null){
            log.info("putAll>>>"+(crawlers.size())+">>"+job.getJobId());
            result = queue.addAll(crawlers);
        }else {
            queue = new ArrayDeque<Crawler>();
            map.put(job,queue);
            log.info("putAll>>>create>>"+crawlers.size()+">"+job.getJobId());
            result = queue.addAll(crawlers);
        }
        return result ? crawlers.size() : 0;
    }

    @Override
    public synchronized Crawler get(Job job) {
        Queue<Crawler> queue = map.get(getKey(job));
        if(queue!=null){
            return queue.poll();
        }
        return null;
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



    private Job getKey(Job job){
        return  job;
    }

}
