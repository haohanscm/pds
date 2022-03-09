package com.haohan.platform.service.sys.modules.pss.entity.storage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品调拨明细Entity
 * @author haohan
 * @version 2018-09-05
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsAllotDetail extends DataEntity<GoodsAllotDetail> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String supplierId;		//供应商ID
	private String allotId;		// 调拨ID
	@JsonProperty("goodsModelId")
	private String goodsCode;		// 商品编码
	private String modelName;		//商品规格
	private String goodsCategory;	//商品分类
	private String goodsName;		// 商品名称
	private String unit;			//商品单位
	private BigDecimal price;		//采购价格
	private BigDecimal goodsNum;		// 调拨数量
	private BigDecimal outorigStock;		// 调出原库存
	private BigDecimal outStock;		// 调出后库存
	private BigDecimal inorigStock;		// 调入原库存
	private BigDecimal inStock;		// 调入后库存
	private String operator;		// 操作人
	private Date oprateTime;		// 操作时间
	
	public GoodsAllotDetail() {
		super();
	}

	public GoodsAllotDetail(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="调拨ID长度必须介于 0 和 64 之间")
	public String getAllotId() {
		return allotId;
	}

	public void setAllotId(String allotId) {
		this.allotId = allotId;
	}
	
	@Length(min=0, max=64, message="商品编码长度必须介于 0 和 64 之间")
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
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

	public BigDecimal getOutorigStock() {
		return outorigStock;
	}

	public void setOutorigStock(BigDecimal outorigStock) {
		this.outorigStock = outorigStock;
	}

	public BigDecimal getOutStock() {
		return outStock;
	}

	public void setOutStock(BigDecimal outStock) {
		this.outStock = outStock;
	}

	public BigDecimal getInorigStock() {
		return inorigStock;
	}

	public void setInorigStock(BigDecimal inorigStock) {
		this.inorigStock = inorigStock;
	}

	public BigDecimal getInStock() {
		return inStock;
	}

	public void setInStock(BigDecimal inStock) {
		this.inStock = inStock;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOprateTime() {
		return oprateTime;
	}

	public void setOprateTime(Date oprateTime) {
		this.oprateTime = oprateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
}