package com.dang.crawler.core.script.tools;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.control.norm.JobCounter;
import com.dang.crawler.core.script.tools.dbimpl.DBInterface;
import com.dang.crawler.core.script.tools.dbimpl.MongDB;
import com.dang.crawler.core.script.tools.dbimpl.MySQL;
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
 * 对脚本开放的DB工具,脚本所有的DB操作都调用本类静态方法
 */
public class DB {
    private static Logger log = LoggerFactory.getLogger(DB.class);
    private static DBInterface mongDB = new MongDB();
    private static DBInterface mySQL = new MySQL();
    public static void saveFile(String path,byte[] bytes){
        try {
            FileUtils.save(PropertiesUtils.getProperty("file.output.base.path")+path,bytes,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insert(String name,String key, List<Map> dbList) {
        mySQL.insert(name,key,dbList);
    }
    public static void insert(String name,String key, Map map) {
        mySQL.insert(name,key,map);
    }

    public static List<Keyword> getKeyWorld(int page, int szie, Crawler crawler, Job job){
        if(crawler.get("#keyworld.page")!=null){
            page = (int) crawler.get("#keyworld.page");
        }
        List<Keyword> list = mySQL.getKeyWorld(job.getProjectId(),page,szie);
        if(list.size()>0){
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
