package com.haohan.platform.service.sys.modules.xiaodian.entity.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 商家支付信息Entity
 * @author haohan
 * @version 2017-12-10
 */
public class MerchantPayInfo extends DataEntity<MerchantPayInfo> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// 商家ID
	private String partnerId;		// 商户编号
    private String authCode;
	private String mercNm;		// 商家名称
	private String mercAbbr;		// 商户简称
	private String mercTyp;		//商户类型 固定值3
	private String mercTrdCls;		// 微信经营一级类目  用做京东渠道微信ID
	private String mercSubCls;		// 微信经营二级类目
	private String mercThirdCls;		// 微信经营三级类目
	private String mercThirdClsAll;		// 支付宝经营类目
	private String crpNm;		// 子商户法人姓名
	private String crpIdNo;		// 子商户法人身份证号
	private String crpIdExpireDay;// 身份证过期日期
	private String regId;		// 注册登记号
	private String organizationCode;		// 组织机构代码
	private String busAddr;		// 经营地址
	private String mercProv;		// 归属省
	private String mercCity;		// 归属市
	private String agrNo;		// 协议编号
	private String usrOprNm;		// 子商户管理员
	private String usrOprMbl;		// 管理员手机号码
	private String stlPerd;		// 固定值2
	private String stlDayFlg;		// 固定值T
	private String wcLbnkNo;		// 结算卡开户行支行联行行号
	private String busPsnFlg;		// 结算账号公私标志
	private String stlOac;		// 子商户结算卡卡号
	private String bnkAcnm;		// 结算卡户主
	private String wcBank;		// 结算卡开户行简称。
	private String lbnkNm;		// 支行名称
	private String opnBnkProv;		// 开户行省
	private String opnBnkCity;		// 开户行市
	private String usrOprLogId;		// 客户经理登录ID
	private String respCode;		// 返回状态码
	private String respDesc;		// 返回信息
	private Date regTime;		// 开户时间
	private Integer regStatus;		// 开户状态
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间

	//koolyun API接口增加字段
	private String mercArea; 		//归属区 merc_area
	private String contactEmail;  //联系人邮箱 contact_email
	private String bankBranchNo; //网点联号 bank_branch_no
	private String idExpireTime; //执照有效期 idexpire_time
	private String merchantLevel="2"; //商家级别 merchant_level 默认为2
	private String opnBnkArea;		// 开户行区

	private String bankChannel;		//银行渠道
	private String isEnable;		//是否启用

	private String custId; //中间变量

	
	public MerchantPayInfo() {
		super();
	}

	public  MerchantPayInfo(String merchantId,String partnerId){
		this.merchantId=merchantId;
		this.partnerId=partnerId;
	}

	public MerchantPayInfo(String id){
		super(id);
	}


	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Length(min=0, max=64, message="商家ID长度必须介于 0 和 64 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=32, message="商户编号长度必须介于 0 和 32 之间")
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public void setSubPartnerId(String partnerId)
    {
		this.partnerId = partnerId;
	}
    @Length(min=0, max=32, message="商户授权码长度必须介于 0 和 32 之间")
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Length(min=0, max=128, message="商家名称长度必须介于 0 和 128 之间")
	public String getMercNm() {
		return mercNm;
	}

	public void setMercNm(String mercNm) {
		this.mercNm = mercNm;
	}
	
	@Length(min=0, max=128, message="商户简称长度必须介于 0 和 128 之间")
	public String getMercAbbr() {
		return mercAbbr;
	}

	public void setMercAbbr(String mercAbbr) {
		this.mercAbbr = mercAbbr;
	}
	
	@Length(min=0, max=5, message="固定值3长度必须介于 0 和 5 之间")
	public String getMercTyp() {
		return mercTyp;
	}

	public void setMercTyp(String mercTyp) {
		this.mercTyp = mercTyp;
	}
	
	@Length(min=0, max=32, message="微信经营一级类目长度必须介于 0 和 32 之间")
	public String getMercTrdCls() {
		return mercTrdCls;
	}

	public void setMercTrdCls(String mercTrdCls) {
		this.mercTrdCls = mercTrdCls;
	}
	
	@Length(min=0, max=32, message="微信经营二级类目长度必须介于 0 和 32 之间")
	public String getMercSubCls() {
		return mercSubCls;
	}

	public void setMercSubCls(String mercSubCls) {
		this.mercSubCls = mercSubCls;
	}
	
	@Length(min=0, max=32, message="微信经营三级类目长度必须介于 0 和 32 之间")
	public String getMercThirdCls() {
		return mercThirdCls;
	}

	public void setMercThirdCls(String mercThirdCls) {
		this.mercThirdCls = mercThirdCls;
	}
	
	@Length(min=0, max=32, message="支付宝经营类目长度必须介于 0 和 32 之间")
	public String getMercThirdClsAll() {
		return mercThirdClsAll;
	}

	public void setMercThirdClsAll(String mercThirdClsAll) {
		this.mercThirdClsAll = mercThirdClsAll;
	}
	
	@Length(min=0, max=32, message="子商户法人姓名长度必须介于 0 和 32 之间")
	public String getCrpNm() {
		return crpNm;
	}

	public void setCrpNm(String crpNm) {
		this.crpNm = crpNm;
	}
	
	@Length(min=0, max=32, message="子商户法人身份证号长度必须介于 0 和 32 之间")
	public String getCrpIdNo() {
		return crpIdNo;
	}

	public void setCrpIdNo(String crpIdNo) {
		this.crpIdNo = crpIdNo;
	}
	
	@Length(min=0, max=32, message="注册登记号长度必须介于 0 和 32 之间")
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	@Length(min=0, max=32, message="组织机构代码长度必须介于 0 和 32 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Length(min=0, max=128, message="经营地址长度必须介于 0 和 128 之间")
	public String getBusAddr() {
		return busAddr;
	}

	public void setBusAddr(String busAddr) {
		this.busAddr = busAddr;
	}
	
	@Length(min=0, max=32, message="归属省长度必须介于 0 和 32 之间")
	public String getMercProv() {
		return mercProv;
	}

	public void setMercProv(String mercProv) {
		this.mercProv = mercProv;
	}
	
	@Length(min=0, max=32, message="归属市长度必须介于 0 和 32 之间")
	public String getMercCity() {
		return mercCity;
	}

	public void setMercCity(String mercCity) {
		this.mercCity = mercCity;
	}
	
	@Length(min=0, max=32, message="协议编号长度必须介于 0 和 32 之间")
	public String getAgrNo() {
		return agrNo;
	}

	public void setAgrNo(String agrNo) {
		this.agrNo = agrNo;
	}
	
	@Length(min=0, max=32, message="子商户管理员长度必须介于 0 和 32 之间")
	public String getUsrOprNm() {
		return usrOprNm;
	}

	public void setUsrOprNm(String usrOprNm) {
		this.usrOprNm = usrOprNm;
	}
	
	@Length(min=0, max=32, message="管理员手机号码长度必须介于 0 和 32 之间")
	public String getUsrOprMbl() {
		return usrOprMbl;
	}

	public void setUsrOprMbl(String usrOprMbl) {
		this.usrOprMbl = usrOprMbl;
	}
	
	@Length(min=0, max=32, message="固定值2长度必须介于 0 和 32 之间")
	public String getStlPerd() {
		return stlPerd;
	}

	public void setStlPerd(String stlPerd) {
		this.stlPerd = stlPerd;
	}
	
	@Length(min=0, max=32, message="固定值T长度必须介于 0 和 32 之间")
	public String getStlDayFlg() {
		return stlDayFlg;
	}

	public void setStlDayFlg(String stlDayFlg) {
		this.stlDayFlg = stlDayFlg;
	}
	
	@Length(min=0, max=32, message="结算卡开户行支行联行行号长度必须介于 0 和 32 之间")
	public String getWcLbnkNo() {
		return wcLbnkNo;
	}

	public void setWcLbnkNo(String wcLbnkNo) {
		this.wcLbnkNo = wcLbnkNo;
	}
	
	@Length(min=0, max=1, message="结算账号公私标志长度必须介于 0 和 1 之间")
	public String getBusPsnFlg() {
		return busPsnFlg;
	}

	public void setBusPsnFlg(String busPsnFlg) {
		this.busPsnFlg = busPsnFlg;
	}
	
	@Length(min=0, max=32, message="子商户结算卡卡号长度必须介于 0 和 32 之间")
	public String getStlOac() {
		return stlOac;
	}

	public void setStlOac(String stlOac) {
		this.stlOac = stlOac;
	}
	
	@Length(min=0, max=32, message="结算卡户主长度必须介于 0 和 32 之间")
	public String getBnkAcnm() {
		return bnkAcnm;
	}

	public void setBnkAcnm(String bnkAcnm) {
		this.bnkAcnm = bnkAcnm;
	}
	
	@Length(min=0, max=32, message="结算卡开户行简称。长度必须介于 0 和 32 之间")
	public String getWcBank() {
		return wcBank;
	}

	public void setWcBank(String wcBank) {
		this.wcBank = wcBank;
	}
	
	@Length(min=0, max=32, message="支行名称长度必须介于 0 和 32 之间")
	public String getLbnkNm() {
		return lbnkNm;
	}

	public void setLbnkNm(String lbnkNm) {
		this.lbnkNm = lbnkNm;
	}
	
	@Length(min=0, max=32, message="开户行省长度必须介于 0 和 32 之间")
	public String getOpnBnkProv() {
		return opnBnkProv;
	}

	public void setOpnBnkProv(String opnBnkProv) {
		this.opnBnkProv = opnBnkProv;
	}
	
	@Length(min=0, max=32, message="开户行市长度必须介于 0 和 32 之间")
	public String getOpnBnkCity() {
		return opnBnkCity;
	}

	public void setOpnBnkCity(String opnBnkCity) {
		this.opnBnkCity = opnBnkCity;
	}
	
	@Length(min=0, max=32, message="客户经理登录ID长度必须介于 0 和 32 之间")
	public String getUsrOprLogId() {
		return usrOprLogId;
	}

	public void setUsrOprLogId(String usrOprLogId) {
		this.usrOprLogId = usrOprLogId;
	}
	
	@Length(min=0, max=32, message="返回状态码长度必须介于 0 和 32 之间")
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	public Integer getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(Integer regStatus) {
		this.regStatus = regStatus;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getMercArea() {
		return mercArea;
	}

	public void setMercArea(String mercArea) {
		this.mercArea = mercArea;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getBankBranchNo() {
		return bankBranchNo;
	}

	public void setBankBranchNo(String bankBranchNo) {
		this.bankBranchNo = bankBranchNo;
	}

	public String getIdExpireTime() {
		return idExpireTime;
	}

	public void setIdExpireTime(String idExpireTime) {
		this.idExpireTime = idExpireTime;
	}


	public String getMerchantLevel() {
		return merchantLevel;
	}

	public void setMerchantLevel(String merchantLevel) {
		this.merchantLevel = merchantLevel;
	}

	public String getOpnBnkArea() {
		return opnBnkArea;
	}

	public void setOpnBnkArea(String opnBnkArea) {
		this.opnBnkArea = opnBnkArea;
	}

	public String getBankChannel() {
		return bankChannel;
	}

	public void setBankChannel(String bankChannel) {
		this.bankChannel = bankChannel;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getCrpIdExpireDay() {
		return crpIdExpireDay;
	}

	public void setCrpIdExpireDay(String crpIdExpireDay) {
		this.crpIdExpireDay = crpIdExpireDay;
	}
}