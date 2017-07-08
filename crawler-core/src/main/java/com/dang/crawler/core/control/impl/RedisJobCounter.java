package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.resources.mysql.dao.CrawlerLogMapper;
import com.dang.crawler.resources.mysql.model.CrawlerLog;
import com.dang.crawler.resources.redis.RedisPool;
import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dang on 17-6-2.
 * Redis job计数器
 */
public class RedisJobCounter implements JobCounter {
    private CrawlerLogMapper crawlerLogMapper;
    private int sun = 0;
    @Override
    public synchronized Integer update(Job job, String key, Integer count) {
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.hincrBy(key(job), key, count);
        jedis.close();
        if (sun++ % 10 == 0) {
            flush(job);//TODO 分散
        }
        return res.intValue();
    }

    @Override
    public synchronized Integer get(Job job, String field) {
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.hincrBy(key(job), field, 0);
        jedis.close();
        return res.intValue();
    }

    @Override
    public synchronized boolean remove(Job job) {
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.del(key(job));
        jedis.close();
        return res>0;
    }

    @Override
    public synchronized void flush(Job job) {
        Jedis jedis = RedisPool.getJedis();
        Set<String> fields = jedis.hkeys(key(job));
        jedis.close();
        Map<String,CrawlerLog> logMap = new HashMap<>();
            for(String field : fields){
                if(field.contains(":")){
                    String[] attr = field.split(":");
                    String type = attr[0];
                    String taskName = attr[1];
                    CrawlerLog log = logMap.get(taskName);
                    if(log == null) {
                        log = new CrawlerLog(job.getJobId(),taskName,job.getFlag());
                    }
                    switch (type){
                        case "taskToDo":{log.setToDoCount(get(job,field));break;}
                        case "taskFail":{log.setFailCount(get(job,field));break;}
                        case "taskSuccess":{log.setSuccessCount(get(job,field));break;}
                    }
                    logMap.put(taskName,log);
                }
            }
            for(Map.Entry<String,CrawlerLog> logEntry : logMap.entrySet()){
                if(crawlerLogMapper==null){
                    crawlerLogMapper = (CrawlerLogMapper) ApplicationContext.getBean("crawlerLogMapper");
                }
                crawlerLogMapper.update(logEntry.getValue());
            }

    }
    private String key(Job job){
        return "counter:"+job.getJobId()+":"+job.getFlag();
    }
}
