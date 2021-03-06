package com.haohan.platform.service.sys.modules.weixin.open.api.ctrl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.common.utils.IdGen;
import com.haohan.platform.service.sys.modules.weixin.constant.IWxConstant.AppServiceType;
import com.haohan.platform.service.sys.modules.weixin.miniapp.service.WxAppKefuMsgService;
import com.haohan.platform.service.sys.modules.weixin.mp.base.entity.WechatUserMsgevent;
import com.haohan.platform.service.sys.modules.weixin.mp.base.service.WechatUserMsgeventService;
import com.haohan.platform.service.sys.modules.weixin.mp.route.WxMpRouterManageService;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@RestController
@RequestMapping("${frontPath}/open/api")
public class WechatNotifyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected WxOpenApiService wxOpenApiService;
    @Autowired
    private WxAppKefuMsgService wxAppKefuMsgService;
    @Autowired
    private WxMpRouterManageService wxMpRouterManageService;
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;
    @Autowired
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    private WechatUserMsgeventService wechatUserMsgeventService;

    @RequestMapping("notify/{appId}")
    public Object receiveTicket(@RequestBody(required = false) String requestBody, @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce, @RequestParam("signature") String signature,
                                @RequestParam(value = "encrypt_type", required = false) String encType,
                                @RequestParam(value = "msg_signature", required = false) String msgSignature,@PathVariable("appId") String appId) {
        this.logger.info(
                "\n?????????????????????[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenApiService.fetchByAppId(appId).getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("?????????????????????????????????????????????");
        }

        // aes???????????????
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody, wxOpenApiService.fetchByAppId(appId).getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        this.logger.debug("\n???????????????????????????\n{} ", inMessage.toString());
        String out = "success";
        try {
            out = wxOpenApiService.fetchByAppId(appId).getWxOpenComponentService().route(inMessage);
        } catch (WxErrorException e) {
            this.logger.error("receive_ticket", e);
        }

        this.logger.debug("\n?????????????????????{}", out);

        return out;
    }

    @RequestMapping("{componentId}/notify/{appId}/callback")
    public Object callback(@RequestBody(required = false) String requestBody,
                           @PathVariable("appId") String appId,
                           @RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("openid") String openid,
                           @RequestParam("encrypt_type") String encType,
                           @RequestParam("msg_signature") String msgSignature,
    @PathVariable("componentId") String componentId) {

        try {
            this.logger.info(
                    "\n?????????????????????[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                            + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                    appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
            if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenApiService.fetchByAppId(componentId).getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
                throw new IllegalArgumentException("?????????????????????????????????????????????");
            }



            //??????OpenApiService
            WxOpenApiService apiService = wxOpenApiService.fetchByAppId(componentId);
            if (null == apiService) {
                return "";
            }

            // aes???????????????
            WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody, apiService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
            this.logger.debug("\n???????????????????????????\n{} ", inMessage.toString());

            //??????componentService
            WxOpenComponentService wxOpenComponentService = apiService.getWxOpenComponentService();
            if (null == wxOpenComponentService) {
                return "";
            }


            String out = "";

            // ????????????????????????
            if (StringUtils.equalsAnyIgnoreCase(appId, "wx570bc396a51b8ff8", "wxd101a85aa106f53e")) {
                try {
                    if (StringUtils.equals(inMessage.getMsgType(), "text")) {
                        if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                            out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                                    WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                            .fromUser(inMessage.getToUser())
                                            .toUser(inMessage.getFromUser())
                                            .build(),
                                    apiService.getWxOpenConfigStorage()
                            );
                        } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
                            String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
                            WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser()).build();
                            apiService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                        }
                    } else if (StringUtils.equals(inMessage.getMsgType(), "event")) {
                        WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback").toUser(inMessage.getFromUser()).build();
                        apiService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                    }
                } catch (WxErrorException e) {
                    logger.error("callback", e);
                }
                return out;
            }

            WxMpService wxMpService = null;//?????????????????????
            WxMaService wxMaService = null;//?????????????????????

            //????????????
            AuthApp authApp = authAppService.fetchByAppId(appId);
            if (null == authApp) {
                return out;
            }
            //?????????????????????
            boolean isMpWx;
            //????????????????????????
            WechatUserMsgevent wechatUser = new WechatUserMsgevent();

            if (AppServiceType.wxmp.getCode().equals(authApp.getServiceType())) {
                isMpWx = true;
                wechatUser.setWxType(AppServiceType.wxmp.getCode());
                wxMpService=wxOpenComponentService.getWxMpServiceByAppid(appId);
                if (null == wxMpService) {
                    return out;
                }
            }else{
                isMpWx = false;
                wechatUser.setWxType(AppServiceType.wxapp.getCode());
                wxMaService = wxOpenComponentService.getWxMaServiceByAppid(appId);
                if (null == wxMaService) {
                    return out;
                }
            }

            //????????????
            Map<String, Object> context = new HashMap<>();

            //??????????????????
            String openId = inMessage.getFromUser();
            if(StringUtils.isEmpty(openId)){
                return out;
            }

            UserOpenPlatform userOpen = userOpenPlatformService.fetchByAppIdAndOpenId(appId, openId);
            if (null == userOpen) {
                MerchantAppManage merchantApp = merchantAppManageService.fetchByAppId(authApp.getAppId());
                String merchantId = (null == merchantApp) ? "" : merchantApp.getMerchantId();
                if(isMpWx) {
                    WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openId);
                    if(!wxMpUser.getSubscribe()){
                        return out;
                    }
                    userOpenPlatformService.addUserByWxMpUser(merchantId, authApp, wxMpUser);
                }
                if(!isMpWx){
                    //????????????????????????????????????????????????,????????????????????????
                }
            }
            userOpen = userOpenPlatformService.fetchByAppIdAndOpenId(appId, openId);
            //??????????????????
            if(null == userOpen){
                return out;
            }
            //??????????????????????????????
            context.put(inMessage.getToUser(), authApp);
            context.put(openId, userOpen);
//                private String msgId;		// ??????ID
//                private String openWxName;		// ??????????????????
//                private String openWxId;		// ????????????ID
//                private String wxId;		// ??????ID
//                private String wxName;		// ????????????
//                private String wxType;		// ????????????
//                private String passportUid;		// ???????????????ID
//                private String openUid;		// ????????????ID
//                private String nickName;		// ????????????
//                private String albumUrl;		// ????????????
//                private String openId;		// ??????openid
//                private String unionId;		// ??????unionid
//                private String msgType;		// ????????????
//                private String msgName;		// ????????????
//                private String msgContent;		// ????????????
//                private Date sendTime;		// ????????????
//                private String fullMsgPkg;		// ???????????????
//                private Date beginSendTime;		// ?????? ????????????
//                private Date endSendTime;		// ?????? ????????????


            long msgId = IdGen.randomLong();

            if(null == inMessage.getMsgId() || 0 == inMessage.getMsgId()){
                inMessage.setMsgId(msgId);
            }else{
                msgId = inMessage.getMsgId();
            }
            wechatUser.setMsgId(msgId+"");
            wechatUser.setOpenWxName(authApp.getAuthAppName());
            wechatUser.setOpenWxId(authApp.getAuthAppId());
            wechatUser.setWxId(authApp.getAppId());
            wechatUser.setWxName(authApp.getAppName());

            wechatUser.setPassportUid(userOpen.getUid());
            wechatUser.setOpenUid(userOpen.getId());
            wechatUser.setNickName(userOpen.getNickName());
            wechatUser.setAlbumUrl(userOpen.getAlbumUrl());
            wechatUser.setOpenId(userOpen.getOpenId());
            wechatUser.setUnionId(userOpen.getUnionId());
            wechatUser.setFullMsgPkg(JacksonUtils.toJson(inMessage));
            String crDate = com.haohan.framework.utils.DateUtils.fromUinxTime(inMessage.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            wechatUser.setSendTime(DateUtils.parseDate(crDate));

            //????????????
            if(StringUtils.isEmpty(inMessage.getEvent())) {
                wechatUser.setMsgType("msg");
                wechatUser.setMsgName(inMessage.getMsgType());
                wechatUser.setMsgContent(inMessage.getContent());
            }
            //????????????
            if(WxConsts.XmlMsgType.EVENT.equalsIgnoreCase(inMessage.getMsgType())){
                wechatUser.setMsgType(inMessage.getMsgType());
                wechatUser.setMsgName(inMessage.getEvent().toLowerCase());
                wechatUser.setMsgContent(inMessage.getEventKey());
            }
            //???????????????????????????????????????
            context.put(inMessage.getMsgId()+"", wechatUser);

            //???????????????????????????
            if (isMpWx) {
                wxMpRouterManageService.routeService(inMessage, context, wxMpService);
            }
            //???????????????????????????
            if (!isMpWx) {
                //??????????????????
                wxAppKefuMsgService.handle(inMessage,wxMaService);
            }
            WechatUserMsgevent userMsgevent = (WechatUserMsgevent) context.get(wechatUser.getMsgId());
            if(null != userMsgevent) {
                wechatUserMsgeventService.save(userMsgevent);
            }
            return out;
        }catch (Exception e){
            logger.error("callback", e);
            return "success";
        }

    }



}
