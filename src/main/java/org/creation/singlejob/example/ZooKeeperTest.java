package org.creation.singlejob.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.concurrent.CountDownLatch;

/**
 * @author 作者 LiuPeng E-mail:
 * @version 创建时间：2017年7月18日 上午10:03:37
 * 类说明
 */

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2017     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2017年7月18日           </p>
 *
 * @author LiuPeng
 *         <p>Update Time: 2017年7月18日               </p>
 *         <p>Updater:                          </p>
 *         <p>Update Comments:                  </p>
 */
public class ZooKeeperTest {
    private static final int TIME_OUT = 3000000;
    private static final String HOST = "localhost:2181";

    public static void waitUntilConnected(ZooKeeper zooKeeper, CountDownLatch connectedLatch) {
        if (ZooKeeper.States.CONNECTING == zooKeeper.getState()) {
            try {
                connectedLatch.await();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    static class ConnectedWatcher implements Watcher {

        private CountDownLatch connectedLatch;

        ConnectedWatcher(CountDownLatch connectedLatch) {
            this.connectedLatch = connectedLatch;
        }

        @Override
        public void process(WatchedEvent event) {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectedLatch.countDown();
            }
        }
    }
    static class EventWatcher implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            System.out.println(event.getPath()+event.toString());
        }
    }

    public static void main(String[] args) {
        ZooKeeper zookeeper = null;
        try{
            CountDownLatch connectedLatch = new CountDownLatch(1);
            Watcher watcher = new ConnectedWatcher(connectedLatch);
            zookeeper = new ZooKeeper(HOST, TIME_OUT, watcher);
            waitUntilConnected(zookeeper, connectedLatch);
        }
        catch(Exception e){
            System.out.println(e);
        }
        try {
            //zookeeper = new ZooKeeper(HOST, TIME_OUT, null);
            int i = 0;
            do {
                try {
                    System.out.println("=========创建节点===========");
                    if (zookeeper.exists("/test", false) == null) {
                        zookeeper.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }
                    System.out.println("=============查看节点是否安装成功===============");
                    System.out.println(new String(zookeeper.getData("/test", new EventWatcher(),null)));

                    System.out.println("=========修改节点的数据==========");
                    String data = "zNode2";
                    zookeeper.setData("/test", data.getBytes(), -1);

                    System.out.println("========查看修改的节点是否成功=========");
                    System.out.println(new String(zookeeper.getData("/test", false, null)));

                    System.out.println("=======删除节点==========");
                    zookeeper.delete("/test", -1);

                    System.out.println("==========查看节点是否被删除============");
                    System.out.println("节点状态：" + zookeeper.exists("/test", false));
                } catch (KeeperException.ConnectionLossException e) {
                    continue;
                } catch (Exception e) {
                e.printStackTrace();
            }
            } while (100>i);

            zookeeper.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

