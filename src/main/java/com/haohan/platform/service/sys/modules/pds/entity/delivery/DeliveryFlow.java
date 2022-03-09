package com.haohan.platform.service.sys.modules.pds.entity.delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 物流配送Entity
 * @author haohan
 * @version 2018-10-17
 */
public class DeliveryFlow extends DataEntity<DeliveryFlow> {
	
	private static final long serialVersionUID = 1L;
	private String pmId;			//平台商家ID
	private String deliveryId;		// 配送编号
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;		// 配送日期
	private String truckNo;		// 物流车号
	private String lineNo;		// 线路编号
	private String ondayTrains;		// 当日车次
	private Date loadTruckTime;		// 装车时间
	private Date departTruckTime;		// 发车时间
	private Date finishTime;		// 完成时间
	private String goodsNum;		// 货物数量
	private String goodsWeight;		// 重量
	private String goodsVolume;		// 体积
	private String status;		// 状态
	private Date beginDeliveryDate;		// 开始 配送日期
	private Date endDeliveryDate;		// 结束 配送日期
	private String deliverySeq;		//送货批次
	private String driver;		//送货司机

    // 条件
    private String buyerId;  // 采购商id
    private String finalStatus; // 最终状态

    private String pmName;		// 平台商家名称

	public DeliveryFlow() {
		super();
	}

	public DeliveryFlow(String id){
		super(id);
	}

	@Length(min=0, max=64, message="配送编号长度必须介于 0 和 64 之间")
	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	@Length(min=0, max=64, message="物流车号长度必须介于 0 和 64 之间")
	public String getTruckNo() {
		return truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}
	
	@Length(min=0, max=64, message="线路编号长度必须介于 0 和 64 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=5, message="当日车次长度必须介于 0 和 5 之间")
	public String getOndayTrains() {
		return ondayTrains;
	}

	public void setOndayTrains(String ondayTrains) {
		this.ondayTrains = ondayTrains;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoadTruckTime() {
		return loadTruckTime;
	}

	public void setLoadTruckTime(Date loadTruckTime) {
		this.loadTruckTime = loadTruckTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDepartTruckTime() {
		return departTruckTime;
	}

	public void setDepartTruckTime(Date departTruckTime) {
		this.departTruckTime = departTruckTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	@Length(min=0, max=5, message="货物数量长度必须介于 0 和 5 之间")
	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	public String getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(String goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	
	public String getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(String goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getBeginDeliveryDate() {
		return beginDeliveryDate;
	}

	public void setBeginDeliveryDate(Date beginDeliveryDate) {
		this.beginDeliveryDate = beginDeliveryDate;
	}
	
	public Date getEndDeliveryDate() {
		return endDeliveryDate;
	}

	public void setEndDeliveryDate(Date endDeliveryDate) {
		this.endDeliveryDate = endDeliveryDate;
	}

	public String getDeliverySeq() {
		return deliverySeq;
	}

	public void setDeliverySeq(String deliverySeq) {
		this.deliverySeq = deliverySeq;
	}

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }
}