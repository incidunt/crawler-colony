package com.dang.crawler.core.script.tools;

import com.dang.crawler.resources.mongodb.MongoDB;
import com.dang.crawler.resources.utils.FileUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by mi on 2017/5/12.
 */
public class DB {
    private static Logger log = LoggerFactory.getLogger(DB.class);
    public static void saveFile(String path,byte[] bytes){
        try {
            FileUtils.save("D:\\Files\\MyCode\\outPut\\"+path,bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insert(String name, List<Map> dbList) {
        for(Map map: dbList) {
            Document document = new Document();
            document.putAll(map);
            try {
                MongoDB.insert(document, name);
            } catch (UnknownHostException e) {
                log.error("入库失败<<"+name,e);
            }
        }
    }
}
