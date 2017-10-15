package com.dang.crawler.web.service;

import com.dang.crawler.resources.mysql.dao.CrawlerJobMapper;
import com.dang.crawler.resources.mysql.dao.JobTaskMapper;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.mysql.model.JobTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dang on 2017/5/3.
 * 负责job  task 的创建工作
 */
@Service
public class JobTaskService {
    @Resource
    private CrawlerJobMapper crawlerJobMapper;
    @Resource
    private JobTaskMapper jobCodeMapper;

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
//            DynamicEngine de = DynamicEngine.getInstance();
//            JavaClassObject jco = de.javaCodeToJavaClassObject(fullName, code);
//            de.bytesToObject(fullName,jco.getBytes());
            JobTask jobTask = new JobTask(crawlerJob.getJobId(),fullName.substring(fullName.lastIndexOf(".")+1));
            jobTask.setCode(code);
            //jobTask.setBytes(jco.getBytes());
            jobCodeMapper.insert(jobTask);
        }
    }


    private String getFullName(String code) {
        List<String> list = regex("(?<=package).+?(?=;)", code);
        String path = "";
        if(list!=null&&list.size()!=0){
            path = list.get(0).trim();
        }
        list = regex("(?<=class).+?(?=implements)", code);
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
        List<String> list = regex("new.+Task.+new.+(?=\\))", code);
        for(String str:list){
            int start = str.indexOf(",")+1;
            String head = str.substring(0,start);
            String name = str.substring(start,str.lastIndexOf("("));
            name = name.replace("new","").trim();
            code = code.replace(str,head+"\""+name+"\")");
        }
        return code;
    }
    public static List<String> regex(String regex,String content){
        List<String> resultList = new ArrayList<>();
        if (StringUtils.isEmpty(regex)||StringUtils.isEmpty(content)){
            return resultList;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()) {
            resultList.add(matcher.group());
        }
        return resultList;
    }
}
