package com.haohan.platform.service.sys.modules.pss.entity.storage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存管理Entity
 * @author haohan
 * @version 2018-09-05
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarehouseStock extends DataEntity<WarehouseStock> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String warehouseId;		// 仓库ID
	private String barCode;		// 条形码
	@JsonProperty("goodsModelId")
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String unit;		// 单位
	private String attr;		// 规格
	private BigDecimal stockNum;		// 库存数量
	private BigDecimal price;		// 单价
	private BigDecimal amount;		// 金额
	private String supplierId;		// 供应商ID			待定(删除)
	private Date instockTime;		// 入库时间
	private Date lastInventoryTime;		// 上次盘点时间

	//JOIN字段
	private String warehouseName;		//仓库名称

	//待确定
	private String goodsCategory;
	
	public WarehouseStock() {
		super();
	}

	public WarehouseStock(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
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

	public BigDecimal getStockNum() {
		return stockNum;
	}

	public void setStockNum(BigDecimal stockNum) {
		this.stockNum = stockNum;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getInstockTime() {
		return instockTime;
	}

	public void setInstockTime(Date instockTime) {
		this.instockTime = instockTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getLastInventoryTime() {
		return lastInventoryTime;
	}

	public void setLastInventoryTime(Date lastInventoryTime) {
		this.lastInventoryTime = lastInventoryTime;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		if(null == this.price){
		    this.price = BigDecimal.ZERO;
        }
        if(null == this.stockNum){
		    this.stockNum = BigDecimal.ZERO;
        }
		return this.price.multiply(this.stockNum);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Length(min=0, max=64, message="供应商ID长度必须介于 0 和 64 之间")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}