package com.haohan.platform.service.sys.modules.pss.entity.procure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 商品采购明细Entity
 * @author haohan
 * @version 2018-09-07
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcurementDetail extends DataEntity<ProcurementDetail> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String procureNum;		// 采购编号
	private String goodsName;		// 商品名称
	@NotBlank(message = "采购商品ID不能为空")
	private String goodsModelId;		// 商品SKU ID
	@NotNull(message = "请填写采购单价")
	private BigDecimal price;		// 单价
	@NotNull(message = "请填写采购数量")
	private BigDecimal goodsNum;		// 数量
	private String modelName;		// 规格
	private BigDecimal sumAmount;		// 合计金额
	private String unit;		// 单位
	private String categrory;		// 分类
	private String stockStatus;		// 入库状态
	private String offerOrderId;		// 报价单编号

	public ProcurementDetail() {
		super();
	}

	public ProcurementDetail(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=64, message="采购编号长度必须介于 1 和 64 之间")
	public String getProcureNum() {
		return procureNum;
	}

	public void setProcureNum(String procureNum) {
		this.procureNum = procureNum;
	}
	
	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsModelId() {
		return goodsModelId;
	}

	public void setGoodsModelId(String goodsModelId) {
		this.goodsModelId = goodsModelId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(BigDecimal goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}
	
	@Length(min=0, max=64, message="单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="分类长度必须介于 0 和 64 之间")
	public String getCategrory() {
		return categrory;
	}

	public void setCategrory(String categrory) {
		this.categrory = categrory;
	}
	
	@Length(min=0, max=64, message="入库状态长度必须介于 0 和 64 之间")
	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

    public String getOfferOrderId() {
        return offerOrderId;
    }

    public void setOfferOrderId(String offerOrderId) {
        this.offerOrderId = offerOrderId;
    }
}