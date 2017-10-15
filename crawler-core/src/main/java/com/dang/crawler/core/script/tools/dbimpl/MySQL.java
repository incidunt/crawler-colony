package com.dang.crawler.core.script.tools.dbimpl;

import com.dang.crawler.core.serivce.ApplicationContext;
import com.dang.crawler.core.serivce.KeywordService;
import com.dang.crawler.resources.mysql.dao.TableMapper;
import com.dang.crawler.resources.mysql.model.Keyword;
import com.dang.crawler.resources.mysql.model.Table;
import java.util.*;

/**
 * Created by dang on 17-6-7.
 */
public class MySQL implements DBInterface{
    private static KeywordService keywordService = (KeywordService) ApplicationContext.getBean("keywordService");
    private static TableMapper tableMapper = (TableMapper) ApplicationContext.getBean("tableMapper");
    private static Set<String> set = new HashSet<>();
    @Override
    public boolean saveFile(String path, byte[] bytes) {
        return false;
    }

    @Override
    public int insert(String name, String key, List<Map> dbList) {
        Table table = new Table("crawler_"+name);
        table.addAll(dbList);
        createTable(table);
        tableMapper.insert(table);
        return dbList.size();
    }

    @Override
    public int insert(String name, String key, Map map) {
        Table table = new Table("crawler_"+name);
        table.add(map);
        createTable(table);
        tableMapper.insert(table);
        return 1;
    }
    public synchronized void createTable(Table table){
        if(!set.contains(table.getName())){
            log.info("create table>>"+table.getName());
            set.add(table.getName());
            try {
                tableMapper.tryCreate(table);
            }catch (Exception e){
                log.error("create table error>>"+table.getName(),e);
            }
        }
    }
    @Override
    public List<Keyword> getKeyWorld(int projectId, int page, int szie) {
        List<Keyword> list = keywordService.select(projectId, page, szie);
        return list;
    }
}
