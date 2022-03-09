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
                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenApiService.fetchByAppId(appId).getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody, wxOpenApiService.fetchByAppId(appId).getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
        this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
        String out = "success";
        try {
            out = wxOpenApiService.fetchByAppId(appId).getWxOpenComponentService().route(inMessage);
        } catch (WxErrorException e) {
            this.logger.error("receive_ticket", e);
        }

        this.logger.debug("\n组装回复信息：{}", out);

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
                    "\n接收微信请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                            + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                    appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
            if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenApiService.fetchByAppId(componentId).getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
                throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
            }



            //验证OpenApiService
            WxOpenApiService apiService = wxOpenApiService.fetchByAppId(componentId);
            if (null == apiService) {
                return "";
            }

            // aes加密的消息
            WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody, apiService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
            this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());

            //验证componentService
            WxOpenComponentService wxOpenComponentService = apiService.getWxOpenComponentService();
            if (null == wxOpenComponentService) {
                return "";
            }


            String out = "";

            // 全网发布测试用例
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

            WxMpService wxMpService = null;//微信公众号服务
            WxMaService wxMaService = null;//微信小程序服务

            //业务处理
            AuthApp authApp = authAppService.fetchByAppId(appId);
            if (null == authApp) {
                return out;
            }
            //是否微信公众号
            boolean isMpWx;
            //微信用户消息事件
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

            //处理消息
            Map<String, Object> context = new HashMap<>();

            //用户是否存在
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
                    //小程序前端登录后则创建完账户信息,此处不作任何操作
                }
            }
            userOpen = userOpenPlatformService.fetchByAppIdAndOpenId(appId, openId);
            //创建用户失败
            if(null == userOpen){
                return out;
            }
            //将应用信息放入上下文
            context.put(inMessage.getToUser(), authApp);
            context.put(openId, userOpen);
//                private String msgId;		// 消息ID
//                private String openWxName;		// 授权微信名称
//                private String openWxId;		// 授权微信ID
//                private String wxId;		// 微信ID
//                private String wxName;		// 微信名称
//                private String wxType;		// 微信类型
//                private String passportUid;		// 用户通行证ID
//                private String openUid;		// 开放用户ID
//                private String nickName;		// 用户昵称
//                private String albumUrl;		// 用户头像
//                private String openId;		// 用户openid
//                private String unionId;		// 用户unionid
//                private String msgType;		// 消息类型
//                private String msgName;		// 消息名称
//                private String msgContent;		// 消息内容
//                private Date sendTime;		// 发送时间
//                private String fullMsgPkg;		// 完整消息包
//                private Date beginSendTime;		// 开始 发送时间
//                private Date endSendTime;		// 结束 发送时间


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

            //普通消息
            if(StringUtils.isEmpty(inMessage.getEvent())) {
                wechatUser.setMsgType("msg");
                wechatUser.setMsgName(inMessage.getMsgType());
                wechatUser.setMsgContent(inMessage.getContent());
            }
            //事件推送
            if(WxConsts.XmlMsgType.EVENT.equalsIgnoreCase(inMessage.getMsgType())){
                wechatUser.setMsgType(inMessage.getMsgType());
                wechatUser.setMsgName(inMessage.getEvent().toLowerCase());
                wechatUser.setMsgContent(inMessage.getEventKey());
            }
            //上下文放入微信用户消息事件
            context.put(inMessage.getMsgId()+"", wechatUser);

            //微信公众号消息处理
            if (isMpWx) {
                wxMpRouterManageService.routeService(inMessage, context, wxMpService);
            }
            //微信小程序消息处理
            if (!isMpWx) {
                //客服消息处理
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
