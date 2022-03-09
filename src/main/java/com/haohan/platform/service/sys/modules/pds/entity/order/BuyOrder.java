package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购单Entity
 *
 * @author haohan
 * @version 2018-10-22
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyOrder extends DataEntity<BuyOrder> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String buyId;        // 采购编号
    private String buyerUid;        // 采购用户
    private String buyerId;        // 采购商
    private String buyerName;    //采购商名称
    private Date buyTime;        // 采购时间
    private Date deliveryTime;        // 送货日期
    private Date dealTime;            //成交时间
    private String buySeq;            //送货批次
    private String needNote;        // 采购需求
    private BigDecimal genPrice;        // 总价预估
    private BigDecimal totalPrice;        // 采购总价
    private String contact;        // 联系人
    private String telephone;        // 联系人电话
    private String address;        // 配送地址
    private String status;        // 采购状态
    private Date beginBuyTime;        // 开始 采购时间
    private Date endBuyTime;        // 结束 采购时间
    private BigDecimal shipFee;        //运费
    private String deliveryType;    //配送方式
    private String goodsOrderId;    //零售单号
//	private String orderFrom;		//订单来源

    private List<BuyOrderDetail> buyOrderDetailList; // 采购单明细列表
    private Integer detailNum;  // 明细数量

    //补充
    private Integer totalGoodsNum;        //下单商品种类数
    private Integer offeredNum;            //已完成报价数量

    private String pmName;        // 平台商家名称
    private String paymentStatus; // 该采购单货款状态
    private String merchantId;  // 采购商商家id
    private String merchantName;  // 采购商商家名称
    private String buyerStatus;  // 交易单中 采购商收货状态  BuyerDealStatus

    public BuyOrder() {
        super();
    }

    public BuyOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "采购编号长度必须介于 0 和 64 之间")
    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    @Length(min = 0, max = 64, message = "采购用户长度必须介于 0 和 64 之间")
    public String getBuyerUid() {
        return buyerUid;
    }

    public void setBuyerUid(String buyerUid) {
        this.buyerUid = buyerUid;
    }

    @Length(min = 0, max = 64, message = "采购商长度必须介于 0 和 64 之间")
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Length(min = 0, max = 500, message = "采购需求长度必须介于 0 和 500 之间")
    public String getNeedNote() {
        return needNote;
    }

    public void setNeedNote(String needNote) {
        this.needNote = needNote;
    }

    public BigDecimal getGenPrice() {
        return genPrice;
    }

    public void setGenPrice(BigDecimal genPrice) {
        this.genPrice = genPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Length(min = 0, max = 64, message = "联系人长度必须介于 0 和 64 之间")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Length(min = 0, max = 500, message = "配送地址长度必须介于 0 和 500 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min = 0, max = 5, message = "采购状态长度必须介于 0 和 5 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBeginBuyTime() {
        return beginBuyTime;
    }

    public void setBeginBuyTime(Date beginBuyTime) {
        this.beginBuyTime = beginBuyTime;
    }

    public Date getEndBuyTime() {
        return endBuyTime;
    }

    public void setEndBuyTime(Date endBuyTime) {
        this.endBuyTime = endBuyTime;
    }

    public List<BuyOrderDetail> getBuyOrderDetailList() {
        return buyOrderDetailList;
    }

    public void setBuyOrderDetailList(List<BuyOrderDetail> buyOrderDetailList) {
        this.buyOrderDetailList = buyOrderDetailList;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
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

    public Integer getDetailNum() {
        return detailNum;
    }

    public void setDetailNum(Integer detailNum) {
        this.detailNum = detailNum;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getTotalGoodsNum() {
        return totalGoodsNum;
    }

    public void setTotalGoodsNum(Integer totalGoodsNum) {
        this.totalGoodsNum = totalGoodsNum;
    }

    public Integer getOfferedNum() {
        return offeredNum;
    }

    public void setOfferedNum(Integer offeredNum) {
        this.offeredNum = offeredNum;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(String buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGoodsOrderId() {
        return goodsOrderId;
    }

    public void setGoodsOrderId(String goodsOrderId) {
        this.goodsOrderId = goodsOrderId;
    }


}