package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.mysql.model.JobCode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class JobCodeMapperTest {
    //@Resource
    private JobCodeMapper jobCodeMapper;
    @Before
    public void setUp() throws Exception {
        jobCodeMapper= (JobCodeMapper)new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("jobCodeMapper");
    }
    @Test
    public void testQueryByTaskId() throws Exception {
        JobCode jobCode = new JobCode("sina_weibo","Init");
        jobCode = jobCodeMapper.select(jobCode);
        System.out.println(jobCode.getCls().length);
    }
    @Test
    public void testlist() throws Exception {
        List<JobCode> jobCodeList = jobCodeMapper.list();
        for(JobCode jobCode : jobCodeList){
            System.out.println(jobCode.getCls());
        }

    }
}
