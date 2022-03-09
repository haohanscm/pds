package com.haohan.platform.service.sys.modules.xiaodian.entity.teiminal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 终端设备Entity
 * @author shenyu
 * @version 2018-08-18
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord", "remarks"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TerminalManage extends DataEntity<TerminalManage> {
	
	private static final long serialVersionUID = 1L;
	private Integer terminalNo;		// 设备编号
	private String terminalType;		// 设备类型
	private String name;		// 设备名称
	private String alias;		// 设备别名
	private String snCode;		// SN码
	private String producer;		// 制造厂商
	private String imeiCode;		// IMEI
	private Date purchaseTime;		// 购货时间
	private Date sellTime;		// 出库时间
	private String merchantId;		// 商家id
	private String shopId;		// 店铺id
	private String shopName;	//店铺名称
	private String status;		// 设备状态
	private String remark;		// 设备备注
	
	public TerminalManage() {
		super();
	}

	public TerminalManage(String id){
		super(id);
	}

	public Integer getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(Integer terminalNo) {
		this.terminalNo = terminalNo;
	}

	@Length(min=0, max=64, message="设备名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="设备别名长度必须介于 0 和 64 之间")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Length(min=0, max=64, message="SN码长度必须介于 0 和 64 之间")
	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}
	
	@Length(min=0, max=64, message="制造厂商长度必须介于 0 和 64 之间")
	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	@Length(min=0, max=64, message="IMEI长度必须介于 0 和 64 之间")
	public String getImeiCode() {
		return imeiCode;
	}

	public void setImeiCode(String imeiCode) {
		this.imeiCode = imeiCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	public Date getSellTime() {
		return sellTime;
	}

	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}
	
	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="店铺id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=5, message="设备状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="设备备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}