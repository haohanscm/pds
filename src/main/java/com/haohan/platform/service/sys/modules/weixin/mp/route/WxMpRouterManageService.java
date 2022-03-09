package com.haohan.platform.service.sys.modules.weixin.mp.route;

import com.haohan.platform.service.sys.modules.weixin.constant.IWxConstant;
import com.haohan.platform.service.sys.modules.weixin.mp.message.base.WxMpEventBaseHandler;
import com.haohan.platform.service.sys.modules.weixin.mp.message.base.WxMpMessageBaseHandler;
import com.haohan.platform.service.sys.modules.weixin.mp.route.config.WxMpRouterConfig;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgw on 2018/11/2.
 */
@Service
@Lazy
public class WxMpRouterManageService {

    @Resource
    private AuthAppService authAppService;

    @Autowired
    protected WxOpenApiService wxOpenApiService;

    @Autowired
    private WxMpEventBaseHandler wxMpEventBaseHandler;
    @Autowired
    private WxMpMessageBaseHandler wxMpMessageBaseHandler;


    private static Map<String, WxMpMessageRouter> wxMpMessageRouterMap = new HashMap<>();


    @PostConstruct
    public void init() {

        AuthApp authApp = new AuthApp();
        authApp.setServiceType(IWxConstant.AppServiceType.wxmp.getCode());
        List<AuthApp> authApps = authAppService.findList(authApp);
        WxOpenApiService apiService = wxOpenApiService;
        for (AuthApp app : authApps) {
            WxMpService wxMpService = null;
            apiService = apiService.fetchByAppId(app.getAuthAppId());
            if (null != apiService) {
                WxOpenComponentService wxOpenComponentService = apiService.getWxOpenComponentService();
                if (null != wxOpenComponentService) {
                    wxMpService = wxOpenComponentService.getWxMpServiceByAppid(app.getAppId());
                }
                if (null != wxMpService) {
                    //构造每个微信公众号静态路由
                    final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);

                    //默认微信路由
                    buildBaseRouter(router);

                    //自定义微信路由
                    WxMpRouterConfig.config(app.getAppId(), router);

                    wxMpMessageRouterMap.put(app.getOriginalAppid(), router);
                }

            }else{
                apiService = wxOpenApiService;
            }
        }
    }


    private void buildBaseRouter(WxMpMessageRouter router) {
        router
                // 文本消息
                .rule()
                .msgType(WxConsts.XmlMsgType.TEXT)
                .handler(wxMpMessageBaseHandler)
                .end()
                // 图片消息
                .rule()
                .msgType(WxConsts.XmlMsgType.IMAGE)
                .handler(wxMpMessageBaseHandler)
                .end()
                // 语音消息
                .rule()
                .msgType(WxConsts.XmlMsgType.VOICE)
                .handler(wxMpMessageBaseHandler)
                .next()
                // 视频消息
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.VIDEO)
                .handler(wxMpMessageBaseHandler)
                .end()
                // 小视频消息 msgType 为 shortvideo
                .rule()
                .msgType(WxConsts.XmlMsgType.SHORTVIDEO)
                .handler(wxMpMessageBaseHandler)
                .end()
                // 地理位置消息
                .rule()
                .msgType(WxConsts.XmlMsgType.LOCATION)
                .handler(wxMpMessageBaseHandler)
                .end()
                // 链接消息
                .rule()
                .msgType(WxConsts.XmlMsgType.LINK)
                .handler(wxMpMessageBaseHandler)
                .end()

                //关注事件
                .rule()
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(wxMpEventBaseHandler)
                .end()

                //取消关注事件
                .rule()
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(wxMpEventBaseHandler)
                .end()

                //默认处理
                .rule()
                .handler(wxMpMessageBaseHandler)
                .end()
        ;
    }


    public WxMpMessageRouter fetchWxMpRouter(String origAppId) {
        return wxMpMessageRouterMap.get(origAppId);
    }

    public void routeService(WxMpXmlMessage inMessage, final Map<String, Object> context, WxMpService wxMpService) {
        WxMpMessageRouter router = fetchWxMpRouter(inMessage.getToUser());
        if (null != router) {
            router.route(inMessage, context, wxMpService);
        }
    }

}
