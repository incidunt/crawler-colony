package com.dang.crawler.core.script.tools.dbimpl;

import com.dang.crawler.resources.mongodb.MongoDB;
import com.dang.crawler.resources.mysql.model.Keyword;
import com.dang.crawler.resources.utils.DateUtils;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dang on 17-6-7.
 */
public class MongDB implements DBInterface{

    @Override
    public boolean saveFile(String path, byte[] bytes) {
        return false;
    }

    @Override
    public int insert(String name, String key, List<Map> dbList) {
        if(dbList==null||dbList.size()==0)return 0;
        String time = DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        for(Map map: dbList) {
            Document document = new Document();
            map.put("_createTime",time);
            document.putAll(map);
            try {
                MongoDB.insert(document, name);
            } catch (UnknownHostException e) {
                log.error("入库失败<<"+name,e);
            }
        }
        try {
            MongoDB.ensureIndex("createTime_index",name,"_createTime",false);
            MongoDB.ensureIndex("createTime_index",name,key,false);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return dbList.size();
    }

    @Override
    public int insert(String name, String key, Map map) {
        if(map==null||map.size()==0)return 0;
        String time = DateUtils.dateConvertToString(new Date(),"yyyy-MM-dd HH:mm:ss");
        Document document = new Document();
        map.put("_createTime",time);
        document.putAll(map);
        try {
            MongoDB.insert(document, name);
            MongoDB.ensureIndex("createTime_index",name,"_createTime",false);
            MongoDB.ensureIndex(key+"_index",name,key,false);
        } catch (UnknownHostException e) {
            log.error("入库失败<<"+name,e);
        }
        return 1;
    }

    @Override
    public List<Keyword> getKeyWorld(int projectId, int page, int szie) {
        return null;
    }
}
