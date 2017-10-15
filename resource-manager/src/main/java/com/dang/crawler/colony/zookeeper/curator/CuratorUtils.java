package com.dang.crawler.colony.zookeeper.curator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.dang.crawler.resources.utils.PropertiesUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**http://blog.csdn.net/sqh201030412/article/details/51456143
 * curator管理zookeeper的相关方法工具类
 */
public class CuratorUtils {
    private static Logger log = LoggerFactory.getLogger(CuratorUtils.class);
    private static final String ZK_URL= PropertiesUtils.getProperty("zk.zookeeper.url");
    private static final int SESSION_TIMEOUT=PropertiesUtils.getInt("zk.zookeeper.timeout");
    private static CuratorFramework client = clientTwo();
    /**
     * 先测试玩玩
     */
    public static void main(String[] args) throws Exception {
        // nodesList(clientOne(), "/");
        //CuratorFramework client = clientTwo();
        System.out.println(client);
        //createNode("/usermanager","dang".getBytes());
        System.out.println(nodesList("/"));
        //使用clientTwo来创建一个节点空间 查看是加密
        setDataNode("/usermanager", "test writer 测试写入效果!");
        System.out.println(new String(getDataNode("/usermanager")));
        // createNode(client, "/three/two/testone");
        //  deleteDataNode(client, "/three");;
    }
    public static CuratorFramework getClient(){
        return client;
    }
    /**
     *
     * @描述：创建一个zookeeper连接---连接方式一: 最简单的连接
     */
    private static CuratorFramework clientOne(){
        //zk 地址
        String connectString = "10.125.2.44:2181";
        // 连接时间 和重试次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
        return client;
    }

    /**
     * @描述：创建一个zookeeper连接---连接方式二:优选这个
     */
    private static CuratorFramework clientTwo(){

        //默认创建的根节点是没有做权限控制的--需要自己手动加权限???----
        ACLProvider aclProvider = new ACLProvider() {
            private List<ACL> acl ;
            @Override
            public List<ACL> getDefaultAcl() {
                if(acl ==null){
                    ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL;
                    acl.clear();
                    acl.add(new ACL(Perms.ALL, new Id("auth", "admin:admin") ));
                    this.acl = acl;
                }
                return acl;
            }
            @Override
            public List<ACL> getAclForPath(String path) {
                return acl;
            }
        };
        String scheme = "digest";
        byte[] auth = "admin:admin".getBytes();
        int connectionTimeoutMs = SESSION_TIMEOUT;
        String namespace = "crawler";
        CuratorFramework client = CuratorFrameworkFactory.builder().aclProvider(aclProvider).
                authorization(scheme, auth).
                connectionTimeoutMs(connectionTimeoutMs).
                connectString(ZK_URL).
                namespace(namespace).
                retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
        client.start();
        return client;
    }

    /**
     * @描述：获取子节点列表
     */
    public static List<String> nodesList(String parentPath) throws Exception{
        List<String> paths = client.getChildren().forPath(parentPath);
        return paths;
    }
    /**
     * @描述：创建一个节点
     */
    public static String createNode(String path, byte[] bytes) throws Exception{
        Stat stat = client.checkExists().forPath(path);
        System.out.println(stat);
        String forPath = client.create().creatingParentsIfNeeded().forPath(path, bytes);
        log.info("createNode"+forPath);
        return forPath;
    }

    /**
     * 获取指定节点中信息
     * @throws Exception
     */
    public static byte[] getDataNode(String path) throws Exception{
        Stat stat = client.checkExists().forPath(path);
        if(path!=null) {
            byte[] datas = client.getData().forPath(path);
            return datas;
        }
        return null;
    }
    /**
     * @描述：设置节点中的信息
     */
    public static Stat setDataNode(String path, String message) throws Exception{
        Stat stat = client.checkExists().forPath(path);
        if(stat!=null) {
            Stat res = client.setData().forPath(path, message.getBytes());
            return res;
        }else {
            log.error("path不存在:"+path);
        }
        return null;
    }

    public static Void deleteDataNode(String path) throws Exception{
        Stat stat = client.checkExists().forPath(path);
        Void forPath = client.delete().deletingChildrenIfNeeded().forPath(path);
        return forPath;
    }
    //http://blog.csdn.net/sqh201030412/article/details/51446434
    public static byte[] addListener(String path, Watcher watcher) throws Exception{
        // 注册观察者，当节点变动时触发
        byte[] data = client.getData().usingWatcher(watcher).forPath(path);
        return data;
    }
}