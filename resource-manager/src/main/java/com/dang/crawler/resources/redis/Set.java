package com.dang.crawler.resources.redis;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * Created by dang on 17-6-2.
 */
public class Set {
    private String key = "";
    private Jedis jedis = RedisPool.getJedis();
    public Set(String key) {
        this.key = key;
    }

    public Long add(Object value) {
        return jedis.sadd(key, JSONObject.toJSONString(value));
    }
    public Long add(Object ... value) {
        return jedis.sadd(key, JSONObject.toJSONString(value));
    }
    public Object pop(){
        return JSONObject.parse(jedis.spop(key));
    }

    @Override
    protected void finalize() throws Throwable {
        jedis.close();
        super.finalize();
    }
}
