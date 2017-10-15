package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.common.ApplicationContextSingleton;
import com.dang.crawler.resources.mysql.model.Cdata;
import com.dang.crawler.resources.mysql.model.Table;
import javafx.scene.control.Tab;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by dangqihe on 2017/4/25.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class TableMapperTest {
    //@Resource
    private TableMapper tableMapper;
    @Before
    public void setUp() throws Exception {
        tableMapper= (TableMapper) ApplicationContextSingleton.getInstance().getBean("tableMapper");
    }
    public Table getTable(){
        Table table = new Table("test_table");
        table.put("a", "safd");
        table.put("b", "dsjaf");
        table.put("c", "123456");
        table.put("d", "风景的撒娇房顶上经费等快速拉进房间空间上");
        return table;
    }
    @Test
    public void testCreate() {
        Table table = getTable();
        tableMapper.tryCreate(table);
    }
    @Test
    public void testInsert() {
       Table table = getTable();
        tableMapper.insert(table);
    }
    @Test
    public void testInsterAll(){
        Table table = new Table("test_table");
        for(int i=0;i<10;i++) {
            Map map = new HashMap();
            map.put("a", "safd"+i);
            map.put("b", "dsjaf"+i);
            map.put("c", "123456"+i);
            map.put("d", "风景的撒娇房顶上经费等快速拉进房间空间上"+i);
            table.add(map);
        }

//        tableMapper.tryCreate1(map);
        tableMapper.insert(table);
    }
    @Test
    public void testInsertAll(){

    }
}
