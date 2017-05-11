package com.dang.crawler.core.parser;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by dang on 17-5-10.
 */
public class Parser {
    public static Text html(String html){
        return new Text(html);
    }
    public static JSONObject json(String json){
        return JSON.parseObject(json);
    }
    public static JSONArray jsonArray(String jsonArray){
        return JSON.parseArray(jsonArray);
    }
}
