package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.common.ApplicationContextSingleton;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class CrawlerJobMapperTest {
    //@Resource
    private CrawlerJobMapper crawlerJobMapper;
    @Before
    public void setUp() throws Exception {
        crawlerJobMapper= (CrawlerJobMapper) ApplicationContextSingleton.getInstance().getBean("crawlerJobMapper");
    }
    @Test
    public void testInsert(){
        CrawlerJob crawleJob = new CrawlerJob();
        crawleJob.setJobId("job_id_test");
        crawleJob.setName("djob name");
        crawleJob.setStatus('1');
        crawleJob.setPriority(5);
        crawleJob.setMaxThread(10);
        crawleJob.setNote("note test");
        crawleJob.setNextStartDate(new Date());
        crawleJob.setPeriod(123);
        crawlerJobMapper.insert(crawleJob);
    }

    @Test
    public void testUpdata(){
        CrawlerJob crawleJob = new CrawlerJob();
        crawleJob.setJobId("job_id_test");
        crawleJob.setName("dang_name");
        crawleJob.setStatus('1');
        crawleJob.setPriority(5);
        crawleJob.setMaxThread(10);
        crawleJob.setName("note test");
        crawleJob.setNextStartDate(new Date());
        crawleJob.setPeriod(123456);
        crawlerJobMapper.update(crawleJob);
    }
    @Test
    public void testSelect() {
        CrawlerJob crawleJob = new CrawlerJob();
        crawleJob.setJobId("job_id_test");
        crawleJob.setName("dang_name");
        crawleJob = crawlerJobMapper.select(crawleJob);
        System.out.println(crawleJob.toString());
    }
    @Test
    public void list(){
        List<CrawlerJob> list = crawlerJobMapper.list();
        for(CrawlerJob crawleJob :list){
            System.out.println(crawleJob.toString());
        }
    }
    @Test
    public void testDelete(){
        crawlerJobMapper.delete("sina_weibo122");
    }
}
