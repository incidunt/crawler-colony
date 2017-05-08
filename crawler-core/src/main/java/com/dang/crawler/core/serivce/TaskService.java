package com.dang.crawler.core.serivce;

import com.dang.crawler.core.tool.Regex;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.compile.DynamicEngine;
import com.dang.crawler.resources.compile.JavaClassObject;
import com.dang.crawler.resources.mysql.dao.CrawlerJobMapper;
import com.dang.crawler.resources.mysql.dao.JobTaskMapper;
import com.dang.crawler.resources.mysql.model.JobTask;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
/**
 * Created by mi on 2017/5/3.
 */
@Service
public class TaskService {
    @Resource
    CrawlerJobMapper crawlerJobMapper;
    @Resource
    JobTaskMapper jobCodeMapper;

    public void create(CrawlerJob crawlerJob, List<String> jobCodeList) throws Exception {
        CrawlerJob selectCrawlerJob = crawlerJobMapper.select(crawlerJob);
        if (selectCrawlerJob == null) {
            crawlerJobMapper.insert(crawlerJob);
        } else {
            crawlerJobMapper.update(selectCrawlerJob);
            JobTask jobTask = new JobTask(crawlerJob.getJobId(), null);
            jobCodeMapper.delete(jobTask);
        }
        for(String code:jobCodeList){
            String fullName = getFullName(code);
            DynamicEngine de = DynamicEngine.getInstance();
            JavaClassObject jco = de.javaCodeToJavaClassObject(fullName, code);
            JobTask jobTask = new JobTask(crawlerJob.getJobId(),fullName.substring(fullName.lastIndexOf(".")+1));
            jobTask.setCode(code);
            jobTask.setBytes(jco.getBytes());
            jobCodeMapper.insert(jobTask);
        }
//        //Script script = getScript();
//        List<CrawlerJob> crawlerJobList = script.create();
//        for (CrawlerJob job : crawlerJobList) {
//            JobCode jobCode = new JobCode(crawlerTask.getTaskId(), job.getClass().getName());
//            jobCode.setCode(job.getClass().toString());
//            jobCode.setCls(job.getClass().toString());
//            jobCodeMapper.insert(jobCode);
//        }
    }

    private String getFullName(String code) {
        List<String> list = Regex.regex("(?<=package).+?(?=;)", code);
        String path = "";
        if(list!=null&&list.size()!=0){
            path = list.get(0).trim();
        }
        list = Regex.regex("(?<=class).+?(?=implements)", code);
        if(list!=null&&list.size()!=0){
           return path+"."+list.get(0).trim();
        }
        return "";
    }

}
