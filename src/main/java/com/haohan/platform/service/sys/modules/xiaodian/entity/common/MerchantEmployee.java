package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 员工管理Entity
 * @author haohan
 * @version 2018-10-18
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantEmployee extends DataEntity<MerchantEmployee> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;			//平台商家ID
	private String merchantId;		// 商家ID
	private String passportId;		// 用户ID
	private String role;		// 角色 类型:采购/配送/分拣/运营/司机
	private String telephone;	//手机号
	private String name;		//姓名
	private Date regDate;		// 注册日期
	private String origin;		// 来源  暂未使用
	private String status;		// 状态
	private Date beginRegDate;		// 开始 注册日期
	private Date endRegDate;		// 结束 注册日期

    private String merchantName;  // 商家名称
    private String pmName;  // 平台商家名称
    private String password;  // 通行证密码

	public MerchantEmployee() {
		super();
	}

	public MerchantEmployee(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="用户ID长度必须介于 0 和 64 之间")
	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}
	
	@Length(min=0, max=5, message="角色长度必须介于 0 和 5 之间")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	@Length(min=0, max=10, message="来源长度必须介于 0 和 10 之间")
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginRegDate() {
		return beginRegDate;
	}

	public void setBeginRegDate(Date beginRegDate) {
		this.beginRegDate = beginRegDate;
	}
	
	public Date getEndRegDate() {
		return endRegDate;
	}

	public void setEndRegDate(Date endRegDate) {
		this.endRegDate = endRegDate;
	}

    @Length(min = 0, max = 15, message = "电话长度必须介于 0 和 15 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Length(min=0, max=64, message="姓名长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Length(min=0, max=64, message="平台商家ID长度必须介于 0 和 64 之间")
	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}