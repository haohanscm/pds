package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl.merchant.device;

import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zgw on 2018/9/26.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/merchant/device")
public class MerchantDeviceCtrl extends BaseController {




    @RequestMapping(value = "list")
    @ResponseBody
    public BaseResp list(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();
        //TODO 创建设备库表


        return baseResp;
    };



    @RequestMapping(value = "add")
    @ResponseBody
    public BaseResp add(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();

        return baseResp;
    };


    @RequestMapping(value = "delete")
    @ResponseBody
    public BaseResp delete(HttpServletRequest request){
        BaseResp baseResp = BaseResp.newError();

        return baseResp;
    };









}
