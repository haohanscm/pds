package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 支付渠道费率管理Entity
 * @author haohan
 * @version 2017-12-11
 */
public class ChannelRateManage extends DataEntity<ChannelRateManage> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String payInfoId;		// 商户账户ID
	private BigDecimal wxQrcode;		// 微信扫码
	private BigDecimal wxPaycard;		// 微信刷卡
	private BigDecimal wxMp;		// 微信公众号
	private BigDecimal alipayQrcode;		// 支付宝扫码
	private BigDecimal alipayBarcode;		// 支付宝条码
	private BigDecimal alipayService;		// 支付宝服务窗
	private String respCode;		// 返回状态码
	private String respMsg;		// 返回描述
	private String status;		// 状态

	private String channel; //渠道名称
	private String rate;  //费率
    private String category; //类目
	private String custId; //银行返回渠道标识ID

	
	public ChannelRateManage() {
		super();
	}

	public ChannelRateManage(String id){
		super(id);
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@Length(min=0, max=64, message="商户账户ID长度必须介于 0 和 64 之间")
	public String getPayInfoId() {
		return payInfoId;
	}

	public void setPayInfoId(String payInfoId) {
		this.payInfoId = payInfoId;
	}



	public BigDecimal getWxQrcode() {
		return wxQrcode;
	}

	public void setWxQrcode(BigDecimal wxQrcode) {
		this.wxQrcode = wxQrcode;
	}
	
	public BigDecimal getWxPaycard() {
		return wxPaycard;
	}

	public void setWxPaycard(BigDecimal wxPaycard) {
		this.wxPaycard = wxPaycard;
	}
	
	public BigDecimal getWxMp() {
		return wxMp;
	}

	public void setWxMp(BigDecimal wxMp) {
		this.wxMp = wxMp;
	}
	
	public BigDecimal getAlipayQrcode() {
		return alipayQrcode;
	}

	public void setAlipayQrcode(BigDecimal alipayQrcode) {
		this.alipayQrcode = alipayQrcode;
	}
	
	public BigDecimal getAlipayBarcode() {
		return alipayBarcode;
	}

	public void setAlipayBarcode(BigDecimal alipayBarcode) {
		this.alipayBarcode = alipayBarcode;
	}
	
	public BigDecimal getAlipayService() {
		return alipayService;
	}

	public void setAlipayService(BigDecimal alipayService) {
		this.alipayService = alipayService;
	}
	
	@Length(min=0, max=32, message="返回状态码长度必须介于 0 和 32 之间")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	@Length(min=0, max=64, message="返回描述长度必须介于 0 和 64 之间")
	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
}