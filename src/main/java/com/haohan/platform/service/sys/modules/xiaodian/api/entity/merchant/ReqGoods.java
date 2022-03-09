package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.modules.xiaodian.entity.delivery.ServiceSelection;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsInfoExt;
import com.haohan.platform.service.sys.modules.xiaodian.entity.retail.GoodsModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户后台 商品请求参数
 * Created by dy on 2018/9/27.
 */
public class ReqGoods implements Serializable {

    private int pageNo;
    private int pageSize;
    private List<String> goodsSns; // 编号列表
    private String goodsStatus; // 商品状态  IGoodsConstant.GoodsStatus  0 出售中 1 仓库中 2 已售罄

    private String merchantId;		// 商家ID
    private String categoryId;		// 商品分类
    private String goodsName;		// 商品名称
    private String goodsSn;		// 商品唯一编号

    private String shopId;		// 店铺ID
    private String detailDesc;		// 商品描述
    private String simpleDesc;  // 关键词
    private String thumbUrl;		// 缩略图地址
    private Integer isMarketable;		// 是否上架
    private Integer storage;		// 库存数量
    private BigDecimal marketPrice;		// 零售定价,单位元
    private BigDecimal virtualPrice;//虚拟价格
    private String unit;		// 计量单位
    private String sort; // 商品排序

    private String goodsModel; // 商品规格标记  yes_no
    private List<GoodsModel> goodsModelList;  // 规格详情

    private String goodsFrom; // 商品来源

    private String saleRule;		// 售卖规则标记 yes_no
    private String serviceSelection; // 服务选项标记  yes_no
    private String deliveryRule; // 配送规则标记  yes_no
    private String goodsGift; // 赠品标记  yes_no

    // 售卖规则
    private String saleAreas;		// 售卖区域
    private String saleArriveType;		// 售卖时效 暂无使用
    private Integer minSaleNum;		// 起售数量
    private Integer limitBuyTimes;		// 限制购买次数
    private String saleDeliveryType;		// 配送类型限制  支持配送方式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date beginSaleDate;  // 起售时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endSaleDate;  //  结束售卖时间
    // 配送规则  配送方式仅为送货上门
    private String deliveryType;       // 配送方式
    private String deliveryPlanType;		// 配送计划类型delivery_plan_type  0.一次配送 1.周期性配送  2.指定时间配送
    private String deliverySchedule;		// 配送周期 字典delivery_schedule  1.每日 2.每周 3.每月 4.工作日
    private String arriveType;		// 配送时效  字典delivery_arrive  1.隔日达  2.及时达  3.当日达
    private Integer deliveryNum;		// 每次配送数量
    private Integer minNum;		// 起送数量
    private String rulesDesc;		// 规则描述
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date specificDate; // 指定时间
    private Integer startDayNum; //配送初始间隔天数
    private Integer deliveryTotalNum;  // 配送总数量
    // 赠品
    private String giftId;		// 赠品id
    private String giftName;		// 赠品名称
    private String giftRule;		// 赠送规则描述
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date beginDate;		// 起始日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endDate;		// 结束日期
    private String giftSchedule;		// 赠送周期
    private Integer giftNum;		// 赠品数量
    private String giftUrl;   // 赠品图片地址
    // 服务选项
    private List<ServiceSelection> serviceSelectionList;



    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
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

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGoodsModel() {
        return goodsModel;
    }

    public void setGoodsModel(String goodsModel) {
        this.goodsModel = goodsModel;
    }


    public List<GoodsModel> getGoodsModelList() {
        return goodsModelList;
    }

    public void setGoodsModelList(List<GoodsModel> goodsModelList) {
        this.goodsModelList = goodsModelList;
    }

    public List<String> getGoodsSns() {
        return goodsSns;
    }

    public void setGoodsSns(List<String> goodsSns) {
        this.goodsSns = goodsSns;
    }

    public String getGoodsFrom() {
        return goodsFrom;
    }

    public void setGoodsFrom(String goodsFrom) {
        this.goodsFrom = goodsFrom;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSaleRule() {
        return saleRule;
    }

    public void setSaleRule(String saleRule) {
        this.saleRule = saleRule;
    }

    public String getServiceSelection() {
        return serviceSelection;
    }

    public void setServiceSelection(String serviceSelection) {
        this.serviceSelection = serviceSelection;
    }

    public String getDeliveryRule() {
        return deliveryRule;
    }

    public void setDeliveryRule(String deliveryRule) {
        this.deliveryRule = deliveryRule;
    }

    public String getGoodsGift() {
        return goodsGift;
    }

    public void setGoodsGift(String goodsGift) {
        this.goodsGift = goodsGift;
    }

    public String getSaleAreas() {
        return saleAreas;
    }

    public void setSaleAreas(String saleAreas) {
        this.saleAreas = saleAreas;
    }

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

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryPlanType() {
        return deliveryPlanType;
    }

    public void setDeliveryPlanType(String deliveryPlanType) {
        this.deliveryPlanType = deliveryPlanType;
    }

    public String getDeliverySchedule() {
        return deliverySchedule;
    }

    public void setDeliverySchedule(String deliverySchedule) {
        this.deliverySchedule = deliverySchedule;
    }

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

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftRule() {
        return giftRule;
    }

    public void setGiftRule(String giftRule) {
        this.giftRule = giftRule;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

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

    public BigDecimal getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(BigDecimal virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public String getSimpleDesc() {
        return simpleDesc;
    }

    public void setSimpleDesc(String simpleDesc) {
        this.simpleDesc = simpleDesc;
    }

    // 复制属性到GoodsInfoExt 基础信息/价格/规格
    public GoodsInfoExt transfBaseGoods(GoodsInfoExt goods){
        goods.setMerchantId(this.merchantId);
        goods.setShopId(this.shopId);
        goods.setGoodsSn(this.goodsSn);
        goods.setGoodsCategoryId(this.categoryId);
        goods.setGoodsName(this.goodsName);
        goods.setDetailDesc(this.detailDesc);
        goods.setSimpleDesc(this.simpleDesc);
        goods.setThumbUrl(this.thumbUrl);
        goods.setIsMarketable(this.isMarketable);
        goods.setStorage(this.storage);
        goods.setGoodsModel(this.goodsModel);
        goods.setSort(this.sort);
        // 价格
        goods.setMarketPrice(this.marketPrice);
        goods.setUnit(this.unit);
        // 规格
        goods.setGoodsModelList(this.goodsModelList);
        return goods;
    }


    // 复制属性到GoodsInfoExt 售卖规则/赠品规则/配送规则/服务选项
    public GoodsInfoExt transfExtGoods(GoodsInfoExt goods) {
        goods.setGoodsSn(this.goodsSn);
        // 标记
        goods.setServiceSelection(this.serviceSelection);
        goods.setSaleRule(this.saleRule);
        goods.setDeliveryRule(this.deliveryRule);
        goods.setGoodsGift(this.goodsGift);
        // 服务选项
        goods.setServiceSelectionList(this.serviceSelectionList);
        // 售卖规则
        goods.setSaleAreas(this.saleAreas);
        goods.setSaleArriveType(this.saleArriveType);
        goods.setMinSaleNum(this.minSaleNum);
        goods.setLimitBuyTimes(this.limitBuyTimes);
        goods.setSaleDeliveryType(this.saleDeliveryType);
        goods.setBeginSaleDate(this.beginSaleDate);
        goods.setEndSaleDate(this.endSaleDate);
        // 配送规则
        goods.setDeliveryType(this.deliveryType);
        goods.setDeliveryPlanType(this.deliveryPlanType);
        goods.setDeliverySchedule(this.deliverySchedule);
        goods.setArriveType(this.arriveType);
        goods.setDeliveryNum(this.deliveryNum);
        goods.setMinNum(this.minNum);
        goods.setRulesDesc(this.rulesDesc);
        goods.setSpecificDate(this.specificDate);
        goods.setStartDayNum(this.startDayNum);
        goods.setDeliveryTotalNum(this.deliveryTotalNum);
        // 赠品
        goods.setGiftId(this.giftId);
        goods.setGiftName(this.giftName);
        goods.setGiftRule(this.giftRule);
        goods.setBeginDate(this.beginDate);
        goods.setEndDate(this.endDate);
        goods.setGiftSchedule(this.giftSchedule);
        goods.setGiftNum(this.giftNum);
        goods.setGiftUrl(this.giftUrl);
        return goods;
    }
}
