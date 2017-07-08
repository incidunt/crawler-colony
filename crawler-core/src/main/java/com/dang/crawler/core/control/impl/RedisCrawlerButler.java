package com.dang.crawler.core.control.impl;

import com.alibaba.fastjson.JSONObject;
import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.Butler;
import com.dang.crawler.resources.redis.RedisPool;
import com.dang.crawler.resources.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Collection;

/**
 * Created by dang on 17-6-2.
 * Redis crawler存储器
 */
public class RedisCrawlerButler implements Butler<Job,Crawler> {
    private static Logger log = LoggerFactory.getLogger(RedisCrawlerButler.class);
    @Override
    public synchronized boolean put(Job job, Crawler crawler) {
        RedisUtils.set(getKey(job)).add(crawler);
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.sadd(getKey(job), JSONObject.toJSONString(crawler));
        jedis.close();
        return res>0;
    }

    @Override
    public synchronized int putAll(Job job, Collection<? extends Crawler> crawlers) {
        if(crawlers==null||crawlers.size()==0)  return 0;
        String [] array = new String[crawlers.size()];
        int index = 0;
        for (Crawler crawler:crawlers){
            array[index++] = JSONObject.toJSONString(crawler);
        }
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.sadd(getKey(job), array);
        jedis.close();
        return res.intValue();
    }

    @Override
    public synchronized Crawler get(Job job) {
        Jedis jedis = RedisPool.getJedis();
        String str = jedis.spop(getKey(job));
        Crawler res = JSONObject.parseObject(str, Crawler.class);
        jedis.close();
        return res;
    }

    @Override
    public synchronized boolean remove(Job job) {
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.del(getKey(job));
        jedis.close();
        return res>0;
    }

    @Override
    public synchronized int size(Job job) {
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.scard(getKey(job));
        jedis.close();
        return res.intValue();
    }

    @Override
    public synchronized int freeSize(Job job) {
        return Integer.MAX_VALUE;
    }

    private String getKey(Job job){
        return  "crawler:"+job.getJobId();
    }
}
