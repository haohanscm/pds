package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 送货单明细Entity
 *
 * @author yu.shen
 * @version 2018-11-14
 */
public class PdsShipOrderDetail extends DataEntity<PdsShipOrderDetail> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String shipId;        // 送货单号
    private String tradeId;        // 交易单号
    private String goodsName;        // 商品名称
    private BigDecimal goodsNum;        // 商品数量

    private String pmName;        // 平台商家名称

    public PdsShipOrderDetail() {
        super();
    }

    public PdsShipOrderDetail(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "送货单号长度必须介于 0 和 64 之间")
    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    @Length(min = 0, max = 64, message = "交易单号长度必须介于 0 和 64 之间")
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Length(min = 0, max = 64, message = "商品名称长度必须介于 0 和 64 之间")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(BigDecimal goodsNum) {
        this.goodsNum = goodsNum;
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
}