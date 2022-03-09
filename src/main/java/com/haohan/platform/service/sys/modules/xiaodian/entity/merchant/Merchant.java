package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import com.haohan.platform.service.sys.modules.xiaodian.entity.UPassport;
import org.hibernate.validator.constraints.Length;

/**
 * 商家管理Entity
 * @author haohan
 * @version 2017-08-05
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Merchant extends DataEntity<Merchant> {
	
	private static final long serialVersionUID = 1L;
	private UPassport upassport;		// 归属用户
	private String merchantName;		// 名称
	private String merchantType;		//商家的店铺类型  0:餐饮   1:零售
	private Area area;		// 区域
	private String address;		// 商家地址
	private String contact;		// 联系人
	private String telephone;		// 电话
	private String industry;		// 行业名称
	private String scale;		// 规模
	private String bizDesc;		// 业务介绍
	private User user;		// 业务专管员
	private String partnerNum;	//渠道编号
	private Integer status;		// 状态
    private String pdsType;  // 采购配送商家类型  0 普通商家  1 平台商家
	private String prodType;	//产品类型
	private String isAutomaticOrder;		//是否启用自动分单
    private String productLine;  // 产品线类型
    private String parentId;  // 归属商家id


	//辅助字段
	private String partnerName;	//渠道名称
	private String parentName;

	public Merchant() {
		super();
	}

	public Merchant(String id){
		super(id);
	}

	public UPassport getUpassport() {
		return upassport;
	}

	public void setUpassport(UPassport upassport) {
		this.upassport = upassport;
	}
	
	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
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
	
	@Length(min=0, max=500, message="商家地址长度必须介于 0 和 500 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=50, message="联系人长度必须介于 0 和 50 之间")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Length(min=0, max=50, message="电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=50, message="行业名称长度必须介于 0 和 50 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=50, message="规模长度必须介于 0 和 50 之间")
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
	
	@Length(min=0, max=1000, message="业务介绍长度必须介于 0 和 1000 之间")
	public String getBizDesc() {
		return bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    @Length(min=0, max=64, message="渠道编号长度必须介于 0 和 64 之间")
	public String getPartnerNum() {
		return partnerNum;
	}

	public void setPartnerNum(String partnerNum) {
		this.partnerNum = partnerNum;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

    @Length(min=0, max=5, message="商家的店铺类型长度必须介于 0 和 5 之间")
	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

    @Length(min=0, max=5, message="是否启用自动分单长度必须介于 0 和 5 之间")
	public String getIsAutomaticOrder() {
		return isAutomaticOrder;
	}

	public void setIsAutomaticOrder(String isAutomaticOrder) {
		this.isAutomaticOrder = isAutomaticOrder;
	}

    @Length(min=0, max=5, message="采购配送商家类型长度必须介于 0 和 5 之间")
    public String getPdsType() {
        return pdsType;
    }

    public void setPdsType(String pdsType) {
        this.pdsType = pdsType;
    }

    @Length(min=0, max=5, message="产品类型长度必须介于 0 和 5 之间")
	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

    @Length(min=0, max=5, message="产品线类型长度必须介于 0 和 5 之间")
    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    @Length(min=0, max=64, message="归属商家id长度必须介于 0 和 64 之间")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}