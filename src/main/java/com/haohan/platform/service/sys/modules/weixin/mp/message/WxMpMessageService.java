package com.haohan.platform.service.sys.modules.weixin.mp.message;

import com.google.common.collect.Lists;
import com.haohan.framework.entity.BaseResp;
import com.haohan.platform.service.sys.common.utils.DateUtils;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant;
import com.haohan.platform.service.sys.modules.pds.constant.IPdsConstant.BuySeq;
import com.haohan.platform.service.sys.modules.pds.entity.delivery.TruckManage;
import com.haohan.platform.service.sys.modules.pds.entity.order.BuyOrder;
import com.haohan.platform.service.sys.modules.sys.utils.DictUtils;
import com.haohan.platform.service.sys.modules.weixin.constant.IWxConstant.WechatMsgType;
import com.haohan.platform.service.sys.modules.weixin.open.api.service.WxOpenApiService;
import com.haohan.platform.service.sys.modules.weixin.open.entity.AuthApp;
import com.haohan.platform.service.sys.modules.weixin.open.service.AuthAppService;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UserOpenPlatform;
import com.haohan.platform.service.sys.modules.xiaodian.entity.common.MerchantEmployee;
import com.haohan.platform.service.sys.modules.xiaodian.entity.message.WechatMessageTemplate;
import com.haohan.platform.service.sys.modules.xiaodian.service.message.WechatMessageTemplateService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.haohan.platform.service.sys.modules.weixin.constant.IWxConstant.WechatMsgType.*;

/**
 * Created by zgw on 2018/11/6.
 * 微信公众号发送消息
 */
@Service
public class WxMpMessageService {

    @Autowired
    private WxOpenApiService wxOpenApiService;
    @Autowired
    private AuthAppService authAppService;
    @Autowired
    private WechatMessageTemplateService messageTemplateService;

    /**
     * 采购商订单待确认消息发送
     *
     * @param userOpen
     * @param buyOrder
     */
    public Boolean confirmBuyOrderNotify(UserOpenPlatform userOpen, BuyOrder buyOrder) {

        Boolean result = false;
        String wxMpAppId = userOpen.getAppId();
        //构造消息
        WxMpTemplateMessage templateMessage = genTemplateMessage(userOpen,buyOrder,OrderConfirmNotify);
        if(null == templateMessage){
            return result;
        }

        WxMpTemplateMsgService msgService =  getWxMpTemplateMsgService(wxMpAppId);


        //创建消息模板
        List<WxMpTemplateData> dataList = new ArrayList<>();

        WxMpTemplateData data1 = new WxMpTemplateData("first", "您收到一条待确认订单", "#173177");
        WxMpTemplateData data2 = new WxMpTemplateData("keyword1", buyOrder.getBuyId(), "#173177");
        WxMpTemplateData data3 = new WxMpTemplateData("keyword2", buyOrder.getTotalPrice() + " 元", "#fe4e4e");
        WxMpTemplateData data4 = new WxMpTemplateData("keyword3", buyOrder.getAddress(), "#173177");
        WxMpTemplateData data5 = new WxMpTemplateData("keyword4", buyOrder.getBuyerName(), "#173177");
        WxMpTemplateData data6 = new WxMpTemplateData("keyword5", DateUtils.formatDate(buyOrder.getDeliveryTime(), "yyyy-MM-dd HH:mm"), "#173177");
        WxMpTemplateData data7 = new WxMpTemplateData("remark", "您的采购商品已完成报价,请进入系统确认!", "#173177");

        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        dataList.add(data4);
        dataList.add(data5);
        dataList.add(data6);
        dataList.add(data7);

        templateMessage.setData(dataList);

        try {
            //发送消息
            msgService.sendTemplateMsg(templateMessage);
            result = true;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 订单配送通知
     *
     * @param userOpen
     * @param buyOrder
     */
    public Boolean orderDeliveryNotify(UserOpenPlatform userOpen, BuyOrder buyOrder, TruckManage truckManage) {
        Boolean result = false;

        WxMpTemplateMessage templateMessage = genTemplateMessage(userOpen,buyOrder,OrderDeliveryNotify);
        if(null == templateMessage){
            return result;
        }

        List<WxMpTemplateData> dataList = Lists.newArrayList(
                    new WxMpTemplateData("first", "您采购的货品已经发出,请等待收货!", "#173177"),
                    new WxMpTemplateData("keyword1", buyOrder.getBuyId(), "#173177"),
                    new WxMpTemplateData("keyword2", DateUtils.getDate("yyyy-MM-dd HH:mm"), "#173177"),
                    new WxMpTemplateData("keyword3", truckManage.getDriver(), "#173177"),
                    new WxMpTemplateData("keyword4", truckManage.getTelephone(), "#173177"),
                    new WxMpTemplateData("keyword5", truckManage.getTruckNo(), "#173177"),
                    new WxMpTemplateData("remark", "了解更多详情,请进入系统查看.", "#173177")
        );
        templateMessage.setData(dataList);

        WxMpTemplateMsgService msgService =  getWxMpTemplateMsgService(userOpen.getAppId());
        try {
            msgService.sendTemplateMsg(templateMessage);
            result = true;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 供应商备货通知
     * @param userOpen
     */
    public BaseResp supPrepareNotify(UserOpenPlatform userOpen, String orderIds , String content, MerchantEmployee merchantEmployee,Date deliveryDate,String buySeq){
        BaseResp baseResp = BaseResp.newError();
        WxMpTemplateMessage templateMessage = genPrepareTplMessage(userOpen,deliveryDate,buySeq,WechatMsgType.PrepareGoodsNotify);
        if(null == templateMessage){
            return baseResp;
        }

        List<WxMpTemplateData> dataList = Lists.newArrayList(
                new WxMpTemplateData("first", "您有一张新订单已确认,请尽快完成备货!", "#173177"),
                new WxMpTemplateData("keyword1", orderIds, "#173177"),
                new WxMpTemplateData("keyword2", content, "#173177"),
                new WxMpTemplateData("keyword3", DateUtils.getDate("yyyy-MM-dd HH:mm"), "#173177"),
                new WxMpTemplateData("keyword4", merchantEmployee.getName(), "#173177"),
                new WxMpTemplateData("keyword5", merchantEmployee.getTelephone(), "#173177"),
                new WxMpTemplateData("remark", "订单详情请点击进入系统查看", "#173177")
        );
        templateMessage.setData(dataList);

        WxMpTemplateMsgService msgService =  getWxMpTemplateMsgService(userOpen.getAppId());
        try {
            msgService.sendTemplateMsg(templateMessage);
            baseResp.success();
        } catch (WxErrorException e) {
            e.printStackTrace();
            baseResp.setMsg(e.getMessage());
        }
        return baseResp;
    }

    /**
     * 订单提货通知
     * @param userOpen
     * @param buyOrder
     */
    public Boolean orderSelfTakeNotify(UserOpenPlatform userOpen, BuyOrder buyOrder) {
        Boolean result = false;
        WxMpTemplateMessage templateMessage = genTemplateMessage(userOpen,buyOrder,OrderSelfTakeNotify);
        if(null == templateMessage){
            return result;
        }

        String selfAddress = DictUtils.getDictLabel("0","pds_self_order_address","");
        List<WxMpTemplateData> dataList = Lists.newArrayList(
                new WxMpTemplateData("first", "您采购的货品已到达自提点,请及时取货!", "#173177"),
                new WxMpTemplateData("keyword1", buyOrder.getBuyId(), "#173177"),
                new WxMpTemplateData("keyword2", DateUtils.getDate("yyyy-MM-dd HH:mm"), "#173177"),
                new WxMpTemplateData("keyword3", "待提货", "#173177"),
                new WxMpTemplateData("keyword4", selfAddress, "#173177"),
                new WxMpTemplateData("remark", "了解更多详情,请进入系统查看.", "#173177")
        );
        templateMessage.setData(dataList);

        WxMpTemplateMsgService msgService =  getWxMpTemplateMsgService(userOpen.getAppId());
        try {
            msgService.sendTemplateMsg(templateMessage);
            result = true;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 订单交易完成通知
     * @param userOpen
     * @param buyOrder
     */
    public Boolean orderDealCloseNotify(UserOpenPlatform userOpen, BuyOrder buyOrder) {
        Boolean result = false;
        //构造消息
        WxMpTemplateMessage templateMessage = genTemplateMessage(userOpen,buyOrder,OrderDealCloseNotify);
        if(null == templateMessage){
            return result;
        }
        String wxMpAppId = userOpen.getAppId();
        WxMpTemplateMsgService msgService =  getWxMpTemplateMsgService(wxMpAppId);



      String seqName =  BuySeq.valueOfBuySeq(buyOrder.getBuySeq()).getDesc();
      String buyTime = DateUtils.formatDate(buyOrder.getDeliveryTime(), "yyyy-MM-dd");
        List<WxMpTemplateData> dataList = Lists.newArrayList(
                new WxMpTemplateData("first", "采购订单交易完成通知", "#173177"),
                new WxMpTemplateData("keyword1", buyOrder.getBuyId(), "#173177"),
                new WxMpTemplateData("keyword2", buyTime+" "+seqName+"货品", "#173177"),
                new WxMpTemplateData("keyword3", buyOrder.getTotalPrice() +" 元", "#fe4e4e"),
                new WxMpTemplateData("keyword4", buyOrder.getAddress(), "#173177"),
                new WxMpTemplateData("keyword5", DateUtils.formatDate(buyOrder.getBuyTime(),"yyyy-MM-dd HH:mm"), "#173177"),
                new WxMpTemplateData("remark", "感谢您的使用,了解更多请进入系统查看.", "#173177")
        );
        templateMessage.setData(dataList);

        try {
            msgService.sendTemplateMsg(templateMessage);
            result = true;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return result;
    }

    //buyer/pages/buyOrderDetail/buyOrderDetail?id=

    private WxMpTemplateMessage genTemplateMsg(String templateId, String openId, String buyId,String pagePath) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(templateId);
        wxMpTemplateMessage.setToUser(openId);
        wxMpTemplateMessage.setUrl("");
        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
        miniProgram.setAppid(IPdsConstant.WX_MINIAPP_APPID);//小程序APPID
        miniProgram.setPagePath(pagePath.replace("$buyId",buyId));
        miniProgram.setUsePath(false);
        wxMpTemplateMessage.setMiniProgram(miniProgram);
        return wxMpTemplateMessage;
    }

    private WxMpTemplateMsgService getWxMpTemplateMsgService(String appId){
        AuthApp authApp = authAppService.fetchByAppId(appId);
        WxMpService wxMpService = wxOpenApiService.fetchByAppId(authApp.getAuthAppId()).getWxOpenComponentService().getWxMpServiceByAppid(appId);
        return  wxMpService.getTemplateMsgService();
    }

    private WxMpTemplateMessage genTemplateMessage(UserOpenPlatform userOpen,BuyOrder buyOrder,WechatMsgType msgType){
        String openId = userOpen.getOpenId();
        String buyId = buyOrder.getBuyId();

        WechatMessageTemplate template = messageTemplateService.fetchByAppIdAndMsgType(IPdsConstant.WX_MP_APPID, msgType.getCode());
        if(null == template){
            return null;
        }

        WxMpTemplateMessage templateMessage = genTemplateMsg(template.getTemplateId(), openId, buyId,template.getGoPage());
        return templateMessage;
    }

    private WxMpTemplateMessage genPrepareTplMsg(String templateId, String openId, Date date ,String buySeq, String pagePath) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(templateId);
        wxMpTemplateMessage.setToUser(openId);
        wxMpTemplateMessage.setUrl("");
        WxMpTemplateMessage.MiniProgram miniProgram = new WxMpTemplateMessage.MiniProgram();
        miniProgram.setAppid(IPdsConstant.WX_MINIAPP_APPID);//小程序APPID
        miniProgram.setPagePath(pagePath.replace("$deliveryDate",DateUtils.formatDateTime(date)).replace("$buySeq",buySeq));
        miniProgram.setUsePath(false);
        wxMpTemplateMessage.setMiniProgram(miniProgram);
        return wxMpTemplateMessage;
    }

    private WxMpTemplateMessage genPrepareTplMessage(UserOpenPlatform userOpen, Date date,String buySeq, WechatMsgType msgType){
        String openId = userOpen.getOpenId();

        WechatMessageTemplate template = messageTemplateService.fetchByAppIdAndMsgType(IPdsConstant.WX_MP_APPID, msgType.getCode());
        if(null == template){
            return null;
        }
        WxMpTemplateMessage templateMessage = genPrepareTplMsg(template.getTemplateId(), openId,date,buySeq,template.getGoPage());
        return templateMessage;
    }
}
