package com.haohan.platform.service.sys.modules.pds.entity.operation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

/**
 * 平台交易流程管理Entity
 * @author dy
 * @version 2018-12-03
 */
public class PdsTradeProcess extends DataEntity<PdsTradeProcess> {
	
	private static final long serialVersionUID = 1L;
	private String processSn;		// 交易流程号
	private String pmId;		// 平台商家ID
	private String buySeq;		// 采购批次
	private Date deliveryTime;		// 送货日期
	private String status;		// 状态

	// 查询条件
	private String buyId; // 采购编号
	
	public PdsTradeProcess() {
		super();
	}

	public PdsTradeProcess(String id){
		super(id);
	}

	@Length(min=0, max=64, message="交易单号长度必须介于 0 和 64 之间")
	public String getProcessSn() {
		return processSn;
	}

	public void setProcessSn(String processSn) {
		this.processSn = processSn;
	}
	
	@Length(min=0, max=64, message="平台商家ID长度必须介于 0 和 64 之间")
	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}
	
	@Length(min=0, max=64, message="采购批次长度必须介于 0 和 64 之间")
	public String getBuySeq() {
		return buySeq;
	}

	public void setBuySeq(String buySeq) {
		this.buySeq = buySeq;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

	
}