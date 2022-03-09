package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 赠品管理Entity
 * @author haohan
 * @version 2018-08-31
 */
public class GoodsGifts extends DataEntity<GoodsGifts> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;	//商家id
	private String goodsId;		// 商品id
	private String giftId;		// 赠品id
	private String giftName;		// 赠品名称
	private String giftRule;		// 赠送规则描述
	private Date beginDate;		// 起始日期
	private Date endDate;		// 结束日期
	private String giftSchedule;		// 赠送周期
	private Integer giftNum;		// 赠品数量
	private String giftUrl;   // 赠品图片地址

//	private String isExpires;	//是否过期
	
	public GoodsGifts() {
		super();
	}

	public GoodsGifts(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Boolean checkIsExpires(){
		Date now = new Date();
		if (null == this.getBeginDate() && null == this.getEndDate()){
			return false;
		}else if (null != this.getBeginDate() && null == this.getEndDate()){
			if (now.compareTo(beginDate) >= 0){
				return false;
			} else return true;
		}else if (null == this.getBeginDate() && null != this.getEndDate()){
			if (now.compareTo(endDate) <= 0){
				return false;
			} else return true;
		}else {
			if (now.compareTo(beginDate) > 0 && now.compareTo(endDate) < 0){
				return false;
			} else return true;

		}
	}
}