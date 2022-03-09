package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import com.haohan.platform.service.sys.modules.xiaodian.entity.merchant.Shop;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求 店铺编辑
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminShopEditReq {

    // 必选项

    @NotBlank(message = "pmId不能为空")
    @Length(min = 0, max = 64, message = "pmId长度必须介于 0 和 64 之间")
    private String pmId;
    @NotBlank(message = "名称不能为空")
    @Length(min = 0, max = 64, message = "名称长度必须介于 0 和 64 之间")
    private String name;
    @NotBlank(message = "店铺地址不能为空")
    @Length(min = 0, max = 64, message = "店铺地址长度必须介于 0 和 64 之间")
    private String address;
    @NotBlank(message = "店铺负责人名称不能为空")
    @Length(min = 0, max = 64, message = "店铺负责人名称长度必须介于 0 和 64 之间")
    private String manager;
    @NotBlank(message = "电话不能为空")
    @Length(min = 0, max = 15, message = "电话长度必须介于 0 和 15 之间")
    private String telephone;
    @NotBlank(message = "状态不能为空")
    @Length(min = 0, max = 5, message = "状态长度必须介于 0 和 5 之间")
    private String status;        // 状态   字典 status_merchant :0 待审核 -1 停用 2 启用
    @NotBlank(message = "店铺经营范围不能为空")
    @Length(min = 0, max = 200, message = "店铺经营范围长度必须介于 0 和 200 之间")
    private String shopService;        // 店铺经营范围

    // 可选项

    @Length(min = 0, max = 64, message = "店铺id长度必须介于 0 和 64 之间")
    private String id;
    @Length(min = 0, max = 64, message = "经度长度必须介于 0 和 64 之间")
    private String mapLongitude;        // 经度
    @Length(min = 0, max = 64, message = "纬度长度必须介于 0 和 64 之间")
    private String mapLatitude;        // 纬度
    @Length(min = 0, max = 100, message = "营业时间长度必须介于 0 和 100 之间")
    private String onlineTime;        // 营业时间
    @Length(min = 0, max = 500, message = "店铺介绍长度必须介于 0 和 500 之间")
    private String shopDesc;        // 店铺介绍
    @Length(min = 0, max = 100, message = "店铺收款码长度必须介于 0 和 100 之间")
    private String payCode;        // 店铺收款码  保存图片组编号 只一张
    @Length(min = 0, max = 100, message = "店铺二维码长度必须介于 0 和 100 之间")
    private String qrcode;        // 店铺二维码  保存图片组编号 只一张
    @Length(min = 0, max = 100, message = "店铺logo长度必须介于 0 和 100 之间")
    private String shopLogo;    //店铺logo     保存图片组编号 只一张
    @Length(min = 0, max = 500, message = "店铺位置长度必须介于 0 和 500 之间")
    private String shopLocation; //店铺位置
    @Length(min = 0, max = 64, message = "配送距离必须介于 0 和 64 之间")
    private String deliverDistence;    //配送距离
    @Length(min = 0, max = 5, message = "店铺类型长度必须介于 0 和 5 之间")
    private String shopType;    //店铺类型: 0 餐饮, 1零售  终端使用
    @Length(min = 0, max = 5, message = "店铺级别长度必须介于 0 和 5 之间")
    private String shopLevel;    //店铺级别   0 总店, 1 分店
    @Length(min = 0, max = 64, message = "行业名称长度必须介于 0 和 64 之间")
    private String industry;        // 行业名称  仅展示
    @Length(min = 0, max = 64, message = "店铺分类id长度必须介于 0 和 64 之间")
    private String shopCategory;  // 店铺分类id
    @Length(min = 0, max = 5, message = "交易类型长度必须介于 0 和 5 之间")
    private String tradeType; // 交易类型shop_trade_type
    @Length(min = 0, max = 500, message = "备注长度必须介于 0 和 500 之间")
    String remarks;    // 备注

    // 默认值项

    @Length(min = 0, max = 5, message = "是否更新即速商品长度必须介于 0 和 5 之间")
    private String isUpdateJisu;  // 是否更新即速商品   yes_no
    @Length(min = 0, max = 5, message = "认证类型长度必须介于 0 和 5 之间")
    private String authType;  // 认证类型shop_auth_type  已认证/未认证
    @Length(min = 0, max = 5, message = "聚合平台类型长度必须介于 0 和 5 之间")
    private String aggregationType;  // 聚合平台类型aggregation_shop_type
    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 5, message = "店铺服务模式长度必须介于 0 和 5 之间")
    private String serviceType;        // 店铺服务模式  字典shop_service_type: 1 云小店 2 云连锁总店  3 云连锁分店

    /**
     * 参数转换
     *
     * @param shop
     */
    public void transToShop(Shop shop) {
        if (null != this.name) {
            shop.setName(this.name);
        }
        if (null != this.id) {
            shop.setId(this.id);
        }
        if (null != this.address) {
            shop.setAddress(this.address);
        }
        if (null != this.manager) {
            shop.setManager(this.manager);
        }
        if (null != this.telephone) {
            shop.setTelephone(this.telephone);
        }
        if (null != this.mapLongitude) {
            shop.setMapLongitude(this.mapLongitude);
        }
        if (null != this.mapLatitude) {
            shop.setMapLatitude(this.mapLatitude);
        }
        if (null != this.merchantId) {
            shop.setMerchantId(this.merchantId);
        }
        if (null != this.onlineTime) {
            shop.setOnlineTime(this.onlineTime);
        }
        if (null != this.shopService) {
            shop.setShopService(this.shopService);
        }
        if (null != this.shopDesc) {
            shop.setShopDesc(this.shopDesc);
        }
        if (null != this.status) {
            shop.setStatus(this.status);
        }
        if (null != this.payCode) {
            shop.setPayCode(this.payCode);
        }
        if (null != this.qrcode) {
            shop.setQrcode(this.qrcode);
        }
        if (null != this.shopLogo) {
            shop.setShopLogo(this.shopLogo);
        }
        if (null != this.shopLocation) {
            shop.setShopLocation(this.shopLocation);
        }
        if (null != this.deliverDistence) {
            shop.setDeliverDistence(this.deliverDistence);
        }
        if (null != this.shopType) {
            shop.setShopType(this.shopType);
        }
        if (null != this.shopLevel) {
            shop.setShopLevel(this.shopLevel);
        }
        if (null != this.isUpdateJisu) {
            shop.setIsUpdateJisu(this.isUpdateJisu);
        }
        if (null != this.serviceType) {
            shop.setServiceType(this.serviceType);
        }
        if (null != this.industry) {
            shop.setIndustry(this.industry);
        }
        if (null != this.shopCategory) {
            shop.setShopCategory(this.shopCategory);
        }
        if (null != this.authType) {
            shop.setAuthType(this.authType);
        }
        if (null != this.aggregationType) {
            shop.setAggregationType(this.aggregationType);
        }
        if (null != this.tradeType) {
            shop.setTradeType(this.tradeType);
        }
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMapLongitude() {
        return mapLongitude;
    }

    public void setMapLongitude(String mapLongitude) {
        this.mapLongitude = mapLongitude;
    }

    public String getMapLatitude() {
        return mapLatitude;
    }

    public void setMapLatitude(String mapLatitude) {
        this.mapLatitude = mapLatitude;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getShopService() {
        return shopService;
    }

    public void setShopService(String shopService) {
        this.shopService = shopService;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getDeliverDistence() {
        return deliverDistence;
    }

    public void setDeliverDistence(String deliverDistence) {
        this.deliverDistence = deliverDistence;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
