package com.dang.crawler.core.control.serivce.master;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dang on 17-5-10.
 */
@Service
public class MasterControl implements Runnable {
    @Resource
    private JobTimerThread jobTimerThread;
    @Override
    public void run() {
        Thread thread = new Thread(jobTimerThread);
        thread.setName("JobTimerThread");
        thread.start();
    }
}
