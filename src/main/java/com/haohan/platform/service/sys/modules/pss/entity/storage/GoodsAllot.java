package com.haohan.platform.service.sys.modules.pss.entity.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品调拨Entity
 * @author haohan
 * @version 2018-09-05
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsAllot extends DataEntity<GoodsAllot> {
	
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "merchantId不能为空")
	private String merchantId;		// 商家ID
	private String allotNum;		// 调拨单据编号
	private Integer num;		// 数量
	private BigDecimal totalAmount;		// 总金额
	private String allotoutType;		// 调出类型
	private String allotoutId;		// 调出ID
	private String allotinId;		// 调入ID
	private String allotinType;		// 调入类型
	private String oprateNode;		// 操作备注
	private String operator;		// 操作员
	private Date bizTime;			//操作时间
	private String auditStatus;		// 审核状态
	private String orderStatus;		// 订单状态
	private String auditMan;		// 审核人
	private String bizMan;		// 制单人

	//JOIN字段
	private String allotoutName;		//调出仓库名称
	private String allotinName;			//调入仓库名称

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<GoodsAllotDetail> goodsAllotDetailList;

	public Date getBizTime() {
		return bizTime;
	}

	public void setBizTime(Date bizTime) {
		this.bizTime = bizTime;
	}

	public GoodsAllot() {
		super();
	}

	public GoodsAllot(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="调拨编号长度必须介于 0 和 64 之间")
	public String getAllotNum() {
		return allotNum;
	}

	public void setAllotNum(String allotNum) {
		this.allotNum = allotNum;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Length(min=0, max=5, message="调出类型长度必须介于 0 和 5 之间")
	public String getAllotoutType() {
		return allotoutType;
	}

	public void setAllotoutType(String allotoutType) {
		this.allotoutType = allotoutType;
	}
	
	@Length(min=0, max=64, message="调出仓库长度必须介于 0 和 64 之间")
	public String getAllotoutId() {
		return allotoutId;
	}

	public void setAllotoutId(String allotoutId) {
		this.allotoutId = allotoutId;
	}
	
	@Length(min=0, max=64, message="调入ID长度必须介于 0 和 64 之间")
	public String getAllotinId() {
		return allotinId;
	}

	public void setAllotinId(String allotinId) {
		this.allotinId = allotinId;
	}
	
	@Length(min=0, max=5, message="调入类型长度必须介于 0 和 5 之间")
	public String getAllotinType() {
		return allotinType;
	}

	public void setAllotinType(String allotinType) {
		this.allotinType = allotinType;
	}

	@Length(min=0, max=255, message="操作备注长度必须介于 0 和 255 之间")
	public String getOprateNode() {
		return oprateNode;
	}

	public void setOprateNode(String oprateNode) {
		this.oprateNode = oprateNode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Length(min=0, max=5, message="审核状态长度必须介于 0 和 5 之间")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Length(min=0, max=5, message="订单状态长度必须介于 0 和 5 之间")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Length(min=0, max=64, message="审核人长度必须介于 0 和 64 之间")
	public String getAuditMan() {
		return auditMan;
	}

	public void setAuditMan(String auditMan) {
		this.auditMan = auditMan;
	}
	
	@Length(min=0, max=64, message="制单人长度必须介于 0 和 64 之间")
	public String getBizMan() {
		return bizMan;
	}

	public void setBizMan(String bizMan) {
		this.bizMan = bizMan;
	}

	public String getAllotoutName() {
		return allotoutName;
	}

	public void setAllotoutName(String allotoutName) {
		this.allotoutName = allotoutName;
	}

	public String getAllotinName() {
		return allotinName;
	}

	public void setAllotinName(String allotinName) {
		this.allotinName = allotinName;
	}

	public List<GoodsAllotDetail> getGoodsAllotDetailList() {
		return goodsAllotDetailList;
	}

	public void setGoodsAllotDetailList(List<GoodsAllotDetail> goodsAllotDetailList) {
		this.goodsAllotDetailList = goodsAllotDetailList;
	}
}