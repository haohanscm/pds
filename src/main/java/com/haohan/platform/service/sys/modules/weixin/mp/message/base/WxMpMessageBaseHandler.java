package com.haohan.platform.service.sys.modules.weixin.mp.message.base;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zgw on 2018/11/5.
 */
@Service
public class WxMpMessageBaseHandler extends WxMpAbstractHandler {

    @Override
    protected Object getObject() {
        return this;
    }


    public void otherDone(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {

    }

    private void text(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getContent();
        configMsgContent(wxMessage,context,msgContent);
    }
    private void image(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getPicUrl();
        configMsgContent(wxMessage,context,msgContent);
    }
    private void voice(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getMediaId();
        configMsgContent(wxMessage, context, msgContent);
    }
    private void video(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getMediaId();
        configMsgContent(wxMessage, context, msgContent);
    }
    private void shortvideo(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getMediaId();
        configMsgContent(wxMessage, context, msgContent);
    }
    private void news(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getTitle();
        configMsgContent(wxMessage, context, msgContent);
    }
    private void music(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getMediaId();
        configMsgContent(wxMessage, context, msgContent);
    }
    private void location(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent =  wxMessage.getLongitude().toString() + "|" + wxMessage.getLatitude().toString();
        configMsgContent(wxMessage, context, msgContent);
    }
    private void link(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager)  {
        String msgContent = wxMessage.getTitle();
        configMsgContent(wxMessage, context, msgContent);
    }

    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    public static final String VOICE = "voice";
    public static final String SHORTVIDEO = "shortvideo";
    public static final String VIDEO = "video";
    public static final String NEWS = "news";
    public static final String MUSIC = "music";
    public static final String LOCATION = "location";
    public static final String LINK = "link";
    public static final String EVENT = "event";
    public static final String DEVICE_TEXT = "device_text";
    public static final String DEVICE_EVENT = "device_event";
    public static final String DEVICE_STATUS = "device_status";
    public static final String HARDWARE = "hardware";
    public static final String TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";


}
