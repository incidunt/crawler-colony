package com.dang.crawler.core.control.impl;

import com.dang.crawler.core.control.bean.Crawler;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

/**
 * Created by dang on 17-5-11.
 */
@Service
public class CrawlerFastButler {
    private int maxSize = 1000;
    private Queue<Crawler> queue = new ArrayDeque<Crawler>(maxSize);


    public synchronized boolean put(String s, Crawler crawler) {
        if (queue.size() < maxSize) {
            return queue.offer(crawler);//使用add报错 而offer返回false
        }
        return false;
    }


    public int putAll(String s, Collection<? extends Crawler> crawlers) {
        queue.addAll(crawlers);
        return 0;
    }


    public synchronized Crawler get(String s) {
        return queue.poll();
    }


    public int size(String key) {
        return queue.size();
    }


    public int freeSize(String s) {
        return maxSize - queue.size();
    }


}
