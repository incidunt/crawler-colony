package com.dang.crawler.core.serivce;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dang.crawler.resources.mysql.model.CrawlerJob;
import com.dang.crawler.resources.mysql.model.Keyword;
import com.dang.crawler.resources.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mi on 2017/5/3.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-*.xml"})
public class KeywordServiceTest {
    KeywordService keywordService ;
    @Before
    public void setUp() throws Exception {
        keywordService = (KeywordService)new ClassPathXmlApplicationContext(new String[] { "classpath:spring-*.xml" }).getBean("keywordService");
    }
    @Test
    public void testCreate() throws Exception {
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        String jsonPath = System.getProperty("user.dir") + "/src/test/java/com/dang/crawler/core/serivce/" + "weibo_user.txt";
        String json = FileUtils.toString(jsonPath);
        System.out.println(jsonPath);
        JSONArray array = JSONObject.parseArray(json);
        Map<String,String> map = new HashMap<>();
        for(int i = 0;i<array.size();i++){
            JSONObject a = array.getJSONObject(i);
            System.out.println(a.get("name"));//name
            map.put(a.get("user").toString(),a.get("name").toString());
        }
        keywordService.insertKeyword(1,map);
    }
    @Test
    public void testSelect(){
        List<Keyword> a = keywordService.select(1, 0, 10);
        System.out.println(a.size());

    }
}
