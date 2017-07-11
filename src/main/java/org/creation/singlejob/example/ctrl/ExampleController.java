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

    @RequestMapping(value = "/memory", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object updateUserPoints(@RequestBody JSONObject object, String uid) {
        return singleJobExampleService.lockInMemory(uid, object);
    }

}
