package com.dang.crawler.resources.redis;

import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.utils.SerializableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by dang on 17-6-2.
 */
public class RedisUtils {
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    public static Set set(String key){
        return new Set(key);
    }
    /**
     * 获取redis键值-object
     * @param key
     * @return
     */
    public Object getObject(String key) {
        Jedis jedis = RedisPool.getJedis();
        try {
            byte[] bytes = jedis.get(key.getBytes());
            if((bytes!=null)) {
                return SerializableUtil.unserializable(bytes);
            }
        } catch (Exception e) {
            logger.error("getObject获取redis键值异常:key=" + key + " cause:" + e.getMessage());
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 设置redis键值-object
     * @param key
     * @param value
     * @return
     */
    public String setObject(String key, Object value) {
        Jedis jedis = RedisPool.getJedis();;
        try {
            return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
        } catch (Exception e) {
            logger.error("setObject设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
            return null;
        } finally {
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public String setObject(String key, Object value,int expiretime) {
        String result = "";
        Jedis jedis = RedisPool.getJedis();;
        try {
            result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
            if(result.equals("OK")) {
                jedis.expire(key.getBytes(), expiretime);
            }
            return result;
        } catch (Exception e) {
            logger.error("setObject设置redis键值异常:key=" + key + " value=" + value + " cause:" + e.getMessage());
        } finally {
            if(jedis != null)
            {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 删除key
     */
    public Long delkeyObject(String key) {
        Jedis jedis = RedisPool.getJedis();;
        try {
            return jedis.del(key.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public Boolean existsObject(String key) {
        Jedis jedis = RedisPool.getJedis();
        try {
            return jedis.exists(key.getBytes());
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }
}
