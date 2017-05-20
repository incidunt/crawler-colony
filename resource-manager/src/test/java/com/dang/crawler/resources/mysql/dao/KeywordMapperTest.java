package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.common.ApplicationContextSingleton;
import com.dang.crawler.resources.mysql.model.Keyword;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dang on 17-5-16.
 */
public class KeywordMapperTest {
    //@Resource
    private KeywordMapper keywordMapper;
    @Before
    public void setUp() throws Exception {
        keywordMapper= (KeywordMapper) ApplicationContextSingleton.getInstance().getBean("keywordMapper");
    }
    @Test
    public void taskInsert(){
        Keyword keyword = new Keyword(1,1,"test","tes");
        keywordMapper.insert(keyword);
    }
}
