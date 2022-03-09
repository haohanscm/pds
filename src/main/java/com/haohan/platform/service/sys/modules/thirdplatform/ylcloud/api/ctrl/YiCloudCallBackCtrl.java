package com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.ctrl;

import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.thirdplatform.ylcloud.api.entity.req.YiCloudPrintCallBackReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/11/26
 */
@Controller
@RequestMapping(value = "${frontPath}/openplatform/ylcloud/api/callback")
public class YiCloudCallBackCtrl extends BaseController {

    //打印完成
    @RequestMapping(value = "print/success")
    @ResponseBody
    public String printSuccess(YiCloudPrintCallBackReq callBackReq,HttpServletRequest request){
//        Map<String,Object> reqMap = getRequestParameters(request);
//        String machineCode = (String) reqMap.get("machine_code");
//        String orderId = (String) reqMap.get("order_id");
//        String state = (String) reqMap.get("state");
//        String printTime = (String) reqMap.get("print_time");
//        String originId = (String) reqMap.get("origin_id");
//        String pushTime = (String) reqMap.get("push_time");
//        String sign = (String) reqMap.get("sign");
        if (null != callBackReq){
            //TODO 验签和处理

        }



        HashMap<String,Object> respMap = new HashMap<>();
        respMap.put("data","OK");
        return JacksonUtils.toJson(respMap);
    }

    //接单拒单

    //按键请求

    //应用菜单

    //打印机实时状态

}
