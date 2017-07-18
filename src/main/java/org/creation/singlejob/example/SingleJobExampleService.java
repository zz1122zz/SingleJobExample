package org.creation.singlejob.example;

import javax.annotation.Resource;

import org.creation.singlejob.SingleJob;
import org.creation.singlejob.SingleJobPolicy;
import org.creation.singlejob.SingleJobType;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 作者 LiuPeng E-mail:
 * @version 创建时间：2017年7月10日 下午1:41:34
 * 类说明
 */

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2017     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2017年7月10日           </p>
 *
 * @author LiuPeng
 *         <p>Update Time: 2017年7月10日               </p>
 *         <p>Updater:                          </p>
 *         <p>Update Comments:                  </p>
 */
@Service
public class SingleJobExampleService {
    @Resource
    SingleJobExampleService singleJobExampleService;
    
    /**
     * <p>Description:  展示ZooKeeper锁  </p>
     * <p>Create Time: 2017年7月11日   </p>
     * <p>Create author: LiuPeng   </p>
     *
     * @param uid
     * @param object
     * @return
     */
    @SingleJob(distinction = "#uid+#object.getString(\"key\")", singleJobDataPersistenceProvider = "zooKeeperSingleJobDataPersistenceProvider", singleJobPolicy = SingleJobPolicy.WAIT_IN_QUENE_TO_PROCEED)
    public String lockInZooKeeper(String uid, JSONObject object) {
        return "cache at:" + new DateTime().toString("MMdd HH:mm:SS");
    }
    
    /**
     * <p>Description:  展示ZooKeeper锁  </p>
     * <p>Create Time: 2017年7月11日   </p>
     * <p>Create author: LiuPeng   </p>
     *
     * @param uid
     * @param object
     * @return
     */
    @SingleJob(distinction = "#uid+#object.getString(\"key\")", readCacheIfExist = true, singleJobDataPersistenceProvider = "zooKeeperSingleJobDataPersistenceProvider", singleJobPolicy = SingleJobPolicy.WAIT_IN_QUENE_TO_PROCEED)
    public String lockInZooKeeperWithCache(String uid, JSONObject object) {
        return "cache at:" + new DateTime().toString("MMdd HH:mm:SS");
    }
    /**
     * <p>Description:  展示redis锁  </p>
     * <p>Create Time: 2017年7月11日   </p>
     * <p>Create author: LiuPeng   </p>
     *
     * @param uid
     * @param object
     * @return
     */
    @SingleJob(distinction = "#uid+#object.getString(\"key\")", singleJobDataPersistenceProvider = "redisSingleJobDataPersistenceProvider", singleJobPolicy = SingleJobPolicy.WAIT_IN_QUENE_TO_PROCEED)
    public String lockInRedis(String uid, JSONObject object) {
        return "cache at:" + new DateTime().toString("MMdd HH:mm:SS");
    }

    /**
     * <p>Description:  展示redis锁  </p>
     * <p>Create Time: 2017年7月11日   </p>
     * <p>Create author: LiuPeng   </p>
     *
     * @param uid
     * @param object
     * @return
     */
    @SingleJob(distinction = "#uid+#object.getString(\"key\")", readCacheIfExist = true, singleJobDataPersistenceProvider = "redisSingleJobDataPersistenceProvider", singleJobPolicy = SingleJobPolicy.WAIT_IN_QUENE_TO_PROCEED)
    public String lockInRedisWithCache(String uid, JSONObject object) {
        return "cache at:" + new DateTime().toString("MMdd HH:mm:SS");
    }

    /**
     * <p>Description:      展示redis锁的可重入性        </p>
     * <p>Create Time: 2017年7月11日   </p>
     * <p>Create author: LiuPeng   </p>
     *
     * @param uid
     * @param object
     * @param reenter
     * @return
     */
    @SingleJob(distinction = "#uid+#object.getString(\"key\")", singleJobDataPersistenceProvider = "redisSingleJobDataPersistenceProvider", singleJobPolicy = SingleJobPolicy.WAIT_IN_QUENE_TO_PROCEED)
    public String lockInRedisReenterable(String uid, JSONObject object, int reenter) {
        if (reenter > 0) {
            //SingleJobExampleService aop = (SingleJobExampleService) AopContext.currentProxy();
            //return aop.lockInRedisReenterable(uid, object,reenter-1);
            return singleJobExampleService.lockInRedisReenterable(uid, object, reenter - 1);
        } else {
            return "cache at:" + new DateTime().toString("MMdd HH:mm:SS");
        }
    }

    /**
     * <p>Description:      展示redis锁的可重入性        </p>
     * <p>Create Time: 2017年7月11日   </p>
     * <p>Create author: LiuPeng   </p>
     *
     * @param uid
     * @param object
     * @param reenter
     * @return
     */
    @SingleJob(distinction = "#uid+#object.getString(\"key\")", singleJobDataPersistenceProvider = "localMemoryDataPersistenceProvider", singleJobPolicy = SingleJobPolicy.WAIT_IN_QUENE_TO_PROCEED)
    public String lockInMemoryReenterable(String uid, JSONObject object, int reenter) {
        if (reenter > 0) {
            return singleJobExampleService.lockInMemoryReenterable(uid, object, reenter - 1);
        } else {
            return "cache at:" + new DateTime().toString("MMdd HH:mm:SS");
        }
    }

    /**
     * <p>Description:  展示自定义业务锁  </p>
     * <p>Create Time: 2017年7月11日   </p>
     * <p>Create author: LiuPeng   </p>
     *
     * @param uid
     * @param object
     * @return
     */
    @SingleJob(distinction = "#uid+#object.getString(\"key\")", type = SingleJobType.LOCK_BY_CUSTOM_LOCKNAME, lockName = "MyCustomName", singleJobDataPersistenceProvider = "redisSingleJobDataPersistenceProvider", singleJobPolicy = SingleJobPolicy.WAIT_IN_QUENE_TO_PROCEED)
    public String lockInRedisWithCustomLockName(String uid, JSONObject object) {
        return "cache at:" + new DateTime().toString("MMdd HH:mm:SS");
    }
}
