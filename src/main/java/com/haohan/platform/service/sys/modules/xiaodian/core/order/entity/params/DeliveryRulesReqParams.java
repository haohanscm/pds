package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.params;

import java.util.Date;

/**
 * @author shenyu
 * @create 2018/10/1
 */
public class DeliveryRulesReqParams {
    private String deliverySchedule;		// 配送周期
    private Date deliveryStartDate;	  //配送起始时间
    private String arriveType;		// 配送时效
    private Integer deliveryNum;		// 每次配送数量
    private String deliveryPlanType;		// 配送计划类型
    private String deliveryType;		// 配送方式
    private Integer deliveryTotalNum;	//配送总数量

    public String getDeliverySchedule() {
        return deliverySchedule;
    }

    public void setDeliverySchedule(String deliverySchedule) {
        this.deliverySchedule = deliverySchedule;
    }

    public Date getDeliveryStartDate() {
        return deliveryStartDate;
    }

    public void setDeliveryStartDate(Date deliveryStartDate) {
        this.deliveryStartDate = deliveryStartDate;
    }

    public String getArriveType() {
        return arriveType;
    }

    public void setArriveType(String arriveType) {
        this.arriveType = arriveType;
    }

    public Integer getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getDeliveryPlanType() {
        return deliveryPlanType;
    }

    public void setDeliveryPlanType(String deliveryPlanType) {
        this.deliveryPlanType = deliveryPlanType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getDeliveryTotalNum() {
        return deliveryTotalNum;
    }

    public void setDeliveryTotalNum(Integer deliveryTotalNum) {
        this.deliveryTotalNum = deliveryTotalNum;
    }
}
