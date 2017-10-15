package com.dang.crawler.resources.mysql.dao;

import com.dang.crawler.resources.common.ApplicationContextSingleton;
import com.dang.crawler.resources.utils.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dang on 17-6-15.
 */
public class SqlMapperTest {
    private static SqlMapper sqlMapper =
            (SqlMapper) ApplicationContextSingleton.getInstance().getBean("sqlMapper");

    @Test
    public void testSelect() throws IOException {
        List<Map<String, Object>> res = sqlMapper.select("select * from user_info");
        Set<String> set = new HashSet<>();
        for(Map<String,Object> map : res){
            String str = ((String) map.get("timestamp")).trim();
            StringBuffer buffer = new StringBuffer();
            buffer.append(str.substring(0,11));
            buffer.reverse();
            set.add(buffer.toString());
        }
        StringBuffer stringBuffer = new StringBuffer();
        for(String str:set){
            stringBuffer.append(str+"\t\n");
        }
        FileUtils.save("/home/dang/phone.txt",stringBuffer.toString().getBytes(),true);
    }
}
