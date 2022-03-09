package com.haohan.platform.service.sys.modules.xiaodian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 通行证管理Entity
 * @author haohan
 * @version 2017-08-05
 */
public class UPassport extends DataEntity<UPassport> {
	
	private static final long serialVersionUID = 1L;
	private String loginName;		//登录名
	private String email;		// 邮箱
	private String telephone;		// 手机
	private String password;		// 密码
	private String salt;		// 登录盐值
	private Date regTime;		// 注册时间
	private String regIp;		// 注册IP
	private String regType;		// 注册方式
	private String regFrom;		// 注册类型
	private String unionId;    //唯一ID
	private String serviceId;		// 服务ID
	private String status;		// 状态
	private String reason;		// 冻结原因
	private String memo;		// 备注
	private String isTest;		// 是否测试
	private Date beginRegTime;		// 开始 注册时间
	private Date endRegTime;		// 结束 注册时间

	private String merchantId;	//商家id
    private String merchantName; //商家名称
	private String avatar;		//头像

	public UPassport() {
		super();
	}

	public UPassport(String id){
		super(id);
	}

	@Length(min=0, max=50, message="登录名长度必须介于 0 和 50 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=50, message="邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=50, message="手机长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=50, message="密码长度必须介于 0 和 50 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=64, message="登录盐值长度必须介于 0 和 64 之间")
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	@Length(min=0, max=64, message="注册IP长度必须介于 0 和 64 之间")
	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	
	@Length(min=0, max=1, message="注册方式长度必须介于 0 和 1 之间")
	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}
	
	@Length(min=0, max=1, message="注册类型长度必须介于 0 和 1 之间")
	public String getRegFrom() {
		return regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}
	
	@Length(min=0, max=64, message="服务ID长度必须介于 0 和 64 之间")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="冻结原因长度必须介于 0 和 100 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Length(min=0, max=100, message="备注长度必须介于 0 和 100 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=1, message="是否测试长度必须介于 0 和 1 之间")
	@NotBlank(message = "请选择一项")
	public String getIsTest() {
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
	
	public Date getBeginRegTime() {
		return beginRegTime;
	}

	public void setBeginRegTime(Date beginRegTime) {
		this.beginRegTime = beginRegTime;
	}
	
	public Date getEndRegTime() {
		return endRegTime;
	}

	public void setEndRegTime(Date endRegTime) {
		this.endRegTime = endRegTime;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}