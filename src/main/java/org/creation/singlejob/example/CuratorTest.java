package org.creation.singlejob.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.StandardLockInternalsDriver;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/** 
* @author 作者 LiuPeng E-mail: 
* @version 创建时间：2017年7月18日 下午1:53:27 
* 类说明 
*/
/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2017     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2017年7月18日           </p>
 * @author LiuPeng
 * <p>Update Time: 2017年7月18日               </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class CuratorTest {
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
        CuratorFramework client = CuratorFrameworkFactory.newClient(HOST, TIME_OUT, TIME_OUT, new RetryNTimes(3,30));
        
        client.start();
        
        InterProcessMutex istributedLock = new InterProcessMutex(client,"/mylock", new StandardLockInternalsDriver());  
        try {
            if(istributedLock.acquire(0, TimeUnit.MILLISECONDS))
            {
                System.out.println("get lock");
            }else
            {
                System.out.println("not get lock");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            client.create().forPath("/listen");
            client.getData().usingWatcher(new EventWatcher()).forPath("/listen");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
