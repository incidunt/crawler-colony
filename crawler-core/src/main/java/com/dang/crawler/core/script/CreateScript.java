package com.dang.crawler.core.script;

import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.core.serivce.JobTaskService;
import com.dang.crawler.core.serivce.KeywordService;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.utils.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created by dang on 2017/5/3.
 */
public class CreateScript {
    private static String JOB_ID = "script_test_speed";
    private static int PROJECT_ID = 2;
    private static int MAX_THREAD =50;
    private static String JOB_NAME = JOB_ID;
    private static int JOB_PERIOD = 60*24*60*60*1000;
    private static JobTaskService jobTaskService = null;
    public static void main(String []args) throws Exception {
        jobTaskService = (JobTaskService) ApplicationContext.getBean("jobTaskService");
        //String path = System.getProperty("user.dir") + "/crawler-core/src/main/java/script/"+JOB_ID.replaceAll("\\.","/");
        //create(path);
        System.out.println(CreateScript.class.getResource("/p2p"));
        createAll();
    }
    public static void create(String path) throws Exception {
        File file = new File(path);
        if(!file.exists()){
            System.out.println("文件夹不存在！"+file.getAbsolutePath());
            return;
        }
        List<String> codeList = new ArrayList<>();
        for(File codeFile :file.listFiles()){
            System.out.println(codeFile.getName());
            String code = FileUtils.toString(codeFile);
            codeList.add(code);
        }
        CrawlerJob task = new CrawlerJob();
        task.setJobId(JOB_ID);
        task.setName(JOB_NAME);
        task.setPeriod(JOB_PERIOD);
        task.setPriority(50);
        task.setMaxThread(MAX_THREAD);
        task.setProjectId(PROJECT_ID);
        task.setStatus(CrawlerJob.Status.standby.getName());
        task.setNextStartDate(new Date());
        jobTaskService.create(task,codeList);
    }
    public static void createAll()  {
        URL url = CreateScript.class.getResource("/p2p");
        System.out.println(url.toString());
        File file = new File(url.getFile());
        for(File f :file.listFiles()){
            JOB_ID = "p2p."+f.getName();
            JOB_NAME = JOB_ID;
            try {
                create(f.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
