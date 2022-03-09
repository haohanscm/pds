package com.haohan.platform.service.sys.modules.pss.entity.storage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 库存盘点Entity
 * @author haohan
 * @version 2018-09-05
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WareHouseInventory extends DataEntity<WareHouseInventory> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	@JsonProperty("goodsModelId")
	private String goodsCode;		// 规格商品唯一标识
	private String warehouseNum;		// 仓库编号
	private String goodsName;		// 商品名称
	private String stockNum;		// 库存数量
	private Date instockTime;		// 入库时间
	private Date lastInventoryTime;		// 上次盘点时间
	private String attr;		// 规格
	private String isConfirm;			//是否确认

	//辅助查询字段
	private Date beginTime;
	private Date endTime;
	
	public WareHouseInventory() {
		super();
	}

	public WareHouseInventory(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商品编码长度必须介于 0 和 64 之间")
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@Length(min=0, max=64, message="仓库编号长度必须介于 0 和 64 之间")
	public String getWarehouseNum() {
		return warehouseNum;
	}

	public void setWarehouseNum(String warehouseNum) {
		this.warehouseNum = warehouseNum;
	}
	
	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Length(min=0, max=10, message="库存数量长度必须介于 0 和 10 之间")
	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInstockTime() {
		return instockTime;
	}

	public void setInstockTime(Date instockTime) {
		this.instockTime = instockTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastInventoryTime() {
		return lastInventoryTime;
	}

	public void setLastInventoryTime(Date lastInventoryTime) {
		this.lastInventoryTime = lastInventoryTime;
	}
	
	@Length(min=0, max=64, message="规格长度必须介于 0 和 64 之间")
	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
}