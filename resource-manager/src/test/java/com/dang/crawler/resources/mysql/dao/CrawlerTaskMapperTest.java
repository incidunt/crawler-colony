package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.common.ApplicationContextSingleton;
import com.dang.crawler.resources.mysql.model.CrawlerTask;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class CrawlerTaskMapperTest {
    //@Resource
    private CrawlerTaskMapper crawlerTaskMapper;
    @Before
    public void setUp() throws Exception {
        crawlerTaskMapper= (CrawlerTaskMapper) ApplicationContextSingleton.getInstance().getBean("crawlerTaskMapper");
    }
    @Test
    public void testInsert(){
        CrawlerTask crawlerTask = new CrawlerTask();
        crawlerTask.setTaskId("sina_weibo122");
        crawlerTask.setName("dang name");
        crawlerTask.setStatus('1');
        crawlerTask.setNextStartDate(new Date());
        crawlerTask.setPeriod(123456);
        crawlerTaskMapper.insert(crawlerTask);
    }
    @Test
    public void testUpdata(){
        CrawlerTask crawlerTask = new CrawlerTask();
        crawlerTask.setTaskId("sina_weibo122");
        crawlerTask.setName("dang name 222");
        crawlerTask.setStatus('3');
        crawlerTask.setNextStartDate(new Date());
        crawlerTask.setPeriod(123123);
        crawlerTaskMapper.update(crawlerTask);
    }
    @Test
    public void testSelect() {
        CrawlerTask crawlerTask = new CrawlerTask();
        crawlerTask.setTaskId("sina_weibo");
        crawlerTask.setName("新浪微博");
        crawlerTask = crawlerTaskMapper.select(crawlerTask);
        System.out.println(crawlerTask.toString());
    }
    @Test
    public void list(){
        List<CrawlerTask> list = crawlerTaskMapper.list();
        for(CrawlerTask crawlerTask :list){
            System.out.println(crawlerTask.toString());
        }
    }
}
