package com.dang.crawler.core.script.norm;

import com.dang.crawler.core.control.bean.Crawler;
import com.dang.crawler.core.script.norm.Script;

import java.util.List;

/**
 * Created by mi on 2017/5/3.
 */
public class Task {
    private List<Crawler> crawlerList;
    private String taskName;
    public Task(List<Crawler> crawlerList , String taskName){
        for(Crawler crawler:crawlerList){
            crawler.setTaskName(taskName);
        }
        this.crawlerList = crawlerList;
        this.taskName = taskName;
    }
    public Task(List<Crawler> crawlerList , Script script){
        this.crawlerList = crawlerList;
        if(crawlerList!=null&&crawlerList.size()>0){
            try {
                script.work(crawlerList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Crawler> getCrawlerList() {
        return crawlerList;
    }

    public void setCrawlerList(List<Crawler> crawlerList) {
        this.crawlerList = crawlerList;
    }
}
