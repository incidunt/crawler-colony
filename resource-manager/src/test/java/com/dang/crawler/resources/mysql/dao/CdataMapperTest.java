package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.common.ApplicationContextSingleton;
import com.dang.crawler.resources.mysql.model.Cdata;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class CdataMapperTest {
    //@Resource
    private CdataMapper cDataMapper;
    @Before
    public void setUp() throws Exception {
        cDataMapper= (CdataMapper) ApplicationContextSingleton.getInstance().getBean("cdataMapper");
    }
    @Test
    public void testInsert() {
        Cdata cData = new Cdata();
        cData.setJobId("jobId");
        cData.setCkey("key");
        cData.setCdata("data 123456abc");
        cDataMapper.insert(cData);
        //System.out.println(project.getName());
    }
    @Test
    public void testInsterAll(){
        List<Cdata> list = new ArrayList<>();
        for(int i = 0;i<10;i++) {
            Cdata cData = new Cdata();
            cData.setJobId("jobId"+i);
            cData.setCkey("key"+i);
            cData.setCdata("data-"+i);
            list.add(cData);
        }
        cDataMapper.insertAll(list);
    }
}
