package org.creation.singlejob.example.ctrl;

import javax.annotation.Resource;

import org.creation.singlejob.example.SingleJobExampleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/** 
* @author 作者 LiuPeng E-mail: 
* @version 创建时间：2017年7月10日 上午11:23:00 
* 类说明 
*/
/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2017     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2017年7月10日           </p>
 * @author LiuPeng
 * <p>Update Time: 2017年7月10日               </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@RestController
@RequestMapping("/example")
public class ExampleController {

    @Resource
    SingleJobExampleService singleJobExampleService;
    
    @RequestMapping(value = "/lockInRedis", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object lockInRedis(@RequestBody JSONObject object, String uid) {
        return singleJobExampleService.lockInRedis(uid, object);
    }
    
    @RequestMapping(value = "/lockInRedisWithCache", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object lockInRedisWithCache(@RequestBody JSONObject object, String uid) {
        return singleJobExampleService.lockInRedisWithCache(uid, object);
    }

    @RequestMapping(value = "/lockInRedisReenterable", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object lockInRedisReenterable(@RequestBody JSONObject object, String uid) {
        return singleJobExampleService.lockInRedisReenterable(uid, object, 3);
    }
    
    @RequestMapping(value = "/lockInMemoryReenterable", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object lockInMemoryReenterable(@RequestBody JSONObject object, String uid) {
        return singleJobExampleService.lockInMemoryReenterable(uid, object, 3);
    }
    @RequestMapping(value = "/lockInRedisWithCustomLockName", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object lockInRedisWithCustomLockName(@RequestBody JSONObject object, String uid) {
        return singleJobExampleService.lockInRedisWithCustomLockName(uid, object);
    }
}
