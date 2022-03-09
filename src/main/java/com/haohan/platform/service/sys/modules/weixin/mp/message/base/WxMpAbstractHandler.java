package com.haohan.platform.service.sys.modules.weixin.mp.message.base;

import com.haohan.platform.service.sys.common.utils.Reflections;
import com.haohan.platform.service.sys.modules.weixin.mp.base.entity.WechatUserMsgevent;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * Created by zgw on 2018/11/8.
 */
public abstract class WxMpAbstractHandler implements WxMpMessageHandler {

    private UserOpenPlatform userOpenPlatform;

    private AuthApp authApp;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        try {
            String openId = wxMessage.getFromUser();

            setUserOpenPlatform((UserOpenPlatform) context.get(openId));
            setAuthApp((AuthApp) context.get(wxMessage.getToUser()));

            String methodName = wxMessage.getMsgType().toLowerCase();
            if (WxConsts.XmlMsgType.EVENT.equalsIgnoreCase(wxMessage.getMsgType())){
                 methodName = wxMessage.getEvent().toLowerCase();
            }
            Reflections.invokeMethodByName(getObject(), methodName, new Object[]{wxMessage, context, wxMpService, sessionManager});
        } catch (IllegalArgumentException e) {

            otherDone(wxMessage, context, wxMpService, sessionManager);
        }

        return null;

    }

    protected abstract Object getObject();

    protected abstract void otherDone(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager);


    protected void configMsgContent(WxMpXmlMessage wxMessage, Map<String, Object> context, String msgContent) {
        WechatUserMsgevent wechatUser = (WechatUserMsgevent) context.get(wxMessage.getMsgId()+"");
        wechatUser.setMsgContent(msgContent);
        context.put(wechatUser.getMsgId(), wechatUser);
    }

    public UserOpenPlatform getUserOpenPlatform() {
        return userOpenPlatform;
    }

    public void setUserOpenPlatform(UserOpenPlatform userOpenPlatform) {
        this.userOpenPlatform = userOpenPlatform;
    }

    public AuthApp getAuthApp() {
        return authApp;
    }

    public void setAuthApp(AuthApp authApp) {
        this.authApp = authApp;
    }
}
