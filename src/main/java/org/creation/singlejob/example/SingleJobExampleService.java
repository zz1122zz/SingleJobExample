package org.creation.singlejob.example;

import org.creation.singlejob.SingleJob;
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
 * @author LiuPeng
 * <p>Update Time: 2017年7月10日               </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class SingleJobExampleService {

    @SingleJob(distinction = "#uid")
    public String lockInMemory(String uid,JSONObject object)
    {
        return null;
    }
}
