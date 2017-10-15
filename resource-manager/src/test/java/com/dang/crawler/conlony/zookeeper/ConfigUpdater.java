package com.dang.crawler.conlony.zookeeper;

/**
 * Created by dang on 17-6-5.
 */
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.dang.crawler.colony.zookeeper.ZKClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class ConfigUpdater {

    public static final String  PATH="/zk";

    private ZKClient zkClient;
    private Random random=new Random();

    public ConfigUpdater() throws IOException, InterruptedException {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watcher :"+watchedEvent);
            }
        };
        zkClient = new ZKClient();
    }
    public void run() throws InterruptedException, KeeperException{
        while(true){
            String value=random.nextInt(100)+"";
            zkClient.write(PATH, value);
            System.out.printf("Set %s to %s\n",PATH,value);
            TimeUnit.SECONDS.sleep(random.nextInt(10));

        }
    }
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigUpdater configUpdater = new ConfigUpdater();
        configUpdater.run();

    }
}