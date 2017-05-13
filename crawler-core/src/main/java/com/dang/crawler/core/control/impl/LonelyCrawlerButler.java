package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.Butler;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.serivce.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * Created by dang on 17-5-11.
 */
public class
LonelyCrawlerButler implements Butler<Job,Crawler> {
    private static Logger log = LoggerFactory.getLogger(LonelyCrawlerButler.class);
    public Map<String,Queue<Crawler>> map = new HashMap<>();
    @Override
    public synchronized boolean put(Job job, Crawler crawler) {
        ApplicationContext.jobCounter.update(job.getJobId(), JobCounter.Attribute.crawler.getName(),1);//+crawler++++++++++++++++++
        Queue<Crawler> queue = map.get(getKey(job));
        boolean result = false;
        if(queue!=null){
            log.info("put>>>"+(queue.size()+1)+">>"+job.getJobId());
            return queue.offer(crawler);
        }else {
            queue = new ArrayDeque<Crawler>();
            map.put(getKey(job),queue);
            log.info("put>>>create>>"+crawler.getJobId());
            return queue.offer(crawler);
        }
    }

    @Override
    public int putAll(Job job, Collection<? extends Crawler> crawlers) {
        ApplicationContext.jobCounter.update(job.getJobId(), JobCounter.Attribute.crawler.getName(),crawlers.size());//+crawler++++++++++++++++++

        Queue<Crawler> queue = map.get(getKey(job));
        boolean result = false;
        if(queue!=null){
            log.info("putAll>>>"+(crawlers.size())+">>"+job.getJobId());
            result = queue.addAll(crawlers);
        }else {
            queue = new ArrayDeque<Crawler>();
            map.put(getKey(job),queue);
            log.info("putAll>>>create>>"+crawlers.size()+">"+job.getJobId());
            result = queue.addAll(crawlers);
        }
        return result ? crawlers.size() : 0;
    }

    @Override
    public synchronized Crawler get(Job job) {
        Queue<Crawler> queue = map.get(getKey(job));
        if(queue!=null){
            Crawler crawler = queue.poll();
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
