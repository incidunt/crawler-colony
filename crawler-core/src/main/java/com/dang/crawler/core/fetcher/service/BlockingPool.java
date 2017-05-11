package com.dang.crawler.core.fetcher.service;

import java.util.ArrayDeque;
import java.util.Queue;
/**
 * Created by dang on 17-5-10.
 * 资源池
 */
public abstract class BlockingPool<T> {
    private final int minPoolSize;
    private final int maxPoolSize;
    private int size = 0;
    private Queue<T> freeQueue;
    public BlockingPool(int minPoolSize,int maxPoolSize){
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        freeQueue = new ArrayDeque<T>(maxPoolSize);
    }
    public synchronized T getFree() {
        while (true) {
            if (freeQueue.size() > 0) {
                size++;
                return freeQueue.poll();
            } else if (size < maxPoolSize) {
                size++;
                return make();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public synchronized void toFree(T t){
        size--;
        if(freeQueue.size()<minPoolSize) {
            freeQueue.add(t);
            notifyAll();
        }else {
            destroy(t);
        }
    }

    protected abstract void destroy(T t);

    protected abstract T make();

}
