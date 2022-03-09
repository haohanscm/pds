package com.haohan.platform.service.sys.modules.pss.entity.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 供应商结款记录Entity
 * @author yu
 * @version 2018-10-11
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierPayrecord extends DataEntity<SupplierPayrecord> {
	
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "merchantId不能为空")
	private String merchantId;		// 商家ID
	@NotBlank(message = "supplierId不能为空")
	private String supplierId;		// 供应商ID
	@NotNull(message = "付款金额不能为空")
	private String payAmount;		// 结算款金额
	private Date payTime;		// 还款时间
	private String payNote;		// 备注信息
	
	public SupplierPayrecord() {
		super();
	}

	public SupplierPayrecord(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="供应商ID长度必须介于 0 和 64 之间")
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=0, max=200, message="备注信息长度必须介于 0 和 200 之间")
	public String getPayNote() {
		return payNote;
	}

	public void setPayNote(String payNote) {
		this.payNote = payNote;
	}
	
}