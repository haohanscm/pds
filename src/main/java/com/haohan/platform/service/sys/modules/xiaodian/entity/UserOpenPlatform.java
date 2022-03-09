package com.haohan.platform.service.sys.modules.xiaodian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 开放平台用户管理Entity
 * @author haohan
 * @version 2017-08-05
 */
@JsonIgnoreProperties(value = {"id", "createDate", "isNewRecord", "openId", "unionId", "accessToken", "personalInfo"})
public class UserOpenPlatform extends DataEntity<UserOpenPlatform> {
	
	private static final long serialVersionUID = 1L;
	private String uid;		// 用户ID
	private String openId;		// 第三方唯一标识
	private String unionId;		// 联合唯一id目前仅用于微信
	private String appId;		// 应用key
	private String appType;		// 应用类型
	private String nickName;		// 登录账号
	private String albumUrl;		// 头像地址
	private String sex;		// 性别
	private String personalInfo;		// 个人信息
	private String flushToken;		// 刷新登录标识的标识
	private String accessToken;		// 登录标识
	private Date updateTime;		// 刷新时间
	private Date createTime;		// 创建时间
	private String memo;		// 备注
	private String province;		// 省
	private String city;		// 市
	private String district;		// 区

	private String merchantId;
	private String shopId;

	private Date beginCreateTime;        //查询条件-开始创建时间
	private Date endCreateTime;          //查询条件-结束创建时间
	private Date beginUpdateDate;        //查询条件-开始刷新时间
	private Date endUpdateDate;          //查询条件-结束刷新时间

	private String appName; //应用名称
	private String qrcode;//二维码图片
	private String status;// 关注,未关注
	private String pmId; // 平台商家id

	public UserOpenPlatform() {
		super();
	}

	public UserOpenPlatform(String id){
		super(id);
	}

	@Length(min=0, max=60, message="用户ID长度必须介于 0 和 60 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=50, message="第三方唯一标识长度必须介于 0 和 50 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=50, message="联合唯一id目前仅用于微信长度必须介于 0 和 50 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	@Length(min=0, max=32, message="应用key长度必须介于 0 和 32 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@Length(min=0, max=5, message="应用类型长度必须介于 0 和 5 之间")
	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	@Length(min=0, max=500, message="登录账号长度必须介于 0 和 500 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Length(min=0, max=500, message="头像地址长度必须介于 0 和 500 之间")
	public String getAlbumUrl() {
		return albumUrl;
	}

	public void setAlbumUrl(String albumUrl) {
		this.albumUrl = albumUrl;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=5000, message="个人信息长度必须介于 0 和 5000 之间")
	public String getPersonalInfo() {
		return personalInfo;
	}

	public void setPersonalInfo(String personalInfo) {
		this.personalInfo = personalInfo;
	}
	
	@Length(min=0, max=50, message="刷新登录标识的标识长度必须介于 0 和 50 之间")
	public String getFlushToken() {
		return flushToken;
	}

	public void setFlushToken(String flushToken) {
		this.flushToken = flushToken;
	}
	
	@Length(min=0, max=500, message="登录标识长度必须介于 0 和 500 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=60, message="省长度必须介于 0 和 60 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=60, message="市长度必须介于 0 和 60 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=60, message="区长度必须介于 0 和 60 之间")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginUpdateDate() {
		return beginUpdateDate;
	}

	public void setBeginUpdateDate(Date beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndUpdateDate() {
		return endUpdateDate;
	}

	public void setEndUpdateDate(Date endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }
}
