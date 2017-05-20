package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.control.bean.Job;
import com.dang.crawler.core.script.norm.Script;
import com.dang.crawler.resources.compile.DynamicEngine;
import com.dang.crawler.resources.compile.JavaClassObject;
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
        String jobId = "sina_weibo_image";
        String taskName = "Init";
        JobTask jobCode = new JobTask(jobId,taskName);
        jobCode = jobTaskMapper.load(jobCode);
        System.out.println(jobCode.getBytes().length);
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance =  de.javaCodeToObject("script."+jobId+"."+taskName,jobCode.getCode());
        JavaClassObject jco = de.javaCodeToJavaClassObject("script."+jobId+"."+taskName, jobCode.getCode());
        Script script = (Script) de.javaCodeToObject("script."+jobId+"."+taskName,jobCode.getCode());
       //Script script = (Script) instance;
        script.work(new Crawler(),new Job());

    }
    @Test
    public void testlist() throws Exception {
        List<JobTask> jobCodeList = jobTaskMapper.list();
        for(JobTask jobCode : jobCodeList){
            System.out.println(jobCode.getBytes());
        }

    }
}
