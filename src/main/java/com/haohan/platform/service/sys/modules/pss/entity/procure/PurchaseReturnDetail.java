package com.haohan.platform.service.sys.modules.pss.entity.procure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 采购退货明细Entity
 * @author haohan
 * @version 2018-09-05
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseReturnDetail extends DataEntity<PurchaseReturnDetail> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String returnId;		// 退货ID
	private String warehouseId;		//退货商品仓库ID
	private String barCode;		// 条形码
	@JsonProperty("goodsModelId")
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String unit;		// 单位
	private String attr;		// 规格
	private BigDecimal num;		// 数量
	private BigDecimal price;		// 单价
	private BigDecimal amount;		// 金额

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public PurchaseReturnDetail() {
		super();
	}

	public PurchaseReturnDetail(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="退货ID长度必须介于 0 和 64 之间")
	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	
	@Length(min=0, max=64, message="条形码长度必须介于 0 和 64 之间")
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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
	
	@Length(min=0, max=64, message="单位长度必须介于 0 和 64 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Length(min=0, max=64, message="规格长度必须介于 0 和 64 之间")
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}