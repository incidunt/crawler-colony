package com.dang.crawler.core.serivce;

import com.dang.crawler.resources.mysql.dao.ProjectMapper;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mi on 2017/5/3.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class TaskServiceTest {
    JobTaskService jobTaskService ;
    @Before
    public void setUp() throws Exception {
        jobTaskService = (JobTaskService)new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("taskService");
    }
    @Test
    public void testCreate() throws Exception {
        CrawlerJob crawlerJob = new CrawlerJob();
        crawlerJob.setJobId("task_test_1");
        crawlerJob.setName("tasl test 1");
        crawlerJob.setPeriod(123);
        crawlerJob.setStatus(CrawlerJob.Status.run.getName());
        //taskService.create(crawlerTask ,"");
        System.out.println("xxx");
    }
}
