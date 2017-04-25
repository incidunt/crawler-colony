package com.dang.crawler.core.tool;

import com.dang.colony.resources.mongodb.MongoDB;
import com.mongodb.DBObject;

/**
 * Created by duang on 2017/4/25.
 */
public class Mongo {
    public static void main(String []args){
        DBObject ob = MongoDB.find("test", "name", "test1");
        System.out.println(ob.get("name"));
    }
}
