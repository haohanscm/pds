package com.haohan.platform.service.sys.modules.xiaodian.entity.printer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;

import com.haohan.platform.service.sys.common.persistence.DataEntity;

/**
 * 易联云打印机管理Entity
 * @author haohan
 * @version 2018-11-26
 */
@JsonIgnoreProperties({"createDate", "updateDate", "isNewRecord","secret","remarks"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CloudPrintTerminal extends DataEntity<CloudPrintTerminal> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家
	private String shopId;		// 店铺
	private String clientId;		// 应用ID
	private String machineCode;		// 打印机编号
	private String secret;		// 密钥
	private String name;		// 打印机名称
	private String status;		// 状态

    private String merchantName;		// 商家
    private String shopName;		// 店铺
	
	public CloudPrintTerminal() {
		super();
	}

	public CloudPrintTerminal(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="店铺长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="应用ID长度必须介于 0 和 64 之间")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Length(min=0, max=64, message="打印机编号长度必须介于 0 和 64 之间")
	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	@Length(min=0, max=64, message="密钥长度必须介于 0 和 64 之间")
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Length(min=0, max=64, message="打印机名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}