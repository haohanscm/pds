package com.haohan.platform.service.sys.modules.pds.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购单汇总Entity
 *
 * @author haohan
 * @version 2018-10-24
 */
public class SummaryOrder extends DataEntity<SummaryOrder> {

    private static final long serialVersionUID = 1L;
    private String pmId; // 平台商家id
    private String summaryOrderId;        // 汇总单号
    private String goodsId;        // 商品ID
    private String goodsCategoryId;    //商品分类
    private String goodsImg;        // 商品图片
    private String goodsName;        // 商品名称
    private String goodsModel;        // 商品规格
    private BigDecimal marketPrice;        // 市场价
    private BigDecimal platformPrice;        // 平台报价
    private BigDecimal buyAvgPrice;        //采购均价
    private BigDecimal supplyAvgPrice;    //供应均价
    private BigDecimal realBuyNum;        // 实际采购数量
    private BigDecimal needBuyNum;        // 需求采购数量
    private Integer limitSupplyNum;        // 最小供应量
    private Date buyTime;        // 采购日期
    private Integer buyerNum;        // 商家数量
    private Date deliveryTime;        // 送货日期
    private String unit;        // 单位
    private String status;        // 状态
    private String buySeq;        //采购批次
    private String isGenTrade;        //是否生成交易单

    private String supplierId;        // 供应商
    private String pmName;        // 平台商家名称


    public BigDecimal getBuyAvgPrice() {
        return buyAvgPrice;
    }

    public void setBuyAvgPrice(BigDecimal buyAvgPrice) {
        this.buyAvgPrice = buyAvgPrice;
    }

    public String getBuySeq() {
        return buySeq;
    }

    public void setBuySeq(String buySeq) {
        this.buySeq = buySeq;
    }

    public SummaryOrder() {
        super();
    }

    public SummaryOrder(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "汇总单号长度必须介于 0 和 64 之间")
    public String getSummaryOrderId() {
        return summaryOrderId;
    }

    public void setSummaryOrderId(String summaryOrderId) {
        this.summaryOrderId = summaryOrderId;
    }

    @Length(min = 0, max = 64, message = "商品ID长度必须介于 0 和 64 之间")
    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    @Length(min = 0, max = 64, message = "商品名称长度必须介于 0 和 64 之间")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Length(min = 0, max = 64, message = "商品规格长度必须介于 0 和 64 之间")
    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getPlatformPrice() {
        return platformPrice;
    }

    public void setPlatformPrice(BigDecimal platformPrice) {
        this.platformPrice = platformPrice;
    }

    public BigDecimal getRealBuyNum() {
        return realBuyNum;
    }

    public void setRealBuyNum(BigDecimal realBuyNum) {
        this.realBuyNum = realBuyNum;
    }

    public BigDecimal getNeedBuyNum() {
        return needBuyNum;
    }

    public void setNeedBuyNum(BigDecimal needBuyNum) {
        this.needBuyNum = needBuyNum;
    }

    public Integer getLimitSupplyNum() {
        return limitSupplyNum;
    }

    public void setLimitSupplyNum(Integer limitSupplyNum) {
        this.limitSupplyNum = limitSupplyNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getBuyerNum() {
        return buyerNum;
    }

    public void setBuyerNum(Integer buyerNum) {
        this.buyerNum = buyerNum;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Length(min = 0, max = 64, message = "单位长度必须介于 0 和 64 之间")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//	public Double getUpPercent() {
//		return upPercent;
//	}
//
//	public void setUpPercent(Double upPercent) {
//		this.upPercent = upPercent;
//	}

    public BigDecimal getSupplyAvgPrice() {
        return supplyAvgPrice;
    }

    public void setSupplyAvgPrice(BigDecimal supplyAvgPrice) {
        this.supplyAvgPrice = supplyAvgPrice;
    }

    public String getIsGenTrade() {
        return isGenTrade;
    }

    public void setIsGenTrade(String isGenTrade) {
        this.isGenTrade = isGenTrade;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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