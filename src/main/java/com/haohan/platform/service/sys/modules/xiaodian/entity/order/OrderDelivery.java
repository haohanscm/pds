package com.haohan.platform.service.sys.modules.xiaodian.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单配送信息管理Entity
 * @author haohan
 * @version 2018-08-31
 */
public class OrderDelivery extends DataEntity<OrderDelivery> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商户ID
	private String merchantName;		// 商户名称
	private String shopId;		// 店铺ID
	private String userId;		// 用户ID
	private String orderFrom;		// 订单来源 0服务员下单,1用户扫码 2在线订单
	private String orderType;		// 订单类型 0餐饮订单1零售
	private String orderStatus;		// 订单的状态;0未确认,1确认,2已取消,3无效,4退货
	private String orderId;		// 订单号
	private BigDecimal deliveryFee;		// 配送费用
	private BigDecimal insureFee;		// 保价费用
	private BigDecimal payFee;		// 支付费用
	private BigDecimal serviceFee;		// 服务费用
	private BigDecimal moneyPaid;		// 已付款金额
	private BigDecimal orderAmount;		// 应付金额
	private String payMark;		// 付款备注
	private String payType;		// 支付方式名称
	private String payStatus;		// 支付状态 0未付款;  1已付款中;  2已付款
    private String province;      // 省份
    private String city;      // 城市
    private String region;      // 地区
    private String street;      // 街道
    private String districtArea;      // 所属片区
    private String communityName;      // 小区名称
    private String communityId;      // 小区id
    private String buildingsNum;      // 楼栋
    private String floor;      // 层数
    private String houseNum;      // 房号
	private String address;		// 详细地址
	private String zipcode;		// 邮编
	private String deliveryStatus;		// 商品配送情况;   0未配送 1配送中 2已配送  OrderDeliveryStatus
	private String deliveryManId;		//配送员id
	private String deliveryManName;		// 送货员
	private String deliveryManTel;		// 送货员联系电话
	private String receiver;		// 接收人
	private String receiverMobile;		// 联系电话
	private String orderMark;		// 订单留言
	private Date startDeliveryDate;		// 起送日期
	private String deliveryOntime;		// 预约配送时间
	private String arriveType;		// 配送时效
	private String deliveryType;		// 配送方式

//	private String memberAddressId;		//会员地址id
	private String planGenStatus;		//计划生成状态
	private String planGenDesc;			//生成结果描述
	private String expressCompany;		//快递公司
	private String expressOrder;		//快递单号

	//中间字段
	private String shopName;
	private Date beginDate;			//查询开始时间
	private Date endDate;			//查询结束时间
	
	public OrderDelivery() {
		super();
	}

	public OrderDelivery(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商户ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户名称长度必须介于 0 和 64 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=64, message="店铺ID长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="用户ID长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=1, message="订单来源 0服务员下单,1用户扫码 2在线订单长度必须介于 0 和 1 之间")
	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	
	@Length(min=0, max=1, message="订单类型 0餐饮订单1零售长度必须介于 0 和 1 之间")
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@Length(min=0, max=1, message="订单的状态;0未确认,1确认,2已取消,3无效,4退货长度必须介于 0 和 1 之间")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Length(min=0, max=64, message="订单号长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	
	public BigDecimal getInsureFee() {
		return insureFee;
	}

	public void setInsureFee(BigDecimal insureFee) {
		this.insureFee = insureFee;
	}
	
	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}
	
	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	
	public BigDecimal getMoneyPaid() {
		return moneyPaid;
	}

	public void setMoneyPaid(BigDecimal moneyPaid) {
		this.moneyPaid = moneyPaid;
	}
	
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	@Length(min=0, max=100, message="付款备注长度必须介于 0 和 100 之间")
	public String getPayMark() {
		return payMark;
	}

	public void setPayMark(String payMark) {
		this.payMark = payMark;
	}
	
	@Length(min=0, max=50, message="支付方式名称长度必须介于 0 和 50 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=32, message="支付状态 0未付款;  1已付款中;  2已付款长度必须介于 0 和 32 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

    @Length(min=0, max=50, message="省份长度必须介于 0 和 50 之间")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }


    @Length(min=0, max=50, message="城市长度必须介于 0 和 50 之间")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Length(min=0, max=50, message="街道长度必须介于 0 和 50 之间")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    @Length(min=0, max=50, message="所属片区长度必须介于 0 和 50 之间")
    public String getDistrictArea() {
        return districtArea;
    }

    public void setDistrictArea(String districtArea) {
        this.districtArea = districtArea;
    }


    @Length(min=0, max=50, message="小区名称长度必须介于 0 和 50 之间")
    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }


    @Length(min=0, max=50, message="小区id长度必须介于 0 和 50 之间")
    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }


    @Length(min=0, max=50, message="楼栋长度必须介于 0 和 50 之间")
    public String getBuildingsNum() {
        return buildingsNum;
    }

    public void setBuildingsNum(String buildingsNum) {
        this.buildingsNum = buildingsNum;
    }


    @Length(min=0, max=50, message="层数长度必须介于 0 和 50 之间")
    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Length(min=0, max=50, message="房号长度必须介于 0 和 50 之间")
    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    @Length(min=0, max=500, message="详细地址长度必须介于 0 和 500 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=50, message="邮编长度必须介于 0 和 50 之间")
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@Length(min=0, max=1, message="商品配送情况;0未发货,1已发货,2已收货,4退货长度必须介于 0 和 1 之间")
	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryManId() {
		return deliveryManId;
	}

	public void setDeliveryManId(String deliveryManId) {
		this.deliveryManId = deliveryManId;
	}

	public String getDeliveryManName() {
		return deliveryManName;
	}

	public void setDeliveryManName(String deliveryManName) {
		this.deliveryManName = deliveryManName;
	}

	public String getDeliveryManTel() {
		return deliveryManTel;
	}

	public void setDeliveryManTel(String deliveryManTel) {
		this.deliveryManTel = deliveryManTel;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	@Length(min=0, max=200, message="订单留言长度必须介于 0 和 200 之间")
	public String getOrderMark() {
		return orderMark;
	}

	public void setOrderMark(String orderMark) {
		this.orderMark = orderMark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDeliveryDate() {
		return startDeliveryDate;
	}

	public void setStartDeliveryDate(Date startDeliveryDate) {
		this.startDeliveryDate = startDeliveryDate;
	}
	
	@Length(min=0, max=100, message="预约配送时间长度必须介于 0 和 100 之间")
	public String getDeliveryOntime() {
		return deliveryOntime;
	}

	public void setDeliveryOntime(String deliveryOntime) {
		this.deliveryOntime = deliveryOntime;
	}
	
	@Length(min=0, max=64, message="配送时效长度必须介于 0 和 64 之间")
	public String getArriveType() {
		return arriveType;
	}

	public void setArriveType(String arriveType) {
		this.arriveType = arriveType;
	}
	
	@Length(min=0, max=64, message="配送方式长度必须介于 0 和 64 之间")
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

//	public void setMemberAddressId(String memberAddressId) {
//		this.memberAddressId = memberAddressId;
//	}

	public String getPlanGenStatus() {
		return planGenStatus;
	}

	public void setPlanGenStatus(String planGenStatus) {
		this.planGenStatus = planGenStatus;
	}

	public String getPlanGenDesc() {
		return planGenDesc;
	}

	public void setPlanGenDesc(String planGenDesc) {
		this.planGenDesc = planGenDesc;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressOrder() {
		return expressOrder;
	}

	public void setExpressOrder(String expressOrder) {
		this.expressOrder = expressOrder;
	}
}