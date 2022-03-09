package com.haohan.platform.service.sys.modules.weixin.mp.message.base;

import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.weixin.mp.base.service.WechatUserMsgeventService;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.service.UserOpenPlatformService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.haohan.platform.service.sys.modules.xiaodian.constant.IUserConstant.OpenUserStatus.subscribe;
import static com.haohan.platform.service.sys.modules.xiaodian.constant.IUserConstant.OpenUserStatus.unsubscribe;

/**
 * Created by zgw on 2018/11/5.
 */
@Service
public class WxMpEventBaseHandler extends WxMpAbstractHandler {

    @Autowired
    private UserOpenPlatformService userOpenPlatformService;
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private WechatUserMsgeventService wechatUserMsgeventService;



    @Override
    protected Object getObject() {
        return this;
    }

    @Override
    protected void otherDone(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {

    }

    public static class EventType {
        public static final String SUBSCRIBE = "subscribe";
        public static final String UNSUBSCRIBE = "unsubscribe";
        public static final String SCAN = "SCAN";
        public static final String LOCATION = "LOCATION";
        public static final String CLICK = "CLICK";
        public static final String VIEW = "VIEW";
        public static final String MASS_SEND_JOB_FINISH = "MASSSENDJOBFINISH";
        public static final String SCANCODE_PUSH = "scancode_push";
        public static final String SCANCODE_WAITMSG = "scancode_waitmsg";
        public static final String PIC_SYSPHOTO = "pic_sysphoto";
        public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
        public static final String PIC_WEIXIN = "pic_weixin";
        public static final String LOCATION_SELECT = "location_select";
        public static final String TEMPLATE_SEND_JOB_FINISH = "TEMPLATESENDJOBFINISH";
        public static final String ENTER_AGENT = "enter_agent";
    }

//    private void otherDone(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
//    }

    private void click(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        String msgContent = wxMessage.getEventKey();
        configMsgContent(wxMessage,context,msgContent);
    }

    private void location(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        String msgContent =  wxMessage.getLongitude().toString() + "|" + wxMessage.getLatitude().toString();
        configMsgContent(wxMessage, context, msgContent);
    }

    private void scan(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {

        String msgContent = wxMessage.getEventKey();
        configMsgContent(wxMessage,context,msgContent);
    }

    private void unsubscribeScan(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
    }

    private void unsubscribe(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
        UserOpenPlatform userOpen = getUserOpenPlatform();
        if(unsubscribe.getCode().equalsIgnoreCase(userOpen.getStatus()) && null != userOpen){
            userOpen.setStatus(unsubscribe.getCode());
            userOpenPlatformService.save(userOpen);
        }

    }

    private void view(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {


    }

    private void subscribe(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {

        UserOpenPlatform userOpen = getUserOpenPlatform();
        if(!subscribe.getCode().equalsIgnoreCase(userOpen.getStatus()) && null != userOpen){
            userOpen.setStatus(subscribe.getCode());
            userOpenPlatformService.save(userOpen);
        }
        //未关注用户扫描带参数二维码
        if (StringUtils.isNotEmpty(wxMessage.getEventKey())) {
            unsubscribeScan(wxMessage, context, wxMpService, sessionManager);
        }


    }





}
