package com.dang.crawler.core.serivce;

import com.dang.crawler.core.parser.utils.RegexUtils;
import com.dang.crawler.core.script.annotation.JobInfo;
import com.dang.crawler.core.script.annotation.JobInfoUtil;
import com.dang.crawler.core.script.norm.Script;
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
 * Created by dang on 2017/5/3.
 * 负责job  task 的创建工作
 */
@Service
public class JobTaskService {
    @Resource
    CrawlerJobMapper crawlerJobMapper;
    @Resource
    JobTaskMapper jobCodeMapper;

    public void create(CrawlerJob crawlerJob, List<String> jobCodeList) throws Exception {
        CrawlerJob selectCrawlerJob = crawlerJobMapper.select(crawlerJob);
        if (selectCrawlerJob == null) {
            crawlerJobMapper.insert(crawlerJob);
        } else {
            crawlerJobMapper.update(crawlerJob);
            JobTask jobTask = new JobTask(crawlerJob.getJobId(), null);
            jobCodeMapper.delete(jobTask);
        }
        for(String code:jobCodeList){
            String fullName = getFullName(code);
            code = convert(code);
            DynamicEngine de = DynamicEngine.getInstance();
            JavaClassObject jco = de.javaCodeToJavaClassObject(fullName, code);
            Script script = (Script) de.bytesToObject(fullName,jco.getBytes());
            System.out.println(script);
            JobTask jobTask = new JobTask(crawlerJob.getJobId(),fullName.substring(fullName.lastIndexOf(".")+1));
            jobTask.setCode(code);
            jobTask.setBytes(jco.getBytes());
            jobCodeMapper.insert(jobTask);
        }
    }
    public void create(List<String> jobCodeList) throws Exception {
        CrawlerJob crawlerJob = new CrawlerJob();
        for(String code:jobCodeList) {
            String fullName = getFullName(code);
            code = convert(code);
            DynamicEngine de = DynamicEngine.getInstance();
            JavaClassObject jco = de.javaCodeToJavaClassObject(fullName, code);
            Object scriptObj = de.bytesToObject(fullName, jco.getBytes());
            Script script = (Script) scriptObj;
            JobInfo info = JobInfoUtil.getFruitInfo(script.getClass());
            if (info != null) {
                crawlerJob.setJobId(info.id());
                crawlerJob.setName(info.name());
                crawlerJob.setPriority(info.priority());
                crawlerJob.setMaxThread(info.maxThread());
                crawlerJob.setPeriod(info.period());
            }
        }
        create(crawlerJob,jobCodeList);
    }

    private String getFullName(String code) {
        List<String> list = RegexUtils.regex("(?<=package).+?(?=;)", code);
        String path = "";
        if(list!=null&&list.size()!=0){
            path = list.get(0).trim();
        }
        list = RegexUtils.regex("(?<=class).+?(?=implements)", code);
        if(list!=null&&list.size()!=0){
           return path+"."+list.get(0).trim();
        }
        return "";
    }

    /**
     * 把直接调用改为字符串参数
     * @param code
     * @return
     */
    private String convert(String code){
        List<String> list = RegexUtils.regex("new.+Task.+new.+(?=\\))", code);
        for(String str:list){//Task(crawlerMQList, new Page())
            int start = str.indexOf(",")+1;
            String head = str.substring(0,start);
            String name = str.substring(start,str.lastIndexOf("("));
            name = name.replace("new","").trim();
            code = code.replace(str,head+"\""+name+"\")");
        }
        return code;
    }
}
