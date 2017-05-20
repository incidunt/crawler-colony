package script;

import com.dang.crawler.core.serivce.JobTaskService;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.utils.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dang on 2017/5/3.
 */
public class CreateScript {
    private static final String JOB_ID = "p2p._51rzy";
    private static final int PROJECT_ID = 2;
    private static final int MAX_THREAD =1;
    private static final String JOB_NAME = JOB_ID;
    private static final long JOB_PERIOD = 5L*24*60*60*1000;
    private static JobTaskService jobTaskService = null;
    public static void main(String []args) throws Exception {
        jobTaskService = (JobTaskService) new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("taskService");
        create();
    }
    public static void create() throws Exception {
        String path = System.getProperty("user.dir") + "/crawler-core/src/test/java/script/"+JOB_ID.replaceAll("\\.","/");
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
        task.setPriority(6);
        task.setMaxThread(MAX_THREAD);
        task.setProjectId(PROJECT_ID);
        task.setStatus(CrawlerJob.Status.standby.getName());
        task.setNextStartDate(new Date());
        jobTaskService.create(task,codeList);

    }
}
