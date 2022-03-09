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
 * 盘点记录Entity
 * @author haohan
 * @version 2018-09-05
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarehouseInventoryDetail extends DataEntity<WarehouseInventoryDetail> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String warehouseStockId;		// 商品库存ID
	private String warehouseId;		// 仓库编号
	@JsonProperty("goodsModelId")
	private String goodsCode;		// 商品编号
	private BigDecimal origNum;		// 原有库存
	private BigDecimal modifyNum;		// 修改数量
	@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord","roleNames","loginFlag","admin"})
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private User operator;		// 操作人员
	private Date checkTime;		// 盘点时间
	private String supplierId;		// 供应商ID
	
	public WarehouseInventoryDetail() {
		super();
	}

	public WarehouseInventoryDetail(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getWarehouseStockId() {
		return warehouseStockId;
	}

	public void setWarehouseStockId(String warehouseStockId) {
		this.warehouseStockId = warehouseStockId;
	}

	@Length(min=0, max=64, message="仓库编号长度必须介于 0 和 64 之间")
	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	@Length(min=0, max=64, message="商品编号长度必须介于 0 和 64 之间")
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public BigDecimal getOrigNum() {
		return origNum;
	}

	public void setOrigNum(BigDecimal origNum) {
		this.origNum = origNum;
	}

	public BigDecimal getModifyNum() {
		return modifyNum;
	}

	public void setModifyNum(BigDecimal modifyNum) {
		this.modifyNum = modifyNum;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	@Length(min=0, max=64, message="供应商ID长度必须介于 0 和 64 之间")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}