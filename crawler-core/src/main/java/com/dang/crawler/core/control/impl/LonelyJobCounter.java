package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.norm.JobCounter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mi on 2017/5/12.
 */
public class LonelyJobCounter implements JobCounter {
    private Map<String,Map<String,Integer>> map = new HashMap<>();
    @Override
    public synchronized int  update(String jobId, String name, int count) {
        Map<String, Integer> v = map.get(jobId);
        if(v ==null){
            Map<String, Integer> newValue = new HashMap<>();
            newValue.put(name,count);
            map.put(jobId,newValue);
            return count;
        }else {
            if(v.containsKey(name)){
                int value = v.get(name);
                v.put(name,value+count);
                return value+count;
            }else {
                v.put(name,count);
                return count;
            }
        }
    }
}
