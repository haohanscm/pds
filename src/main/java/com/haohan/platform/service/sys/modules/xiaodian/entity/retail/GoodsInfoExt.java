package com.haohan.platform.service.sys.modules.xiaodian.entity.retail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haohan.platform.service.sys.common.utils.Collections3;
import com.haohan.platform.service.sys.common.utils.StringUtils;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import com.haohan.platform.service.sys.modules.xiaodian.constant.ICommonConstant;
import com.haohan.platform.service.sys.modules.xiaodian.constant.IGoodsConstant;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.DeliveryRules;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.GoodsGifts;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.SaleRules;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ServiceSelection;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 商品管理 商品详细信息 Entity
 * @author haohan
 * @version 2017-08-06
 */
@JsonIgnoreProperties({"isNewRecord"})
public class GoodsInfoExt implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;  // goodsId
	private String shopId;		// 店铺ID
	private String merchantId;		// 商家ID
    @JsonProperty("categoryId")
	private String goodsCategoryId;		// 商品分类
	private String goodsSn;		// 商品唯一编号
	private String goodsName;		// 商品名称
	private String detailDesc;		// 商品描述
	private String simpleDesc;		// 概要描述
	private String brandId;		// 商品品牌ID
	private String saleRule;		// 售卖规则标记 yes_no
	private String photoGroupNum;		// 图片组编号
	private String thumbUrl;		// 缩略图地址
	private Integer isMarketable;		// 是否上架
	private Integer storage;		// 库存数量
    private String serviceSelection; // 服务选项标记 yes_no
    private String deliveryRule; // 配送规则标记 yes_no
    private String goodsGift; // 赠品标记 yes_no
	private String goodsModel; // 商品规格标记  yes_no
    private String remarks;	// 备注
    private String goodsStatus; // 商品状态  IGoodsConstant.GoodsStatus  0 出售中 1 仓库中 2 已售罄
    private String goodsFrom; // 商品来源
    private String categorySn; // 分类编号
    private String sort; // 商品排序
    private String goodsType;//0实体商品，1虚拟商品
    private String scanCode;//扫码购编码
    private String thirdGoodsSn;  // 第三方编号 即速商品id
    private String generalSn;  // 通用编号 公共商品库通用编号
    private String salecFlag;

	//价格
    private BigDecimal marketPrice;		// 零售定价,单位元
    private BigDecimal vipPrice; // 促销价
    private BigDecimal virtualPrice;//虚拟价格
    private String unit;		// 计量单位
    // 售卖规则
    private String saleAreas;		// 售卖区域
    private String saleArriveType;		// 售卖时效 暂无使用
    private Integer minSaleNum;		// 起售数量
    private Integer limitBuyTimes;		// 限制购买次数
    private String saleDeliveryType;		// 配送类型限制  支持配送方式
    private Date beginSaleDate;  // 起售时间
    private Date endSaleDate;  //  结束售卖时间
    // 配送规则  配送方式仅为送货上门
    private String deliveryType;       // 配送方式 delivery_type
    private String deliveryPlanType;		// 配送计划类型delivery_plan_type  0.一次配送 1.周期性配送  2.指定时间配送
    private String deliverySchedule;		// 配送周期 字典delivery_schedule  1.每日 2.每周 3.每月 4.工作日
    private String arriveType;		// 配送时效  字典delivery_arrive  1.隔日达  2.及时达  3.当日达
    private Integer deliveryNum;		// 每次配送数量
    private Integer minNum;		// 起送数量
    private String rulesDesc;		// 规则描述
    private Date specificDate; // 指定时间
    private Integer startDayNum; //配送初始间隔天数
    private Integer deliveryTotalNum;  // 配送总数量
    // 赠品
    private String giftId;		// 赠品id
    private String giftName;		// 赠品名称
    private String giftRule;		// 赠送规则描述
    private Date beginDate;		// 起始日期
    private Date endDate;		// 结束日期
    private String giftSchedule;		// 赠送周期
    private Integer giftNum;		// 赠品数量
    private String giftUrl;   // 赠品图片地址
    // 服务选项
    private List<ServiceSelection> serviceSelectionList;
    // 商品规格
    private List<GoodsModel> goodsModelList;  // 当未启用规格时 存在一条默认商品规格
    private Map<String, JisuModelInfo> modelInfo; // 规格名称
    private List<GoodsModelTotal> infoList;  // 规格名称列表 用于转换为modelInfo

    private boolean isNewRecord = false;  // 是否新增记录

	private String shopName;
	private String merchantName;
	private String categoryName; // 分类名称
    private String collectionStatus;  // 商品收藏状态

    /**
     * 属性初始化 标记全不启用, 状态下架,仓库中,库存1000,排序10
     */
    public void init(){
        this.goodsModel = ICommonConstant.YesNoType.no.getCode();
        this.serviceSelection = ICommonConstant.YesNoType.no.getCode();
        this.saleRule = ICommonConstant.YesNoType.no.getCode();
        this.goodsGift = ICommonConstant.YesNoType.no.getCode();
        this.deliveryRule = ICommonConstant.YesNoType.no.getCode();
        this.isMarketable = StringUtils.toInteger(ICommonConstant.YesNoType.no.getCode());
        this.goodsStatus = IGoodsConstant.GoodsStatus.order.getCode();
        this.storage = 1000;
        this.sort = "10";
    }

    /**
     * 从 infoList中获取信息转换为modelInfo(map)
     */
    public void transToModelInfo(){
        if(Collections3.isEmpty(this.infoList)){
            return;
        }
        this.modelInfo = new HashMap<>(8);
        String modelId;
        int size = (this.infoList.size() > 4) ? this.infoList.size() : 4;
        for (GoodsModelTotal m : this.infoList) {
            if (null == m) {
                continue;
            }
            modelId = m.getModelId();
            if (this.modelInfo.containsKey(modelId)) {
                JisuModelInfo info = this.modelInfo.get(modelId);
                info.getSubModelId().add(m.getSubModelId());
                info.getSubModelName().add(m.getSubModelName());
            } else {
                if(StringUtils.isEmpty(modelId)){
                    continue;
                }
                JisuModelInfo info = new JisuModelInfo();
                info.setId(modelId);
                info.setName(m.getModelName());
                List<String> subIdList = new ArrayList<>(size);
                subIdList.add(m.getSubModelId());
                info.setSubModelId(subIdList);
                List<String> subNameList = new ArrayList<>(size);
                subNameList.add(m.getSubModelName());
                info.setSubModelName(subNameList);
                this.modelInfo.put(modelId, info);
            }
        }
    }

    /**
     * 复制 售卖规则 信息
     * @param goodsPriceRule 售卖规则
     * @return
     */
    public GoodsPriceRule transfGoodsPriceRule(GoodsPriceRule goodsPriceRule){
        goodsPriceRule.setGoodsId(this.id);
        goodsPriceRule.setShopId(this.shopId);
        goodsPriceRule.setMerchantId(this.merchantId);
        goodsPriceRule.setMarketPrice(this.marketPrice);
        goodsPriceRule.setVirtualPrice(this.virtualPrice);
        goodsPriceRule.setVipPrice(this.vipPrice);
        goodsPriceRule.setUnit(this.unit);
        return goodsPriceRule;
    }

    /**
     * 复制 售卖规则 信息
     * @param saleRules 售卖规则
     * @return
     */
    public SaleRules transfSaleRule(SaleRules saleRules){
        // 售卖区域
        Area area = new Area();
        area.setId(this.saleAreas);
        saleRules.setArea(area);

        saleRules.setMerchantId(this.merchantId);
        saleRules.setGoodsId(this.id);
        saleRules.setSaleArriveType(this.saleArriveType);
        saleRules.setMinSaleNum(this.minSaleNum);
        saleRules.setLimitBuyTimes(this.limitBuyTimes);
        saleRules.setSaleDeliveryType(this.saleDeliveryType);
        saleRules.setBeginSaleDate(this.beginSaleDate);
        saleRules.setEndSaleDate(this.endSaleDate);
        return saleRules;
    }

    /**
     * 复制 配送规则 信息
     * @param deliveryRules
     * @return
     */
    public DeliveryRules transfDeliveryRules(DeliveryRules deliveryRules){
        deliveryRules.setMerchantId(this.merchantId);
        deliveryRules.setGoodsId(this.id);
        deliveryRules.setDeliveryType(this.deliveryType);
        deliveryRules.setDeliveryPlanType(this.deliveryPlanType);
        deliveryRules.setDeliverySchedule(this.deliverySchedule);
        deliveryRules.setArriveType(this.arriveType);
        deliveryRules.setDeliveryNum(this.deliveryNum);
        deliveryRules.setMinNum(this.minNum);
        deliveryRules.setRulesDesc(this.rulesDesc);
        deliveryRules.setSpecificDate(this.specificDate);
        deliveryRules.setStartDayNum(this.startDayNum);
        deliveryRules.setDeliveryTotalNum(this.deliveryTotalNum);
        return deliveryRules;
    }

    /**
     * 复制 赠品 信息
     * @param goodsGifts
     * @return
     */
    public GoodsGifts transfGoodsGifts(GoodsGifts goodsGifts){
        goodsGifts.setMerchantId(this.merchantId);
        goodsGifts.setGoodsId(this.id);
        goodsGifts.setGiftId(this.giftId);
        goodsGifts.setGiftName(this.giftName);
        goodsGifts.setGiftRule(this.giftRule);
        goodsGifts.setBeginDate(this.beginDate);
        goodsGifts.setEndDate(this.endDate);
        goodsGifts.setGiftSchedule(this.giftSchedule);
        goodsGifts.setGiftNum(this.giftNum);
        goodsGifts.setGiftUrl(this.giftUrl);
        return goodsGifts;
    }

    public GoodsInfoExt() {
        super();
    }

    @Length(min=0, max=64, message="商品ID长度必须介于 0 和 64 之间")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min=0, max=500, message="商品分类长度必须介于 0 和 500 之间")
    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    @Length(min=0, max=64, message="商品唯一编号长度必须介于 0 和 64 之间")
    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    @Length(min=0, max=128, message="商品名称长度必须介于 0 和 128 之间")
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    @Length(min=0, max=255, message="概要描述长度必须介于 0 和 255 之间")
    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    @Length(min=0, max=64, message="商品品牌ID长度必须介于 0 和 64 之间")
    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }


    @Length(min=0, max=64, message="图片组编号长度必须介于 0 和 64 之间")
    public String getPhotoGroupNum() {
        return photoGroupNum;
    }

    public void setPhotoGroupNum(String photoGroupNum) {
        this.photoGroupNum = photoGroupNum;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Integer isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Length(min=0, max=1, message="启用售卖规则长度必须介于 0 和 1 之间")
    public String getSaleRule() {
        return saleRule;
    }

    public void setSaleRule(String saleRule) {
        this.saleRule = saleRule;
    }

    @Length(min=0, max=1, message="启用服务选项长度必须介于 0 和 1 之间")
    public String getServiceSelection() {
        return serviceSelection;
    }

    public void setServiceSelection(String serviceSelection) {
        this.serviceSelection = serviceSelection;
    }

    @Length(min=0, max=1, message="启用配送规则长度必须介于 0 和 1 之间")
    public String getDeliveryRule() {
        return deliveryRule;
    }

    public void setDeliveryRule(String deliveryRule) {
        this.deliveryRule = deliveryRule;
    }

    @Length(min=0, max=1, message="启用赠品长度必须介于 0 和 1 之间")
    public String getGoodsGift() {
        return goodsGift;
    }

    public void setGoodsGift(String goodsGift) {
        this.goodsGift = goodsGift;
    }

    @Length(min=0, max=1, message="启用规格长度必须介于 0 和 1 之间")
    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Length(min=0, max=64, message="售卖时效长度必须介于 0 和 64 之间")
    public String getSaleArriveType() {
        return saleArriveType;
    }

    public void setSaleArriveType(String saleArriveType) {
        this.saleArriveType = saleArriveType;
    }

    public Integer getMinSaleNum() {
        return minSaleNum;
    }

    public void setMinSaleNum(Integer minSaleNum) {
        this.minSaleNum = minSaleNum;
    }

    public Integer getLimitBuyTimes() {
        return limitBuyTimes;
    }

    public void setLimitBuyTimes(Integer limitBuyTimes) {
        this.limitBuyTimes = limitBuyTimes;
    }

    @Length(min=0, max=1000, message="售卖区域长度必须介于 0 和 1000 之间")
    public String getSaleAreas() {
        return saleAreas;
    }

    public void setSaleAreas(String saleAreas) {
        this.saleAreas = saleAreas;
    }

    @Length(min=0, max=64, message="售卖配送类型限制长度必须介于 0 和 64 之间")
    public String getSaleDeliveryType() {
        return saleDeliveryType;
    }

    public void setSaleDeliveryType(String saleDeliveryType) {
        this.saleDeliveryType = saleDeliveryType;
    }

    public Date getBeginSaleDate() {
        return beginSaleDate;
    }

    public void setBeginSaleDate(Date beginSaleDate) {
        this.beginSaleDate = beginSaleDate;
    }

    public Date getEndSaleDate() {
        return endSaleDate;
    }

    public void setEndSaleDate(Date endSaleDate) {
        this.endSaleDate = endSaleDate;
    }

    @Length(min=0, max=255, message="配送周期长度必须介于 0 和 255 之间")
    public String getDeliverySchedule() {
        return deliverySchedule;
    }

    public void setDeliverySchedule(String deliverySchedule) {
        this.deliverySchedule = deliverySchedule;
    }

    @Length(min=0, max=64, message="配送时效长度必须介于 0 和 64 之间")
    public String getArriveType() {
        return arriveType;
    }

    public void setArriveType(String arriveType) {
        this.arriveType = arriveType;
    }

    public Integer getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    @Length(min=0, max=64, message="配送计划类型长度必须介于 0 和 64 之间")
    public String getDeliveryPlanType() {
        return deliveryPlanType;
    }

    public void setDeliveryPlanType(String deliveryPlanType) {
        this.deliveryPlanType = deliveryPlanType;
    }

    @Length(min=0, max=64, message="配送方式长度必须介于 0 和 64 之间")
    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Length(min=0, max=500, message="规则描述长度必须介于 0 和 500 之间")
    public String getRulesDesc() {
        return rulesDesc;
    }

    public void setRulesDesc(String rulesDesc) {
        this.rulesDesc = rulesDesc;
    }

    public Date getSpecificDate() {
        return specificDate;
    }

    public void setSpecificDate(Date specificDate) {
        this.specificDate = specificDate;
    }

    public Integer getStartDayNum() {
        return startDayNum;
    }

    public void setStartDayNum(Integer startDayNum) {
        this.startDayNum = startDayNum;
    }

    public Integer getDeliveryTotalNum() {
        return deliveryTotalNum;
    }

    public void setDeliveryTotalNum(Integer deliveryTotalNum) {
        this.deliveryTotalNum = deliveryTotalNum;
    }

    @Length(min=0, max=64, message="赠品id长度必须介于 0 和 64 之间")
    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    @Length(min=0, max=64, message="赠品名称长度必须介于 0 和 64 之间")
    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    @Length(min=0, max=64, message="赠送规则长度必须介于 0 和 64 之间")
    public String getGiftRule() {
        return giftRule;
    }

    public void setGiftRule(String giftRule) {
        this.giftRule = giftRule;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndDate() {
        return endDate;
    }

    public String getSalecFlag() {
        return salecFlag;
    }

    public void setSalecFlag(String salecFlag) {
        this.salecFlag = salecFlag;
    }


    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Length(min=0, max=64, message="赠送周期长度必须介于 0 和 64 之间")
    public String getGiftSchedule() {
        return giftSchedule;
    }

    public void setGiftSchedule(String giftSchedule) {
        this.giftSchedule = giftSchedule;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    @Length(min=0, max=255, message="赠品图片地址长度必须介于 0 和 255 之间")
    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public List<ServiceSelection> getServiceSelectionList() {
        return serviceSelectionList;
    }

    public void setServiceSelectionList(List<ServiceSelection> serviceSelectionList) {
        this.serviceSelectionList = serviceSelectionList;
    }

    public List<GoodsModel> getGoodsModelList() {
        return goodsModelList;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(String goodsFrom) {
        this.goodsFrom = goodsFrom;
    }

    public void setGoodsModelList(List<GoodsModel> goodsModelList) {
        this.goodsModelList = goodsModelList;
    }

    public boolean getIsNewRecord() {
        return isNewRecord;
    }

    public void setIsNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

//    public Map<String, JisuModelInfo> getModelInfo() {
//        return modelInfo;
//    }
//
//    public void setModelInfo(Map<String, JisuModelInfo> modelInfo) {
//        this.modelInfo = modelInfo;
//    }

    public String getCategorySn() {
        return categorySn;
    }

    public void setCategorySn(String categorySn) {
        this.categorySn = categorySn;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public BigDecimal getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(BigDecimal virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public String getThirdGoodsSn() {
        return thirdGoodsSn;
    }

    public void setThirdGoodsSn(String thirdGoodsSn) {
        this.thirdGoodsSn = thirdGoodsSn;
    }

    public String getGeneralSn() {
        return generalSn;
    }

    public void setGeneralSn(String generalSn) {
        this.generalSn = generalSn;
    }

    public String getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(String collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public List<GoodsModelTotal> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<GoodsModelTotal> infoList) {
        this.infoList = infoList;
    }

    public Map<String, JisuModelInfo> getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(Map<String, JisuModelInfo> modelInfo) {
        this.modelInfo = modelInfo;
    }
}