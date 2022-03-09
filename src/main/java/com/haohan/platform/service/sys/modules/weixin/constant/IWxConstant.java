package com.haohan.platform.service.sys.modules.weixin.constant;

import com.haohan.framework.utils.EnumUtils;

/**
 * Created by zgw on 2018/4/24.
 */
public interface IWxConstant {

    //微信消息类型
    enum WechatMsgType{

        payResultNotify("0","支付结果通知"),
        KefuMessageNotify("1","客服消息通知"),
        MpPaySuccessNotify("2","公众号付款成功通知"),
        OrderConfirmNotify("3","订单待确认通知"),
        OrderDeliveryNotify("4","订单发货通知"),
        OrderDealCloseNotify("5","订单交易完成通知"),
        OrderSelfTakeNotify("6","订单提货通知"),
        PrepareGoodsNotify("7","订单备货通知")
        ;


        WechatMsgType(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString()+code,this);
        }

        public static WechatMsgType valueOfWechatMsgType(String code) {
            Object obj = EnumUtils.get(WechatMsgType.class.getName() + code);
            if (null != obj) {
                return (WechatMsgType) obj;
            }
            return null;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    enum AppServiceType{

        wxapp("0","小程序"),open("1","开放平台"),wxmp("2","公众号");

        AppServiceType(String code, String desc) {
            this.code = code;
            this.desc = desc;
            EnumUtils.put(getClass().getName().toString()+code,this);
        }

        public static AppServiceType valueOfAppServiceType(String code) {
            Object obj = EnumUtils.get(AppServiceType.class.getName() + code);
            if (null != obj) {
                return (AppServiceType) obj;
            }
            return null;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
    
