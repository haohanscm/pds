package com.haohan.platform.service.sys.modules.xiaodian.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Merchant;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 客户服务管理Entity
 * @author haohan
 * @version 2018-04-22
 */
public class CustomerServiceManage extends DataEntity<CustomerServiceManage> {
	
	private static final long serialVersionUID = 1L;
	private Merchant merchant;		// 商家
	private String serviceType;		// 服务类型
	private String serviceContent;		// 服务内容
	private String[] serviceContents;  // 多选
	private String serviceDesc;		// 服务描述
	private Date serviceTime;		// 服务时间
	private String payType;		// 支付方式
	private String payAmount;		// 收费
	private Date payTime;		// 支付时间
	private User bizUser;		// 业务专管员
	private User opUser;		// 运营专管员
	private User techUser;		// 技术负责人
	private String serviceInfo;		// 服务情况
	private User financeUser;		// 财务核对人
	private String serviceStatus;		// 服务状态
	private String custormEvaluate;		// 客户评分
	private Date beginServiceTime;		// 开始 服务时间
	private Date endServiceTime;		// 结束 服务时间
	private Date beginPayTime;		// 开始 支付时间
	private Date endPayTime;		// 结束 支付时间
	
	public CustomerServiceManage() {
		super();
	}

	public CustomerServiceManage(String id){
		super(id);
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	
	@Length(min=0, max=64, message="服务类型长度必须介于 0 和 64 之间")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Length(min=0, max=200, message="服务内容长度必须介于 0 和 200 之间")
	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	
	@Length(min=0, max=500, message="服务描述长度必须介于 0 和 500 之间")
	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}
	
	@Length(min=0, max=64, message="支付方式长度必须介于 0 和 64 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=64, message="收费长度必须介于 0 和 64 之间")
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public User getBizUser() {
		return bizUser;
	}

	public void setBizUser(User bizUser) {
		this.bizUser = bizUser;
	}
	
	public User getOpUser() {
		return opUser;
	}

	public void setOpUser(User opUser) {
		this.opUser = opUser;
	}
	
	public User getTechUser() {
		return techUser;
	}

	public void setTechUser(User techUser) {
		this.techUser = techUser;
	}
	
	@Length(min=0, max=500, message="服务情况长度必须介于 0 和 500 之间")
	public String getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	
	public User getFinanceUser() {
		return financeUser;
	}

	public void setFinanceUser(User financeUser) {
		this.financeUser = financeUser;
	}
	
	@Length(min=0, max=64, message="服务状态长度必须介于 0 和 64 之间")
	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	@Length(min=0, max=64, message="客户评分长度必须介于 0 和 64 之间")
	public String getCustormEvaluate() {
		return custormEvaluate;
	}

	public void setCustormEvaluate(String custormEvaluate) {
		this.custormEvaluate = custormEvaluate;
	}
	
	public Date getBeginServiceTime() {
		return beginServiceTime;
	}

	public void setBeginServiceTime(Date beginServiceTime) {
		this.beginServiceTime = beginServiceTime;
	}
	
	public Date getEndServiceTime() {
		return endServiceTime;
	}

	public void setEndServiceTime(Date endServiceTime) {
		this.endServiceTime = endServiceTime;
	}
		
	public Date getBeginPayTime() {
		return beginPayTime;
	}

	public void setBeginPayTime(Date beginPayTime) {
		this.beginPayTime = beginPayTime;
	}
	
	public Date getEndPayTime() {
		return endPayTime;
	}

	public void setEndPayTime(Date endPayTime) {
		this.endPayTime = endPayTime;
	}

	public String[] getServiceContents() {
		return serviceContents;
	}

	public void setServiceContents(String[] serviceContents) {
		this.serviceContents = serviceContents;
	}
}