package com.haohan.platform.service.sys.modules.xiaodian.entity.delivery;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.Area;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 售卖规则管理Entity
 * @author haohan
 * @version 2018-08-31
 */
public class SaleRules extends DataEntity<SaleRules> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;	//商家id
	private String goodsId;		// 商品id
	private Area area;		// 售卖区域
	private String saleArriveType;		// 售卖时效 暂无使用
	private Integer minSaleNum;		// 起售数量
	private Integer limitBuyTimes;		// 限制购买次数
	private String saleDeliveryType;		// 配送类型限制  支持配送方式
	private Date beginSaleDate;  // 起售时间
	private Date endSaleDate;  //  结束售卖时间
	
	public SaleRules() {
		super();
	}

	public SaleRules(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
//	@Length(min=0, max=1000, message="售卖区域长度必须介于 0 和 1000 之间")
//	public String getSaleAreas() {
//		return saleAreas;
//	}
//
//	public void setSaleAreas(String saleAreas) {
//		this.saleAreas = saleAreas;
//	}


	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
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

	@Length(min=0, max=64, message="配送类型限制长度必须介于 0 和 64 之间")
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
}