package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.miniapp.service.WxAppMsgService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/wx/miniapp")
public class WxMiniApiController extends BaseController {
    @Autowired
    private AuthAppService authAppService;


        //获取应用二维码
    @RequestMapping(value = "fetchQrCode")
    @ResponseBody
    public String fetchQrCode(String appId,HttpServletRequest request) {

        BaseResp resp  = BaseResp.newError();

//            Map<String,Object> reqParams =  getRequestParameters(request);

        try {

            if(StringUtils.isEmpty(appId)){
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp.toJson();
            }


            //从授权应用列表获取小程序二维码地址
        AuthApp authApp =  authAppService.fetchByAppId(appId);
            if(null != authApp){
                if(StringUtils.isNotEmpty(authApp.getQrcode())) {
                    resp.putStatus(RespStatus.SUCCESS);
                    resp.setExt(authApp.getQrcode());
                    return resp.toJson();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return resp.toJson();
    }

}
