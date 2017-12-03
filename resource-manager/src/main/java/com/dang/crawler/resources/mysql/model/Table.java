package com.dang.crawler.resources.mysql.model;

import java.util.*;

/**
 * Created by dang on 17-6-9.
 */
public class Table {
    private String name;
    private List<Map<String, String>> mapList = new ArrayList<>();

    public Table(){}
    public Table(String name){
        this.name = name;
    }
    public Table put(String key,String value){
        if(mapList.size()==0){
            mapList.add(new HashMap<String, String>());
        }
        mapList.get(0).put(key,value);
        return this;
    }
    public Table add(Map map){
        if(map.size()!= 0) {
            mapList.add(map);
        }
        return this;
    }
    public Table addAll(List list){
        mapList.addAll(list);
        return this;
    }

    public Set<String> getKeys() {
        if(mapList.size()==0){
            mapList.add(new HashMap<String, String>());
        }
        return mapList.get(0).keySet();
    }
    ///////////////////////////////////////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }
}
