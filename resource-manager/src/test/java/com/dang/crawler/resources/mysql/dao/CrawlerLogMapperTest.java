package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.common.ApplicationContextSingleton;
import com.dang.crawler.resources.mysql.model.CrawlerLog;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dang on 17-5-15.
 */
public class CrawlerLogMapperTest {
    //@Resource
    private CrawlerLogMapper crawlerLogMapper;
    @Before
    public void setUp() throws Exception {
        crawlerLogMapper= (CrawlerLogMapper) ApplicationContextSingleton.getInstance().getBean("crawlerLogMapper");
    }
    @Test
    public void testInsert() {
        CrawlerLog crawlerLog = new CrawlerLog("sss","ttt",1111L);
        crawlerLogMapper.insert(crawlerLog);
    }
}
