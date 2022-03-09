package com.haohan.platform.service.sys.modules.xiaodian.entity.order;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单明细Entity
 * @author haohan
 * @version 2018-09-01
 */
public class GoodsOrderDetail extends DataEntity<GoodsOrderDetail> {
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单编号
	private String merchantId;		// 商家id
	private String goodsId;		// 商品编号
	private String modelId;		//sku id
	private String goodsName;		// 商品名称
	private BigDecimal goodsPrice;		// 实际售价
	private BigDecimal marketPrice;		// 市场售价
	private BigDecimal goodsNum;		// 商品份数
	private String goodsUnit;		// 商品单位
	private String modelName;			//商品规格
	private String goodsAttrIds;		// 商品属性集合
	private String extAttr;		// 扩展属性
	private Integer isReal;		// 是否实物
	private Integer cartGoodsType;		//购物类型
	private String activityId;		// 活动编号
	private String giftName;		// 赠品名称
	private String giftId;		// 赠品id
	private String giftSchedule;		// 赠送周期
	private Integer giftNum;		// 赠送数量
	private String deliverySchedule;		// 配送周期
	private Date deliveryStartDate;	  //配送起始时间
	private String arriveType;		// 配送时效
	private Integer deliveryNum;		// 每次配送数量
	private String deliveryPlanType;		// 配送计划类型
	private String deliveryType;		// 配送方式
	private Integer deliveryTotalNum;	//配送总数量
	private String serviceName;		// 服务名称
	private String serviceDetail;		// 服务内容
	private BigDecimal servicePrice;		// 服务价格
	private String serviceSchedule;		// 服务周期
	private Integer serviceNum;		// 服务次数


	//查询销量额外需要的参数
	private String shopId;
	private String payStatus;
	private Date startTime;
	private Date endTime;

	public GoodsOrderDetail() {
		super();
	}

	public GoodsOrderDetail(String id){
		super(id);
	}

	@Length(min=0, max=64, message="订单编号长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商品编号长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public BigDecimal getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(BigDecimal goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	@Length(min=0, max=50, message="商品单位长度必须介于 0 和 50 之间")
	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	
	@Length(min=0, max=500, message="商品属性集合长度必须介于 0 和 500 之间")
	public String getGoodsAttrIds() {
		return goodsAttrIds;
	}

	public void setGoodsAttrIds(String goodsAttrIds) {
		this.goodsAttrIds = goodsAttrIds;
	}
	
	public String getExtAttr() {
		return extAttr;
	}

	public void setExtAttr(String extAttr) {
		this.extAttr = extAttr;
	}
	
	public Integer getIsReal() {
		return isReal;
	}

	public void setIsReal(Integer isReal) {
		this.isReal = isReal;
	}
	
	public Integer getCartGoodsType() {
		return cartGoodsType;
	}

	public void setCartGoodsType(Integer cartGoodsType) {
		this.cartGoodsType = cartGoodsType;
	}
	
	@Length(min=0, max=64, message="活动编号长度必须介于 0 和 64 之间")
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@Length(min=0, max=64, message="赠品名称长度必须介于 0 和 64 之间")
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	@Length(min=0, max=64, message="赠品id长度必须介于 0 和 64 之间")
	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
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
	
	@Length(min=0, max=64, message="配送周期长度必须介于 0 和 64 之间")
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
	
	@Length(min=0, max=64, message="服务名称长度必须介于 0 和 64 之间")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Length(min=0, max=64, message="服务内容长度必须介于 0 和 64 之间")
	public String getServiceDetail() {
		return serviceDetail;
	}

	public void setServiceDetail(String serviceDetail) {
		this.serviceDetail = serviceDetail;
	}
	
	public BigDecimal getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}
	
	@Length(min=0, max=64, message="服务周期长度必须介于 0 和 64 之间")
	public String getServiceSchedule() {
		return serviceSchedule;
	}

	public void setServiceSchedule(String serviceSchedule) {
		this.serviceSchedule = serviceSchedule;
	}
	
	public Integer getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(Integer serviceNum) {
		this.serviceNum = serviceNum;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(Date deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getDeliveryTotalNum() {
		return deliveryTotalNum;
	}

	public void setDeliveryTotalNum(Integer deliveryTotalNum) {
		this.deliveryTotalNum = deliveryTotalNum;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public GoodsOrderDetail fromOrder(GoodsOrder goodsOrder){
		if (null != goodsOrder){
			this.setMerchantId(goodsOrder.getMerchantId());
			this.setShopId(goodsOrder.getShopId());
			this.setOrderId(goodsOrder.getOrderId());
		}
		return this;
	}
}