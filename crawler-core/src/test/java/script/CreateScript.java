package script;

import com.dang.crawler.core.serivce.TaskService;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.utils.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public class CreateScript {
    private static final String JOB_ID = "script_test_speed";
    private static final String JOB_NAME = "script_test_speed";
    private static final long JOB_PERIOD = 5L*24*60*60*1000;
    private static TaskService taskService = null;
    public static void main(String []args) throws Exception {
        taskService = (TaskService) new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("taskService");
        create();
    }
    public static void create() throws Exception {
        String path = System.getProperty("user.dir") + "/crawler-core/src/test/java/script/"+JOB_ID;
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
        task.setStatus(CrawlerJob.Status.standby.getName());
        task.setNextStartDate(new Date());
        taskService.create(task,codeList);

    }
}
