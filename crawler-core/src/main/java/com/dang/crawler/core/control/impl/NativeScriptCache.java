package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.bean.JobCrawler;
import com.dang.crawler.core.control.norm.Cache;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.resources.compile.DynamicEngine;
import com.dang.crawler.resources.compile.JavaClassObject;
import com.dang.crawler.resources.mysql.dao.JobTaskMapper;
import com.dang.crawler.resources.mysql.model.JobTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dang on 17-5-10.
 * 本机Script缓存容器 负责存储脚本缓存
 */
@Service
public class NativeScriptCache implements Cache<JobCrawler,Script>{
    private Logger log = LoggerFactory.getLogger(NativeScriptCache.class);
    private Map<String,SoftReference<Script>> cache = new HashMap<>();
    @Resource
    private JobTaskMapper jobTaskMapper;
    @Override
    public Script get(JobCrawler jobCrawler) {
        String key = getKey(jobCrawler);
        Script value = null;
        SoftReference<Script> scriptSoftReference = cache.get(key);
        if(scriptSoftReference!=null) {
             value = (Script) scriptSoftReference.get();
        }
        if(value!=null){
            return value;
        }else {
            try {
                return make(jobCrawler);
            } catch (Exception e) {
                log.info("获取Script失败"+jobCrawler.getJob().getJobId()+">>"+jobCrawler.getCrawler().getTaskName(),e);
            }
        }
        return null;
    }

    @Override
    public synchronized boolean put(JobCrawler crawler, Script script) {
        return false;
    }

    public Script make(JobCrawler jobCrawler) throws Exception {
        JobTask jobTask = new JobTask();
        jobTask.setJobId(jobCrawler.getJob().getJobId());
        jobTask.setTaskName(jobCrawler.getCrawler().getTaskName());
        jobTask = jobTaskMapper.load(jobTask);
        if(jobTask==null)return null;
        DynamicEngine dynamicEngine = DynamicEngine.getInstance();
        String fullClassName = "script."+jobCrawler.getJob().getJobId()+"."+jobCrawler.getCrawler().getTaskName();
        Script script = null;
        try {
            script = (Script) DynamicEngine.getInstance().bytesToObject(fullClassName,jobTask.getBytes());
        }catch (Exception e){
            log.info("获取class失败 ，重新编译>>"+jobTask.getJobId()+">"+jobTask.getTaskName());

            JavaClassObject jco = dynamicEngine.javaCodeToJavaClassObject(fullClassName, jobTask.getCode());
            jobTask.setBytes(jco.getBytes());
            jobTaskMapper.update(jobTask);
            script = (Script) DynamicEngine.getInstance().bytesToObject(fullClassName,jobTask.getBytes());
        }
        cache.put(getKey(jobCrawler),new SoftReference<Script>(script));
        return script;
    }

    private String getKey(JobCrawler jobCrawler){
        return jobCrawler.getJob().getJobId()+":"+jobCrawler.getCrawler().getTaskName();
    }
}
