package com.haohan.platform.service.sys.modules.weixin.miniapp.service;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import com.haohan.framework.constant.RespStatus;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.weixin.constant.IWxConstant;
import com.haohan.platform.service.sys.modules.weixin.miniapp.constant.IWxMiniAppConstant;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IMerchantConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.MerchantAppManage;
import com.haohan.platform.service.sys.modules.xiaodian.entity.message.WechatMessageTemplate;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantAppManageService;
import com.haohan.platform.service.sys.modules.xiaodian.service.merchant.MerchantService;
import com.haohan.platform.service.sys.modules.xiaodian.service.message.WechatMessageTemplateService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客服消息服务
 * Created by zgw on 2018/5/1.
 */
@Service
public class WxAppKefuMsgService implements IWxMiniAppConstant {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private WxAppBaseService wxAppBaseService;
    @Autowired
    private MerchantAppManageService merchantAppManageService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private WechatMessageTemplateService wechatMessageTemplateService;

    public BaseResp handle(WxMpXmlMessage inMessage,WxMaService wxMaService) {


        BaseResp resp = BaseResp.newError();

        try {
            String ghId = inMessage.getToUser();
            if(StringUtils.isEmpty(ghId)){
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp;
            }
            //查询对应的应用

        AuthApp authApp = authAppService.fetchByAppGhId(ghId);
            if(null == authApp){
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp;
            }

//            获取应用消息服务
            WxMaMsgService wxMaMsgService =  wxMaService.getMsgService();

            //获取应用通用客服消息模板
            WechatMessageTemplate messageTemplate = wechatMessageTemplateService.fetchByAppIdAndMsgType(authApp.getAppId(), IWxConstant.WechatMsgType.KefuMessageNotify.getCode());

            if(null == messageTemplate || IMerchantConstant.disabled.equals(messageTemplate.getStatus())){
                resp.setMsg("不处理消息");
                return resp;
            }

            MerchantAppManage merchantAppManage =  merchantAppManageService.fetchByAppId(authApp.getAppId());
            if(null == merchantAppManage){
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp;
            }
            Merchant merchant = merchantService.get(merchantAppManage.getMerchantId());
            String kefuMsg = messageTemplate.getRemarks();
            if (null == kefuMsg){
                kefuMsg = "亲!需要帮助请拨打电话:"+merchant.getTelephone();
            }

            if(null == merchant){
                resp.putStatus(RespStatus.DATA_RECODE_IS_NULL);
                return resp;
            }
            WxMaKefuMessage message = WxMaKefuMessage.newTextBuilder()
                    .toUser(inMessage.getFromUser())
                    .content(kefuMsg)
                    .build();
            wxMaMsgService.sendKefuMsg(message);

        }catch (Exception e){
            resp.setMsg(e.getMessage());
        }finally {
            return resp;
        }

    }
}
