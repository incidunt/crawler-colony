package com.dang.crawler.colony.zookeeper;

import com.dang.crawler.resources.utils.PropertiesUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dang on 17-6-5.
 */
public class ZKClient {
    private static Logger log = LoggerFactory.getLogger(ZKClient.class);
    private static final String ZK_URL=PropertiesUtils.getProperty("zk.zookeeper.url");
    private static final int SESSION_TIMEOUT=PropertiesUtils.getInt("zk.zookeeper.timeout");
    private static final Charset CHARSET=Charset.forName("UTF-8");
    private static ZooKeeper zk;
    static {
        try {
            zk = new ZooKeeper(ZK_URL,SESSION_TIMEOUT,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ZooKeeper getZooKeeper(){
        return zk;
    }
    public static void main(String[] args) throws Exception {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("watcher :"+watchedEvent);
            }
        };
        ZKClient zkClient = new ZKClient();
//        zkClient.create("crawler","a".getBytes());
//        zkClient.join("crawler","a");
//        System.out.println("crawler 成员："+zkClient.list("crawler"));
        zkClient.delete("crawler");
        Thread.sleep(10000);
        zkClient.close();
    }



    //创建节点
    private static String create(String groupName, byte[] data) throws KeeperException, InterruptedException {
        String path="/"+groupName;
        if(zk.exists(path, false)== null){
            return zk.create(path, data/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        log.info("Created:"+path);
        return "";
    }
    //当程序退出时，这个组成员应当从组中被删除
    public void join(String groupName,String memberName) throws KeeperException, InterruptedException{
        String path="/"+groupName+"/"+memberName;
        String createdPath=zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        log.info("Created:"+createdPath);
    }
    public List<String> list(String groupName) throws KeeperException, InterruptedException{
        String path ="/"+groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            return children;
        } catch (KeeperException.NoNodeException e) {
            log.info("Group %s does not exist \n", groupName);
        }
        return new ArrayList<String>();
    }
    public void delete(String groupName) throws InterruptedException, KeeperException {
        String path = "/" + groupName;
        List<String> children;
        try {
            children = zk.getChildren(path, false);
            for (String child : children) {
                zk.delete(path + "/" + child, -1);
                //通过将版本号设置为-1，可以绕过这个版本检测机制，不管znode的版本号是什么而直接将其删除。ZooKeeper不支持递归的删除操作，因此在删除父节点之前必须先删除子节点。
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }
    public void write(String path,String value) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(path, false);
        if(stat==null){
            zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }else{
            zk.setData(path, value.getBytes(CHARSET),-1);
        }
    }
    public String read(String path,Watcher watch) throws KeeperException, InterruptedException{
        byte[] data = zk.getData(path, watch, null);
        return new String(data,CHARSET);

    }

    private void close() throws InterruptedException {
        zk.close();
    }
}
