package com.haohan.platform.service.sys.modules.xiaodian.api.ctrl;

import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.framework.utils.IpUtils;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.HttpClientUtils;
import com.haohan.platform.service.sys.common.utils.JedisUtils;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.common.web.BaseController;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.entity.WxUserInfo;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.api.entity.open.WechatAuthResp;
import com.haohan.platform.service.sys.modules.xiaodian.api.inf.IOpenPlatformUserService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.service.UPassportService;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zgw on 2018/1/3.
 */
@Controller
@RequestMapping(value = "${frontPath}/xiaodian/api/openuser")
public class OpenPlatformUserApiCtrl extends BaseController {


    @Autowired
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private UPassportService uPassportService;
    @Autowired
    private IOpenPlatformUserService openPlatformUserService;

    @RequestMapping(value = "add")
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> reqParams = getRequestParameters(request);
        //验证参数
        try {
            String appId = (String) reqParams.get("appId");
            String code = (String) reqParams.get("code");
            //设置用户信息
            String userInfo = (String) reqParams.get("rawData");
            String encryptedData = (String) reqParams.get("encryptedData");
            String signature = (String) reqParams.get("signature");
            String iv = (String) reqParams.get("iv");
            String regIp = IpUtils.getRemoteIpAddr(request);
            BaseResp resp =  openPlatformUserService.openUserAdd(appId, code, userInfo, encryptedData, signature, iv, regIp);
            return resp.toJson();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResp.newError().toJson();
        }

    }

    /**
     * 刷新开放平台用户 sessionKey
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "flushSessionKey")
    @ResponseBody
    public BaseResp flushSessionKey(HttpServletRequest request, HttpServletResponse response) {

        BaseResp resp = BaseResp.newError();
        Map<String, Object> reqParams = getRequestParameters(request);
        //验证参数
        try {
            String appId = (String) reqParams.get("appId");
            String code = (String) reqParams.get("code");

            BaseResp wechatResp =  openPlatformUserService.wechatCheck(appId, code);
            if(!wechatResp.isSuccess()){
                return wechatResp;
            }
            WechatAuthResp authResp = (WechatAuthResp)wechatResp.getExt();
            String openId = authResp.getOpenId();
            String sessionKey = authResp.getSessionKey();

            // 查找开放平台用户 并更新sessionKey
            if(StringUtils.isNotEmpty(openId)) {
                UserOpenPlatform userOpenPlatform = userOpenPlatformService.fetchByAppIdAndOpenId(appId, openId);
                // 找到对应开放平台用户
                if (null != userOpenPlatform) {
                    resp.setExt(userOpenPlatform.getUid());
                    resp.putStatus(RespStatus.SUCCESS);
                    // 更新 sessionKey
                    if(!StringUtils.equals(sessionKey, userOpenPlatform.getAccessToken())){
                        userOpenPlatform.setAccessToken(sessionKey);
                        userOpenPlatformService.save(userOpenPlatform);
                    }
                    return resp;
                }
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResp.newError();
        }

    }

//    /**
//     * 同步即速应用Sessionkey
//     * @param appId
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "sysncJisuSid/{appId}")
//    @ResponseBody
//    public String sysncJisuSid(@PathVariable("appId")String appId, HttpServletRequest request) {
//
//        BaseResp resp = new BaseResp();
//        //验证参数
//        try {
//            Map<String, Object> reqParams = getRequestParameters(request);
//
//            String uid = (String) reqParams.get("uid");
//            String sessionKey  = (String) reqParams.get("sessionKey");
//            String jisuAppId = (String) reqParams.get("jisuAppId");
//
//            Map<String, Object> requiredParams = new HashMap<String, Object>();
//
//            requiredParams.put("uid",uid);
//            requiredParams.put("jisuAppId",jisuAppId);
//            requiredParams.put("sessionKey",sessionKey);
//            requiredParams.put("appId",appId);
//
//            //错误处理
//            resp = paramsValid(requiredParams);
//            if (!resp.isSuccess()) {
//                return resp.toJson();
//            }
//
//            //TODO 验证APPID 合法性
//
//            //设置缓存值
//            CacheUtils.put(IJisuAppService.cachekey,jisuAppId,sessionKey);
//
//            resp.putStatus(RespStatus.SUCCESS);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            return resp.toJson();
//        }
//
//    }


    @RequestMapping(value = "appUserQuery/{uid}")
    @ResponseBody
    public String appUserQuery(@PathVariable("uid") String uid, HttpServletRequest request) {

        BaseResp resp = new BaseResp();


        try {
            Map<String, Object> reqParams = getRequestParameters(request);

            String appId = (String) reqParams.get("appId");
            Map<String, Object> requiredParams = new HashMap<String, Object>();
            requiredParams.put("appId",appId);
            requiredParams.put("uid",uid);
            //错误处理
            resp = paramsValid(requiredParams);
            if (!resp.isSuccess()) {
                return resp.toJson();
            }

            UserOpenPlatform userOpenPlatform  = userOpenPlatformService.fetchByAppIdAndUid(appId,uid);
            resp.putStatus(RespStatus.SUCCESS);
            resp.setExt(userOpenPlatform);

            return resp.toJson();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resp.toJson();
        }

    }


    @RequestMapping(value = "passportQuery/{uid}")
    @ResponseBody
    public String passportQuery(@PathVariable("uid") String uid, HttpServletRequest request) {

        BaseResp resp = new BaseResp();

        try {

            UPassport passport = uPassportService.get(uid);
            resp.putStatus(RespStatus.SUCCESS);
            resp.setExt(passport);

            return resp.toJson();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resp.toJson();
        }


    }


    @RequestMapping(value = "MerchantBind")
    @ResponseBody
    public BaseResp merchantBind(HttpServletRequest request){
        BaseResp resp = BaseResp.newError();

        try {
            Map<String, Object> reqMap = getRequestParameters(request);
            String appId = (String) reqMap.get("appId");
            String code = (String) reqMap.get("code");
            //设置用户信息
            String userInfo = (String) reqMap.get("rawData");
            String mobile = (String) reqMap.get("mobile");
            String validCode = (String) reqMap.get("validCode");

            String originCode = JedisUtils.get(mobile);
            if(!StringUtils.equals(originCode,validCode)){
                resp.putStatus(RespStatus.ERROR);
                resp.setMsg("验证码错误,请重新获取!");
                return resp;
            }



            WxUserInfo wxUserInfo = JacksonUtils.readValue(userInfo, WxUserInfo.class);



        }catch (Exception e){
            e.printStackTrace();;
        }

        return resp;
    }


    @Deprecated
    @RequestMapping(value = "syncUser")
    @ResponseBody
    public String syncUser(UserOpenPlatform userOpen, HttpServletRequest request) {

        BaseResp resp = new BaseResp();
//        UserOpenPlatform userOpen = new UserOpenPlatform();
        //验证参数
        try {
            Map<String, Object> reqParams = getRequestParameters(request);

            String appId = (String) reqParams.get("appId");
            String openid = (String) reqParams.get("openid");

            UserOpenPlatform userOpenPlatform = userOpenPlatformService.fetchByAppIdAndOpenId(appId, openid);
            if (null != userOpenPlatform) {
//                BeanUtils.copyProperties(userOpenPlatform, userOpen);
                userOpenPlatform.setAccessToken(userOpen.getAccessToken());
                userOpenPlatformService.save(userOpenPlatform);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return resp.toJson();
        }
    }


}
