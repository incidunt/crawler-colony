package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.mysql.model.JobTask;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class JobTaskMapperTest {
    //@Resource
    private JobTaskMapper jobTaskMapper;
    @Before
    public void setUp() throws Exception {
        jobTaskMapper= (JobTaskMapper)new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("jobTaskMapper");
    }
    @Test
    public void testQueryByTaskId() throws Exception {
        JobTask jobCode = new JobTask("sina_weibo","Detail");
        jobCode = jobTaskMapper.select(jobCode);
        System.out.println(jobCode.getBytes().length);
    }
    @Test
    public void testlist() throws Exception {
        List<JobTask> jobCodeList = jobTaskMapper.list();
        for(JobTask jobCode : jobCodeList){
            System.out.println(jobCode.getBytes());
        }

    }
}
