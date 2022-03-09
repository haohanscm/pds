package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 商家资料库Entity
 * @author haohan
 * @version 2018-04-07
 */
public class MerchantDatabase extends DataEntity<MerchantDatabase> {
	
	private static final long serialVersionUID = 1L;
	private String regName;		// 商户注册全称
	private String regUser;		// 经营法人
	private Area area;		// 所属区域
	private String opAddress;		// 经营地址
	private String contact;		// 商家联系人
	private String telephone;		// 联系手机
	private String phoneNumber;		// 座机电话
	private String merchantType;		// 商户类别
	private String industry;		// 行业类别
	private String website;		// 网站名称
	private String taobaoShop;		// 淘宝店名称
	private String marketPlatform;		// 现有推广平台
	private String[] marketPlatformMore;		// 多选
	private String payTools;		// 现有支付工具
	private String[] payToolsMore;		//  多选
	private String shopName;		// 店铺名称
	private String operateArea;		// 经营面积
	private String workerNum;		// 员工人数
	private String shopDesc;		// 店铺介绍
	private String serviceTime;		// 营业时间
	private String bizDesc;		// 业务介绍
	private String shopAddress;		// 店铺地址
	private String bizLicense;		// 营业执照
	private String environment;		// 周围环境
	private String shopService;		// 店铺服务
	private String shopSale;		// 年经营流水
	private String pictureFile;		// 照片资料
	private Date initTime;		// 收录时间
	private Date beginInitTime;		// 开始 收录时间
	private Date endInitTime;		// 结束 收录时间
	private String bizfromType;  // 商机来源
	private String status; // 是否审核  字典 yes_no

	public MerchantDatabase() {
		super();
	}

	public MerchantDatabase(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商户注册全称长度必须介于 0 和 64 之间")
	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}
	
	@Length(min=0, max=64, message="经营法人长度必须介于 0 和 64 之间")
	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	
	public Area getArea() {
        // // 导入Excel时防止出现NPE异常
        if (this.area == null) {
            this.area = new Area();
        }
        return this.area;
    }

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=200, message="经营地址长度必须介于 0 和 200 之间")
	public String getOpAddress() {
		return opAddress;
	}

	public void setOpAddress(String opAddress) {
		this.opAddress = opAddress;
	}
	
	@Length(min=0, max=64, message="商家联系人长度必须介于 0 和 64 之间")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Length(min=0, max=64, message="联系手机长度必须介于 0 和 64 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=64, message="座机电话长度必须介于 0 和 64 之间")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Length(min=0, max=64, message="商户类别长度必须介于 0 和 64 之间")
	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	
	@Length(min=0, max=64, message="行业类别长度必须介于 0 和 64 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@Length(min=0, max=64, message="网站名称长度必须介于 0 和 64 之间")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Length(min=0, max=64, message="淘宝店名称长度必须介于 0 和 64 之间")
	public String getTaobaoShop() {
		return taobaoShop;
	}

	public void setTaobaoShop(String taobaoShop) {
		this.taobaoShop = taobaoShop;
	}
	
	@Length(min=0, max=64, message="现有推广平台长度必须介于 0 和 64 之间")
	public String getMarketPlatform() {
		return marketPlatform;
	}

	public void setMarketPlatform(String marketPlatform) {
		this.marketPlatform = marketPlatform;
	}
	
	@Length(min=0, max=64, message="现有支付工具长度必须介于 0 和 64 之间")
	public String getPayTools() {
		return payTools;
	}

	public void setPayTools(String payTools) {
		this.payTools = payTools;
	}
	
	@Length(min=0, max=64, message="店铺名称长度必须介于 0 和 64 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Length(min=0, max=64, message="经营面积长度必须介于 0 和 64 之间")
	public String getOperateArea() {
		return operateArea;
	}

	public void setOperateArea(String operateArea) {
		this.operateArea = operateArea;
	}
	
	@Length(min=0, max=64, message="员工人数长度必须介于 0 和 64 之间")
	public String getWorkerNum() {
		return workerNum;
	}

	public void setWorkerNum(String workerNum) {
		this.workerNum = workerNum;
	}
	
	@Length(min=0, max=200, message="店铺介绍长度必须介于 0 和 200 之间")
	public String getShopDesc() {
		return shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	
	@Length(min=0, max=200, message="营业时间长度必须介于 0 和 200 之间")
	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	
	@Length(min=0, max=500, message="业务介绍长度必须介于 0 和 500 之间")
	public String getBizDesc() {
		return bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}
	
	@Length(min=0, max=500, message="店铺地址长度必须介于 0 和 500 之间")
	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	
	@Length(min=0, max=200, message="营业执照长度必须介于 0 和 200 之间")
	public String getBizLicense() {
		return bizLicense;
	}

	public void setBizLicense(String bizLicense) {
		this.bizLicense = bizLicense;
	}
	
	@Length(min=0, max=200, message="周围环境长度必须介于 0 和 200 之间")
	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	@Length(min=0, max=64, message="店铺服务长度必须介于 0 和 64 之间")
	public String getShopService() {
		return shopService;
	}

	public void setShopService(String shopService) {
		this.shopService = shopService;
	}
	
	@Length(min=0, max=64, message="年经营流水长度必须介于 0 和 64 之间")
	public String getShopSale() {
		return shopSale;
	}

	public void setShopSale(String shopSale) {
		this.shopSale = shopSale;
	}
	
	@Length(min=0, max=200, message="照片资料长度必须介于 0 和 200 之间")
	public String getPictureFile() {
		return pictureFile;
	}

	public void setPictureFile(String pictureFile) {
		this.pictureFile = pictureFile;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInitTime() {
		return initTime;
	}

	public void setInitTime(Date initTime) {
		this.initTime = initTime;
	}
	
	public Date getBeginInitTime() {
		return beginInitTime;
	}

	public void setBeginInitTime(Date beginInitTime) {
		this.beginInitTime = beginInitTime;
	}
	
	public Date getEndInitTime() {
		return endInitTime;
	}

	public void setEndInitTime(Date endInitTime) {
		this.endInitTime = endInitTime;
	}

	public String getBizfromType() {
		return bizfromType;
	}

	public void setBizfromType(String bizfromType) {
		this.bizfromType = bizfromType;
	}


	public String[] getMarketPlatformMore() {
		return marketPlatformMore;
	}

	public void setMarketPlatformMore(String[] marketPlatformMore) {
		this.marketPlatformMore = marketPlatformMore;
	}

	public String[] getPayToolsMore() {
		return payToolsMore;
	}

	public void setPayToolsMore(String[] payToolsMore) {
		this.payToolsMore = payToolsMore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}