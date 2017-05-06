package com.dang.crawler.core.serivce;

import com.dang.crawler.core.tool.Regex;
import com.dang.crawler.resources.compile.DynamicEngine;
import com.dang.crawler.resources.compile.JavaClassObject;
import com.dang.crawler.resources.mysql.dao.CrawlerTaskMapper;
import com.dang.crawler.resources.mysql.dao.JobCodeMapper;
import com.dang.crawler.resources.mysql.model.CrawlerTask;
import com.dang.crawler.resources.mysql.model.JobCode;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
/**
 * Created by mi on 2017/5/3.
 */
@Service
public class TaskService {
    @Resource
    CrawlerTaskMapper crawlerTaskMapper;
    @Resource
    JobCodeMapper jobCodeMapper;

    public void create(CrawlerTask crawlerTask, List<String> jobCodeList) throws Exception {
        CrawlerTask selectCrawlerTask = crawlerTaskMapper.select(crawlerTask);
        if (selectCrawlerTask == null) {
            crawlerTaskMapper.insert(crawlerTask);
        } else {
            crawlerTaskMapper.update(crawlerTask);
            JobCode jobCode = new JobCode(crawlerTask.getTaskId(), null);
            jobCodeMapper.delete(jobCode);
        }
        for(String code:jobCodeList){
            String fullName = getFullName(code);
            DynamicEngine de = DynamicEngine.getInstance();
            JavaClassObject jco = de.javaCodeToJavaClassObject(fullName, code);
            JobCode jobCode = new JobCode(crawlerTask.getTaskId(),fullName.substring(fullName.lastIndexOf(".")+1));
            jobCode.setCode(code);
            jobCode.setCls(jco.getBytes());
            jobCodeMapper.insert(jobCode);
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
