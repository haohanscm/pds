package com.haohan.platform.service.sys.modules.xiaodian.api.entity.reserve;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

import java.util.Date;

/**
 * Created by zgw on 2018/9/3.
 */
public class DeliveryQueryParams extends DataEntity<DeliveryQueryParams> {

    private String uid;  //用户ID
    private Date theDay;  //日期
    private String beginDate;//开始日期
    private String endDate;//结束日期
    private String deliveryPlanId; // 配送计划Id
    private String status; // 配送状态
    private String communityNo; // 小区编号
    private String telephone;  // 手机号
    private String memberId; // 会员id

    //created by yu.shen
    private String merchantId;  //商家id
    private String shopId;  //店铺id

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getTheDay() {
        return theDay;
    }

    public void setTheDay(Date theDay) {
        this.theDay = theDay;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDeliveryPlanId() {
        return deliveryPlanId;
    }

    public void setDeliveryPlanId(String deliveryPlanId) {
        this.deliveryPlanId = deliveryPlanId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommunityNo() {
        return communityNo;
    }

    public void setCommunityNo(String communityNo) {
        this.communityNo = communityNo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
