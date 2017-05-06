package com.dang.crawler.core.serivce;

import com.dang.crawler.resources.mysql.dao.ProjectMapper;
import com.dang.crawler.resources.mysql.model.CrawlerTask;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mi on 2017/5/3.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class TaskServiceTest {
    TaskService taskService ;
    @Before
    public void setUp() throws Exception {
        taskService = (TaskService)new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("taskService");
    }
    @Test
    public void testCreate() throws Exception {
        CrawlerTask crawlerTask = new CrawlerTask();
        crawlerTask.setTaskId("task_test_1");
        crawlerTask.setName("tasl test 1");
        crawlerTask.setPeriod(123);
        crawlerTask.setStatus('0');
        //taskService.create(crawlerTask ,"");
        System.out.println("xxx");
    }
}
