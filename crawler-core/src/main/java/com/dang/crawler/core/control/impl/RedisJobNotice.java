package com.dang.crawler.core.control.impl;

import com.alibaba.fastjson.JSONObject;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.Notice;
import com.dang.crawler.resources.redis.RedisPool;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * Created by dang on 17-6-2.
 */
public class RedisJobNotice implements Notice<Job,Job> {
    private final String KEY = "jobNotice";
    @Override
    public Job notice() {
        Jedis jedis = RedisPool.getJedis();
        String json = jedis.srandmember(KEY);
        jedis.close();
        if(StringUtils.isNotEmpty(json)){
            Job job = JSONObject.parseObject(json, Job.class);
            return job;
        }
        return null;
    }

    @Override
    public boolean put(Job job, Job value) {
        Jedis jedis = RedisPool.getJedis();
        Long res = jedis.sadd(KEY, JSONObject.toJSONString(value));
        jedis.close();
        return res>0;
    }

    @Override
    public Job remove(Job job) {
        Jedis jedis = RedisPool.getJedis();
        jedis.srem(KEY, JSONObject.toJSONString(job));
        jedis.close();
        return job;
    }
}
