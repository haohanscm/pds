package com.haohan.platform.service.sys.modules.xiaodian.api.entity.order.params;

import java.io.Serializable;

/**
 * @author shenyu
 * @create 2018/9/25
 */
public class PayNotifyTaskDto implements Serializable {
//    private String partnerNum;      //订单渠道来源
    private Boolean isGenOrderDelivery = false; //是否生成订单配送
    private Boolean isGenDeliveryPlan = false;   //是否添加计划队列
    private Boolean isPaySuccess = false;        //是否添加消息通知队列
    private Boolean isSynJisuAppOrder = false;   //是否同步即速订单
    private Boolean isNotifyPartner = false;     //是否通知渠道商

//    public String getPartnerNum() {
//        return partnerNum;
//    }
//
//    public void setPartnerNum(String partnerNum) {
//        this.partnerNum = partnerNum;
//    }

    public Boolean getGenOrderDelivery() {
        return isGenOrderDelivery;
    }

    public void setGenOrderDelivery(Boolean genOrderDelivery) {
        isGenOrderDelivery = genOrderDelivery;
    }

    public Boolean getGenDeliveryPlan() {
        return isGenDeliveryPlan;
    }

    public void setGenDeliveryPlan(Boolean genDeliveryPlan) {
        isGenDeliveryPlan = genDeliveryPlan;
    }

    public Boolean getPaySuccess() {
        return isPaySuccess;
    }

    public void setPaySuccess(Boolean paySuccess) {
        isPaySuccess = paySuccess;
    }

    public Boolean getSynJisuAppOrder() {
        return isSynJisuAppOrder;
    }

    public void setSynJisuAppOrder(Boolean synJisuAppOrder) {
        isSynJisuAppOrder = synJisuAppOrder;
    }

    public Boolean getNotifyPartner() {
        return isNotifyPartner;
    }

    public void setNotifyPartner(Boolean notifyPartner) {
        isNotifyPartner = notifyPartner;
    }
}
