package com.dang.crawler.core.script.tools;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.core.serivce.KeywordService;
import com.dang.crawler.resources.mongodb.MongoDB;
import com.dang.crawler.resources.mysql.model.Keyword;
import com.dang.crawler.resources.utils.DateUtils;
import com.dang.crawler.resources.utils.FileUtils;
import com.dang.crawler.resources.utils.PropertiesUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * Created by dang on 2017/5/12.
 */
public class DB {
    private static Logger log = LoggerFactory.getLogger(DB.class);
    private static KeywordService keywordService;
    public static void saveFile(String path,byte[] bytes){
        try {
            FileUtils.save(PropertiesUtils.getProperty("file.output.base.path")+path,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insert(String name,String  key, List<Map> dbList) {
        if(dbList==null||dbList.size()==0)return;
        String time = DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        for(Map map: dbList) {
            Document document = new Document();
            map.put("_createTime",time);
            document.putAll(map);
            try {
                MongoDB.insert(document, name);
            } catch (UnknownHostException e) {
                log.error("入库失败<<"+name,e);
            }
        }
        try {
            MongoDB.ensureIndex("createTime_index",name,"_createTime",false);
            MongoDB.ensureIndex("createTime_index",name,key,false);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public static void insert(String name,String key, Map map) {
        if(map==null||map.size()==0)return;
        String time = DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        Document document = new Document();
        map.put("_createTime",time);
        document.putAll(map);
            try {
                MongoDB.insert(document, name);
                MongoDB.ensureIndex("createTime_index",name,"_createTime",false);
                MongoDB.ensureIndex(key+"_index",name,key,false);
            } catch (UnknownHostException e) {
                log.error("入库失败<<"+name,e);
            }

    }
    public static List<Keyword> getKeyWorld(int projectId, int page, int szie){
        if(keywordService==null){
            keywordService = (KeywordService) ApplicationContext.getBean("keywordService");
        }
        List<Keyword> list = keywordService.select(projectId, page, szie);
        return list;
    }
    public static List<Keyword> getKeyWorld(int page, int szie, Crawler crawler, Job job){
        if(keywordService==null){
            keywordService = (KeywordService) ApplicationContext.getBean("keywordService");
        }
        if(crawler.get("#keyworld.page")!=null){
            page = (int) crawler.get("#keyworld.page");
        }
        List<Keyword> list = keywordService.select(job.getProjectId(), page, szie);
        if(list.size()==szie){
            Crawler nextCrawler = new Crawler();
            nextCrawler.setTaskName(crawler.getTaskName());
            nextCrawler.put("#keyworld.page",page+1);
            boolean isadd = ApplicationContext.crawlerButler.put(job, nextCrawler);
            if(isadd) {
                ApplicationContext.jobCounter.update(job, JobCounter.Name.crawler.getName(), 1);
                ApplicationContext.jobCounter.update(job, JobCounter.Name.taskToDo.getName(crawler.getTaskName()), 1);
            }
        }
        return list;
    }
}
