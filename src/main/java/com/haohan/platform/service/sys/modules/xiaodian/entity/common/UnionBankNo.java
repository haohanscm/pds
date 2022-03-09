package com.haohan.platform.service.sys.modules.xiaodian.entity.common;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 银行行号管理Entity
 * @author haohan
 * @version 2017-12-10
 */
public class UnionBankNo extends DataEntity<UnionBankNo> {
	
	private static final long serialVersionUID = 1L;
	private String bankNo;		// 行号
	private String bankName;		// 银行名称
	private String liquidationNo;		// 清算行号
	
	public UnionBankNo() {
		super();
	}

	public UnionBankNo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="行号长度必须介于 0 和 64 之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=0, max=64, message="银行名称长度必须介于 0 和 64 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=64, message="清算行号长度必须介于 0 和 64 之间")
	public String getLiquidationNo() {
		return liquidationNo;
	}

	public void setLiquidationNo(String liquidationNo) {
		this.liquidationNo = liquidationNo;
	}
	
}