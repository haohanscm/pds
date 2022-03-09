package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.params;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author shenyu
 * @create 2018/9/5
 */
public class GoodsDeliveryRulesResp implements Serializable {
    private Date specificDate;    //指定时间
    private String arriveType;      //配送时效
    private Map<String,Object> deliveryType;    //配送方式
    private String deliveryPlanType;    //配送计划类型
    private String rulesDesc;       //规则描述
    private Integer deliveryTotalNum;   //配送总数量
    private Map<String,Object> deliverySchedule;    //配送周期

    private Integer startDayNum;     //开始配送时间的偏移量

    public Date getSpecificDate() {
        return specificDate;
    }

    public void setSpecificDate(Date specificDate) {
        this.specificDate = specificDate;
    }

    public String getArriveType() {
        return arriveType;
    }

    public void setArriveType(String arriveType) {
        this.arriveType = arriveType;
    }

    public String getDeliveryPlanType() {
        return deliveryPlanType;
    }

    public void setDeliveryPlanType(String deliveryPlanType) {
        this.deliveryPlanType = deliveryPlanType;
    }

    public String getRulesDesc() {
        return rulesDesc;
    }

    public void setRulesDesc(String rulesDesc) {
        this.rulesDesc = rulesDesc;
    }

    public Map<String, Object> getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Map<String, Object> deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Map<String, Object> getDeliverySchedule() {
        return deliverySchedule;
    }

    public void setDeliverySchedule(Map<String, Object> deliverySchedule) {
        this.deliverySchedule = deliverySchedule;
    }

    public Integer getStartDayNum() {
        return startDayNum;
    }

    public void setStartDayNum(Integer startDayNum) {
        this.startDayNum = startDayNum;
    }

    public Integer getDeliveryTotalNum() {
        return deliveryTotalNum;
    }

    public void setDeliveryTotalNum(Integer deliveryTotalNum) {
        this.deliveryTotalNum = deliveryTotalNum;
    }
}
