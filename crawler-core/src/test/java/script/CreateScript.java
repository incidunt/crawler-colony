package script;

import com.dang.crawler.core.serivce.TaskService;
import com.dang.crawler.resources.mysql.model.CrawlerTask;
import com.dang.crawler.resources.utils.FileUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public class CreateScript {
    private static String taskId="sina_weibo";
    private static TaskService taskService = null;
    public static void main(String []args) throws Exception {
        taskService = (TaskService) new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("taskService");
        create();
    }
    public static void create() throws Exception {
        String path = System.getProperty("user.dir") + "/crawler-core/src/test/java/script/"+taskId;
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
        CrawlerTask task = new CrawlerTask();
        task.setTaskId("sina_weibo");
        task.setName("新浪微博");
        task.setPeriod(777777777);
        task.setStatus('0');
        taskService.create(task,codeList);

    }
}
