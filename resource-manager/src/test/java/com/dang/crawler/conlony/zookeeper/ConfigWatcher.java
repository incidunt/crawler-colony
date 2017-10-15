package com.dang.crawler.conlony.zookeeper;
import java.io.IOException;

import com.dang.crawler.colony.zookeeper.ZKClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ConfigWatcher implements Watcher{
    private ZKClient zkClient;

    @Override
    public void process(WatchedEvent event) {
        if(event.getType()==EventType.NodeDataChanged){
            try{
                dispalyConfig();
            }catch(InterruptedException e){
                System.err.println("Interrupted. exiting. ");
                Thread.currentThread().interrupt();
            }catch(KeeperException e){
                System.out.printf("KeeperException锛?s. Exiting.\n", e);
            }

        }

    }
    public ConfigWatcher() throws IOException, InterruptedException {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watcher :"+watchedEvent);
            }
        };
        zkClient = new ZKClient();
    }
    public void dispalyConfig() throws KeeperException, InterruptedException{
        String value=zkClient.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s\n",ConfigUpdater.PATH,value);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ConfigWatcher configWatcher = new ConfigWatcher();
        configWatcher.dispalyConfig();
        //stay alive until process is killed or Thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }
}