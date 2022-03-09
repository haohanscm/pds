package com.haohan.platform.service.sys.modules.xiaodian.core.order.entity.req;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenyu
 * @create 2019/1/3
 */
public class WxOrderDetailReq extends BaseOrderDetailReq {
    private String giftName;		// 赠品名称
    private String giftId;		// 赠品id
    private String giftSchedule;		// 赠送周期
    private Integer giftNum;		// 赠送数量
    private String deliverySchedule;		// 配送周期
    private Date deliveryStartDate;	  //配送起始时间
    private String arriveType;		// 配送时效
    private Integer deliveryNum;		// 每次配送数量
    private String deliveryPlanType;		// 配送计划类型
    private String deliveryType;		// 配送方式
    private Integer deliveryTotalNum;	//配送总数量
    private String serviceName;		// 服务名称
    private String serviceDetail;		// 服务内容
    private BigDecimal servicePrice;		// 服务价格
    private String serviceSchedule;		// 服务周期
    private Integer serviceNum;		// 服务次数

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftSchedule() {
        return giftSchedule;
    }

    public void setGiftSchedule(String giftSchedule) {
        this.giftSchedule = giftSchedule;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceSchedule() {
        return serviceSchedule;
    }

    public void setServiceSchedule(String serviceSchedule) {
        this.serviceSchedule = serviceSchedule;
    }

    public Integer getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(Integer serviceNum) {
        this.serviceNum = serviceNum;
    }

}
