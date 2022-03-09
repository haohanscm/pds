package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 配送规则管理Entity
 * @author haohan
 * @version 2018-08-31
 */
public class DeliveryRules extends DataEntity<DeliveryRules> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		//商家id
	private String goodsId;		// 商品id
	private String deliveryType;		// 配送方式	delivery_type  字典  1.快递 2.自提  3.送货上门
	private String deliveryPlanType;		// 配送计划类型delivery_plan_type  0.一次配送 1.周期性配送  2.指定时间配送
	private String deliverySchedule;		// 配送周期 字典delivery_schedule  1.每日 2.每周 3.每月 4.工作日
	private String arriveType;		// 配送时效  字典delivery_arrive  1.隔日达  2.及时达  3.当日达
	private Integer deliveryNum;		// 每次配送数量
	private Integer minNum;		// 起送数量
	private String rulesDesc;		// 规则描述
	private Date specificDate; // 指定时间
    private Integer startDayNum; //配送初始间隔天数
	private Integer deliveryTotalNum;  // 配送总数量
	
	public DeliveryRules() {
		super();
	}

	public DeliveryRules(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}