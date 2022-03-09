package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 配送计划Entity
 * @author yu.shen
 * @version 2018-08-31
 */
public class DeliveryPlan extends DataEntity<DeliveryPlan> {
	
	private static final long serialVersionUID = 1L;
	private Date theDay;		// 配送日期
	private String merchantId;		// 商家id
	private String merchantName;		// 商家名称
	private String shopId;		// 店铺id
	private String shopName;		// 店铺名称
	private String orderId;		// 配送商品订单id
	private String payStatus;		// 支付状态
	private String orderRemark;		// 订单备注
	private String deliveryOrderId;		// 配送订单id
	private String goodsId;		// 配送商品id
	private String goodsName;		// 配送商品名称
	private Integer goodsNum;		// 配送商品数量
    private String goodsUrl; //  商品图片地址
	private String goodsUnit; // 商品计量单位
	private String goodsInfo; // 商品规格信息
	private String extDeliveryInfo;		// 扩展配送信息
	private Date reserveTime;		// 预约时间
	private String address;		// 配送地址
	private String province;		// 省份
	private String city;		// 城市
	private String area;		// 地区
	private String street;		// 街道
	private String districtArea;		// 所属片区
	private String communityId;		// 配送小区id
	private String communityName;		// 小区名称
	private String buildingsNum;		// 楼栋
	private String floor;		// 层数
	private String houseNum;		// 房号
	private String memberId;		// 会员id
	private String memberName;		// 会员姓名
	private String memberContact;		// 会员联系方式
	private Date deliveryTime;		// 送达时间
	private String status;		// 配送状态
	private String deliveryManId;		// 配送员id
	private String deliveryManName;		// 配送员姓名
	private String deliverManTel;		// 配送员联系方式
    private String giftName; //  赠品名称
    private Integer giftNum; // 赠品数量
    private String giftUrl; // 赠品图片地址
	private String giftUnit; // 赠品计量单位
	private String giftInfo; // 赠品规格信息

	private String serviceContent;	//服务内容

	private Date beginDate; // 查询开始日期
    private Date endDate;  // 查询结束日期


	public DeliveryPlan() {
		super();
	}

	public DeliveryPlan(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getTheDay() {
		return theDay;
	}

	public void setTheDay(Date theDay) {
		this.theDay = theDay;
	}
	
	@Length(min=0, max=64, message="商家id长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商家名称长度必须介于 0 和 64 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=64, message="店铺id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="店铺名称长度必须介于 0 和 64 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Length(min=0, max=64, message="配送商品订单id长度必须介于 0 和 64 之间")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="支付状态长度必须介于 0 和 64 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Length(min=0, max=200, message="订单备注长度必须介于 0 和 200 之间")
	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
	@Length(min=0, max=64, message="配送订单id长度必须介于 0 和 64 之间")
	public String getDeliveryOrderId() {
		return deliveryOrderId;
	}

	public void setDeliveryOrderId(String deliveryOrderId) {
		this.deliveryOrderId = deliveryOrderId;
	}
	
	@Length(min=0, max=64, message="配送商品id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=64, message="配送商品名称长度必须介于 0 和 64 之间")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	@Length(min=0, max=200, message="扩展配送信息长度必须介于 0 和 200 之间")
	public String getExtDeliveryInfo() {
		return extDeliveryInfo;
	}

	public void setExtDeliveryInfo(String extDeliveryInfo) {
		this.extDeliveryInfo = extDeliveryInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(Date reserveTime) {
		this.reserveTime = reserveTime;
	}
	
	@Length(min=0, max=64, message="配送地址长度必须介于 0 和 64 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	@Length(min=0, max=50, message="地区长度必须介于 0 和 50 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
	
	@Length(min=0, max=64, message="配送小区id长度必须介于 0 和 64 之间")
	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	
	@Length(min=0, max=64, message="小区名称长度必须介于 0 和 64 之间")
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
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

    @Length(min=0, max=64, message="会员姓名长度必须介于 0 和 64 之间")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Length(min=0, max=64, message="会员姓名长度必须介于 0 和 64 之间")
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@Length(min=0, max=64, message="会员联系方式长度必须介于 0 和 64 之间")
	public String getMemberContact() {
		return memberContact;
	}

	public void setMemberContact(String memberContact) {
		this.memberContact = memberContact;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	@Length(min=0, max=5, message="配送状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="配送员id长度必须介于 0 和 64 之间")
	public String getDeliveryManId() {
		return deliveryManId;
	}

	public void setDeliveryManId(String deliveryManId) {
		this.deliveryManId = deliveryManId;
	}
	
	@Length(min=0, max=24, message="配送员姓名长度必须介于 0 和 24 之间")
	public String getDeliveryManName() {
		return deliveryManName;
	}

	public void setDeliveryManName(String deliveryManName) {
		this.deliveryManName = deliveryManName;
	}
	
	@Length(min=0, max=64, message="配送员联系方式长度必须介于 0 和 64 之间")
	public String getDeliverManTel() {
		return deliverManTel;
	}

	public void setDeliverManTel(String deliverManTel) {
		this.deliverManTel = deliverManTel;
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

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
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

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getGiftUnit() {
        return giftUnit;
    }

    public void setGiftUnit(String giftUnit) {
        this.giftUnit = giftUnit;
    }

    public String getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(String giftInfo) {
        this.giftInfo = giftInfo;
    }

	public String getServiceContent() {
		return serviceContent;
	}

	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
}