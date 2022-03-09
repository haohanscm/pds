package com.haohan.platform.service.sys.modules.xiaodian.entity.merchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.framework.utils.JacksonUtils;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 店铺管理Entity
 * @author haohan
 * @version 2017-12-15
 */
@JsonIgnoreProperties({"createDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Shop extends DataEntity<Shop> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 店铺编号
	private String name;		// 店铺名称
	private Area area;		// 店铺区域
	private String address;		// 店铺地址
	private String manager;		// 店铺负责人
	private String telephone;		// 店铺电话
	private String mapLongitude;		// 经度
	private String mapLatitude;		// 纬度
	private String merchantId;		// 商家ID
	private String onlineTime;		// 营业时间
	private String shopService;		// 店铺经营范围  仅展示
	private String templateId;		// 店铺模板ID
	private String photoGroupNum;		// 图片组编号
	private String shopDesc;		// 店铺介绍
	private String status;		// 状态   字典 status_merchant :0 待审核 -1 停用 2 启用
	private String payCode;		// 店铺收款码  保存图片组编号 只一张
	private String qrcode;		// 店铺二维码  保存图片组编号 只一张
	private String shopLogo;    //店铺logo     保存图片组编号 只一张
	private String shopLocation; //店铺位置
	private String deliverDistence;	//配送距离
	private String shopType;    //店铺类型: 0 餐饮, 1零售  终端使用
	private String shopLevel;    //店铺级别   0 总店, 1 分店
	private String isUpdateJisu;  // 是否更新即速商品   yes_no
	private String serviceType;		// 店铺服务模式  字典shop_service_type: 1 云小店 2 云连锁总店  3 云连锁分店
	private String industry;		// 行业名称  仅展示
    private String shopCategory;  // 店铺分类id
    private String authType;  // 认证类型shop_auth_type  已认证/未认证
    private String aggregationType;  // 聚合平台类型aggregation_shop_type
    private String tradeType; // 交易类型shop_trade_type

    private String merchantName;
    private String shopCategoryName;
    private String pmId;

	public Shop() {
		super();
	}

	public Shop(String id){
		super(id);
	}

	@Length(min=0, max=50, message="店铺编号长度必须介于 0 和 50 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="店铺名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=100, message="店铺地址长度必须介于 0 和 100 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="店铺负责人长度必须介于 0 和 100 之间")
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	@Length(min=0, max=50, message="店铺电话长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Length(min=0, max=64, message="经度长度必须介于 0 和 64 之间")
	public String getMapLongitude() {
		return mapLongitude;
	}

	public void setMapLongitude(String mapLongitude) {
		this.mapLongitude = mapLongitude;
	}
	
	@Length(min=0, max=64, message="纬度长度必须介于 0 和 64 之间")
	public String getMapLatitude() {
		return mapLatitude;
	}

	public void setMapLatitude(String mapLatitude) {
		this.mapLatitude = mapLatitude;
	}
	
	@Length(min=0, max=50, message="商家ID长度必须介于 0 和 50 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=100, message="营业时间长度必须介于 0 和 100 之间")
	public String getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	@Length(min=0, max=200, message="店铺经营范围长度必须介于 0 和 200 之间")
	public String getShopService() {
		return shopService;
	}

	public void setShopService(String shopService) {
		this.shopService = shopService;
	}
	
	@Length(min=0, max=100, message="店铺模板ID长度必须介于 0 和 100 之间")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Length(min=0, max=100, message="图片组编号长度必须介于 0 和 100 之间")
	public String getPhotoGroupNum() {
		return photoGroupNum;
	}

	public void setPhotoGroupNum(String photoGroupNum) {
		this.photoGroupNum = photoGroupNum;
	}
	
	@Length(min=0, max=500, message="店铺介绍长度必须介于 0 和 500 之间")
	public String getShopDesc() {
		return shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	
	@Length(min=0, max=2, message="状态长度必须介于 0 和 2 之间")
	@NotBlank
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="店铺收款码长度必须介于 0 和 100 之间")
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
	@Length(min=0, max=100, message="店铺二维码长度必须介于 0 和 100 之间")
	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

    @Length(min=0, max=500, message="店铺位置长度必须介于 0 和 500 之间")
	public String getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}

    @Length(min=0, max=100, message="店铺logo长度必须介于 0 和 100 之间")
	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public ShopLocation fetchShopLocation(){
		return JacksonUtils.readValue(this.shopLocation,ShopLocation.class);
	}

    @Length(min = 0, max = 5, message = "店铺类型长度必须介于 0 和 5 之间")
	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

    @Length(min=0, max=64, message="配送距离必须介于 0 和 64 之间")
	public String getDeliverDistence() {
		return deliverDistence;
	}

	public void setDeliverDistence(String deliverDistence) {
		this.deliverDistence = deliverDistence;
	}

    @Length(min = 0, max = 5, message = "店铺级别长度必须介于 0 和 5 之间")
	public String getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(String shopLevel) {
		this.shopLevel = shopLevel;
	}

    @Length(min = 0, max = 5, message = "是否更新即速商品长度必须介于 0 和 5 之间")
    public String getIsUpdateJisu() {
        return isUpdateJisu;
    }

    public void setIsUpdateJisu(String isUpdateJisu) {
        this.isUpdateJisu = isUpdateJisu;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Length(min = 0, max = 64, message = "行业名称长度必须介于 0 和 64 之间")
    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Length(min=0, max=64, message="店铺分类id长度必须介于 0 和 64 之间")
    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    @Length(min = 0, max = 5, message = "认证类型长度必须介于 0 和 5 之间")
    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @Length(min = 0, max = 5, message = "聚合平台类型长度必须介于 0 和 5 之间")
    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    @Length(min = 0, max = 5, message = "交易类型长度必须介于 0 和 5 之间")
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }
}