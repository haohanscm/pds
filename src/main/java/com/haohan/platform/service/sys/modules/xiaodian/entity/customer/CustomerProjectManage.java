package com.haohan.platform.service.sys.modules.xiaodian.entity.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 客户项目管理Entity
 * @author haohan
 * @version 2018-04-22
 */
public class CustomerProjectManage extends DataEntity<CustomerProjectManage> {
	
	private static final long serialVersionUID = 1L;
	private String quarter;		// 季度
	private Date signTime;		// 签约时间
	private String merchantName;		// 商家名称
	private Area area;		// 区域
	private String serviceProduct;		// 产品
	private String serviceType;		// 服务版本
	private String serviceList;		// 开通功能
	private String[] serviceLists;
	private String payType;		// 支付方式
	private Date payTime;		// 支付时间
	private String payAmount;		// 支付金额
	private User bizUser;		// 业务专管员
	private User opUser;		// 运营专管员
	private User techUser;		// 技术负责人
	private User financeUser;		// 财务核对人
	private String projectInfo;		// 项目情况
	private String[] projectInfos;
	private String projectDesc;		// 其他说明
	private String projectStep;		// 项目阶段
	private String onlineStatus;		// 上线状态
	private Date onlineTime;		// 上线时间
	private String merchantDatabase;		// 商家资源库ID
	private String merchant;		// 商家ID
	private String merchantApp;		// 商家应用ID
	private Date beginSignTime;		// 开始 签约时间
	private Date endSignTime;		// 结束 签约时间
	private Date beginOnlineTime;		// 开始 上线时间
	private Date endOnlineTime;		// 结束 上线时间
	
	public CustomerProjectManage() {
		super();
	}

	public CustomerProjectManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="季度长度必须介于 0 和 64 之间")
	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	
	@Length(min=0, max=64, message="商家名称长度必须介于 0 和 64 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=64, message="产品长度必须介于 0 和 64 之间")
	public String getServiceProduct() {
		return serviceProduct;
	}

	public void setServiceProduct(String serviceProduct) {
		this.serviceProduct = serviceProduct;
	}
	
	@Length(min=0, max=64, message="服务版本长度必须介于 0 和 64 之间")
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Length(min=0, max=1000, message="开通功能长度必须介于 0 和 1000 之间")
	public String getServiceList() {
		return serviceList;
	}

	public void setServiceList(String serviceList) {
		this.serviceList = serviceList;
	}
	
	@Length(min=0, max=64, message="支付方式长度必须介于 0 和 64 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=0, max=64, message="支付金额长度必须介于 0 和 64 之间")
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	

	@Length(min=0, max=1000, message="项目情况长度必须介于 0 和 1000 之间")
	public String getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(String projectInfo) {
		this.projectInfo = projectInfo;
	}
	
	@Length(min=0, max=500, message="其他说明长度必须介于 0 和 500 之间")
	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	
	@Length(min=0, max=64, message="项目阶段长度必须介于 0 和 64 之间")
	public String getProjectStep() {
		return projectStep;
	}

	public void setProjectStep(String projectStep) {
		this.projectStep = projectStep;
	}
	
	@Length(min=0, max=64, message="上线状态长度必须介于 0 和 64 之间")
	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	@Length(min=0, max=64, message="商家资源库ID长度必须介于 0 和 64 之间")
	public String getMerchantDatabase() {
		return merchantDatabase;
	}

	public void setMerchantDatabase(String merchantDatabase) {
		this.merchantDatabase = merchantDatabase;
	}
	
	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	
	@Length(min=0, max=64, message="商家应用ID长度必须介于 0 和 64 之间")
	public String getMerchantApp() {
		return merchantApp;
	}

	public void setMerchantApp(String merchantApp) {
		this.merchantApp = merchantApp;
	}
	
	public Date getBeginSignTime() {
		return beginSignTime;
	}

	public void setBeginSignTime(Date beginSignTime) {
		this.beginSignTime = beginSignTime;
	}
	
	public Date getEndSignTime() {
		return endSignTime;
	}

	public void setEndSignTime(Date endSignTime) {
		this.endSignTime = endSignTime;
	}
		
	public Date getBeginOnlineTime() {
		return beginOnlineTime;
	}

	public void setBeginOnlineTime(Date beginOnlineTime) {
		this.beginOnlineTime = beginOnlineTime;
	}
	
	public Date getEndOnlineTime() {
		return endOnlineTime;
	}

	public void setEndOnlineTime(Date endOnlineTime) {
		this.endOnlineTime = endOnlineTime;
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

	public User getFinanceUser() {
		return financeUser;
	}

	public void setFinanceUser(User financeUser) {
		this.financeUser = financeUser;
	}

	public String[] getServiceLists() {
		return serviceLists;
	}

	public void setServiceLists(String[] serviceLists) {
		this.serviceLists = serviceLists;
	}

	public String[] getProjectInfos() {
		return projectInfos;
	}

	public void setProjectInfos(String[] projectInfos) {
		this.projectInfos = projectInfos;
	}
}