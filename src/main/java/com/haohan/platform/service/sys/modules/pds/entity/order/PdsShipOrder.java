package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 送货单Entity
 *
 * @author yu.shen
 * @version 2018-11-14
 */
public class PdsShipOrder extends DataEntity<PdsShipOrder> {

    private static final long serialVersionUID = 1L;
    private String pmId;        //平台商家ID
    private String shipId;        // 送货单号
    private String buyerId;        // 采购商ID
    private String deliveryId;        // 配送编号
    private String pathPoint;        // 途径点
    private Date arriveTime;        // 送达时间
    private Date acceptTime;        // 验收时间
    private String status;        // 状态

    private String pmName;        // 平台商家名称
    private String buyerName;        // 采购商名称

    //查询字段
    private Date deliveryDate;        //送货时间
    private String deliverySeq;            //送货批次

    public PdsShipOrder() {
        super();
    }

    public PdsShipOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "送货单号长度必须介于 0 和 64 之间")
    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    @Length(min = 0, max = 64, message = "采购商ID长度必须介于 0 和 64 之间")
    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Length(min = 0, max = 64, message = "配送编号长度必须介于 0 和 64 之间")
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Length(min = 0, max = 64, message = "途径点长度必须介于 0 和 64 之间")
    public String getPathPoint() {
        return pathPoint;
    }

    public void setPathPoint(String pathPoint) {
        this.pathPoint = pathPoint;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliverySeq() {
        return deliverySeq;
    }

    public void setDeliverySeq(String deliverySeq) {
        this.deliverySeq = deliverySeq;
    }
}