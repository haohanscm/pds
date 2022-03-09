package com.haohan.platform.service.sys.modules.pds.api.entity.resp.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.modules.pds.api.entity.params.PdsAdmBOrderDetailParams;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author shenyu
 * @create 2018/10/31
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PdsApiSumBuyOrderResp implements Serializable {
    private String buyId;        // 采购编号
    private Date buyTime;        // 采购时间
    private Date deliveryTime;        // 送货日期
    private String buySeq;            //送货批次
    private BigDecimal totalPrice;        // 采购总价 暂未使用
    private String contact;        // 联系人
    private String telephone;        // 联系人电话
    private String address;        // 配送地址
    private String status;        // 采购状态
    private String buyerName; //采购商名称
    private BigDecimal shipFee;        //运费
    private String deliveryType;    //配送方式
    private Integer categoryNum;    //商品种类
    private Integer totalGoodsNum;         //商品总数量

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PdsAdmBOrderDetailParams> orderDetailList;

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public BigDecimal getShipFee() {
        return shipFee;
    }

    public void setShipFee(BigDecimal shipFee) {
        this.shipFee = shipFee;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(Integer categoryNum) {
        this.categoryNum = categoryNum;
    }

    public Integer getTotalGoodsNum() {
        return totalGoodsNum;
    }

    public void setTotalGoodsNum(Integer totalGoodsNum) {
        this.totalGoodsNum = totalGoodsNum;
    }

    public List<PdsAdmBOrderDetailParams> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<PdsAdmBOrderDetailParams> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
