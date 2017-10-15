package com.dang.crawler.core.script.tools.dbimpl;

import com.dang.crawler.resources.mysql.model.Keyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by dang on 17-6-7.
 */
public interface DBInterface {
    static Logger log = LoggerFactory.getLogger(DBInterface.class);
     boolean saveFile(String path,byte[] bytes);

    int insert(String name,String  key, List<Map> dbList);
    int insert(String name,String key, Map map);
    List<Keyword> getKeyWorld(int projectId, int page, int szie);
}
