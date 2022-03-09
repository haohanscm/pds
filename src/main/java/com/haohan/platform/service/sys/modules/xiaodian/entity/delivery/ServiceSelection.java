package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 服务选项管理Entity
 * @author haohan
 * @version 2018-08-31
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
public class ServiceSelection extends DataEntity<ServiceSelection> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;	//商家id
	private String goodsId;		// 商品id
	private String serviceName;		// 服务名称
	private String serviceDetail;		// 服务内容
	private BigDecimal servicePrice;		// 服务价格
	private Integer serviceNum;		// 服务次数
	private String serviceSchedule;		// 服务周期     字典
	
	public ServiceSelection() {
		super();
	}

	public ServiceSelection(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="服务名称长度必须介于 0 和 64 之间")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Length(min=0, max=64, message="服务内容长度必须介于 0 和 64 之间")
	public String getServiceDetail() {
		return serviceDetail;
	}

	public void setServiceDetail(String serviceDetail) {
		this.serviceDetail = serviceDetail;
	}
	
	public BigDecimal getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}
	
	public Integer getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(Integer serviceNum) {
		this.serviceNum = serviceNum;
	}
	
	@Length(min=0, max=64, message="服务周期长度必须介于 0 和 64 之间")
	public String getServiceSchedule() {
		return serviceSchedule;
	}

	public void setServiceSchedule(String serviceSchedule) {
		this.serviceSchedule = serviceSchedule;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}