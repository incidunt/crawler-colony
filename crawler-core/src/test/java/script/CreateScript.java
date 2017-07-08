package script;

import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.core.serivce.JobTaskService;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.utils.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建Job使用,原理就是吧JOB_ID对应的同名文件夹(包)下所有的源码传给JobTaskService.create(Job,List<String>)
 * 其中Job的Job相关的信息,List是源码
 */
public class CreateScript {
    private static String JOB_ID = "script_test_speed";
    private static int PROJECT_ID = 2;
    private static int MAX_THREAD =5;
    private static String JOB_NAME = JOB_ID;
    private static int JOB_PERIOD = 5*24*60*60*1000;
    private static JobTaskService jobTaskService = null;
    public static void main(String []args) throws Exception {
        jobTaskService = (JobTaskService) new ApplicationContext().getBean("jobTaskService");
        String path = System.getProperty("user.dir") + "/crawler-core/src/test/java/script/"+JOB_ID.replaceAll("\\.","/");
        create(path);
        //createAll();
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
        task.setPriority(MAX_THREAD);
        task.setMaxThread(MAX_THREAD);
        task.setProjectId(PROJECT_ID);
        task.setStatus(CrawlerJob.Status.standby.getName());
        task.setNextStartDate(new Date());
        jobTaskService.create(task,codeList);
    }
    public static void createAll()  {
        String path = System.getProperty("user.dir") + "/crawler-core/src/test/java/script/p2p";
        System.out.println(path);
        File file = new File(path);
        for(File f :file.listFiles()){
            JOB_ID = "p2p."+f.getName();
            JOB_NAME = JOB_ID;
            try {
                create(path+"/"+f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
