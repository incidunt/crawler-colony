package com.dang.crawler.core.fetcher.file;

import com.dang.crawler.resources.bean.core.CrawlerMQ;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by duang on 2017/4/30.
 */
public class Downloader {
    private static Downloader downloader = new Downloader();
    private Queue<CrawlerMQ> queue = new LinkedBlockingQueue<>();
    private int maxThread = 5;
    private int threadCount = 0;
    ExecutorService es = Executors.newFixedThreadPool(maxThread);
    private Downloader(){

    }
    public static Downloader getInstance(){
        return downloader;
    }
    public synchronized void addURL(CrawlerMQ mq){
        queue.add(mq);
        if(threadCount<maxThread) {
            es.execute(new DownloadThread(this));
            threadCount++;
        }
    }
    public void addURL(String url){
        CrawlerMQ crawlerMQ = new CrawlerMQ();
        crawlerMQ.getRequest().setUrl(url);
        addURL(crawlerMQ);
    }

    public synchronized CrawlerMQ getURL(){
        return queue.poll();
    }

    public int finish() {
        threadCount--;
        return threadCount;
    }
}

