package com.dang.colony.resources.mongo;

import com.dang.colony.resources.mongodb.MongoDB;
import com.mongodb.DBObject;
import org.bson.Document;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * Created by duang on 2017/4/25.
 */
public class MongoTest {
    @Test
    public void test() throws UnknownHostException {
        Document map = new Document();
        map.put("name","test1");
        MongoDB.insert(map, "test");
    }
    @Test
    public void testFind(){
        DBObject ob = MongoDB.find("test", "name", "test1");
        System.out.println(ob.get("name"));
    }
    @Test
    public void update(){
        //MongoDB.updateObject();
        //MongoDB.updateObject();
    }
}
