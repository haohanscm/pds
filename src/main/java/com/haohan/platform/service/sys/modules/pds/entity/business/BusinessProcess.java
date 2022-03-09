package com.haohan.platform.service.sys.modules.pds.entity.business;

import org.hibernate.validator.constraints.Length;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

/**
 * 业务流程Entity
 * @author haohan
 * @version 2018-12-03
 */
public class BusinessProcess extends DataEntity<BusinessProcess> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;		// 平台商家ID
	private String tradeId;		// 交易单号
	private String processStage;		// 流程阶段
	private String images;		// 图片组
	private String processDesc;		// 文字说明
	private String operator;		// 操作员
	private String status;		// 状态
	
	public BusinessProcess() {
		super();
	}

	public BusinessProcess(String id){
		super(id);
	}

	@Length(min=0, max=64, message="平台商家ID长度必须介于 0 和 64 之间")
	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	
	@Length(min=0, max=64, message="交易单号长度必须介于 0 和 64 之间")
	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	
	@Length(min=0, max=5, message="流程阶段长度必须介于 0 和 5 之间")
	public String getProcessStage() {
		return processStage;
	}

	public void setProcessStage(String processStage) {
		this.processStage = processStage;
	}
	
	@Length(min=0, max=2000, message="图片组长度必须介于 0 和 2000 之间")
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	@Length(min=0, max=255, message="文字说明长度必须介于 0 和 255 之间")
	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	
	@Length(min=0, max=64, message="操作员长度必须介于 0 和 64 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}