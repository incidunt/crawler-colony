package com.dang.crawler.core.control.serivce.master;

import com.dang.crawler.colony.zookeeper.curator.CuratorUtils;
import com.dang.crawler.core.serivce.ApplicationContext;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dang on 17-5-10.
 */
@Service
public class MasterControl implements Runnable {
    private static Logger log = LoggerFactory.getLogger(MasterControl.class);
    @Resource
    private JobTimerThread jobTimerThread;
    @Override
    public void run() {
        if(!ApplicationContext.isColony) {
            Thread thread = new Thread(jobTimerThread);
            thread.setName("JobTimerThread");
            thread.start();
        }else {//几圈
            zkMaster();
        }
    }

    private void zkMaster() {
        LeaderSelector leaderSelector = new LeaderSelector(CuratorUtils.getClient(), "/master", new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                log.info("-------------------------------------Master-------------------------------------");
                jobTimerThread.run();//不结束，结束会重新选主
            }
        });
        leaderSelector.autoRequeue();
        leaderSelector.start();
    }
}
