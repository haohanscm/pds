package com.haohan.platform.service.sys.modules.weixin.mp.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haohan.platform.service.sys.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 微信用户消息事件Entity
 * @author haohan
 * @version 2018-11-07
 */
public class WechatUserMsgevent extends DataEntity<WechatUserMsgevent> {

	private static final long serialVersionUID = 1L;
	private String msgId;		// 消息ID
	private String openWxName;		// 授权微信名称
	private String openWxId;		// 授权微信ID
	private String wxId;		// 微信ID
	private String wxName;		// 微信名称
	private String wxType;		// 微信类型
	private String passportUid;		// 用户通行证ID
	private String openUid;		// 开放用户ID
	private String nickName;		// 用户昵称
	private String albumUrl;		// 用户头像
	private String openId;		// 用户openid
	private String unionId;		// 用户unionid
	private String msgType;		// 消息类型
	private String msgName;		// 消息名称
	private String msgContent;		// 消息内容
	private Date sendTime;		// 发送时间
	private String fullMsgPkg;		// 完整消息包
	private Date beginSendTime;		// 开始 发送时间
	private Date endSendTime;		// 结束 发送时间

	public WechatUserMsgevent() {
		super();
	}

	public WechatUserMsgevent(String id){
		super(id);
	}

	@Length(min=0, max=64, message="消息ID长度必须介于 0 和 64 之间")
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Length(min=0, max=64, message="授权微信名称长度必须介于 0 和 64 之间")
	public String getOpenWxName() {
		return openWxName;
	}

	public void setOpenWxName(String openWxName) {
		this.openWxName = openWxName;
	}

	@Length(min=0, max=64, message="授权微信ID长度必须介于 0 和 64 之间")
	public String getOpenWxId() {
		return openWxId;
	}

	public void setOpenWxId(String openWxId) {
		this.openWxId = openWxId;
	}

	@Length(min=0, max=64, message="微信ID长度必须介于 0 和 64 之间")
	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	@Length(min=0, max=64, message="微信名称长度必须介于 0 和 64 之间")
	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	@Length(min=0, max=64, message="微信类型长度必须介于 0 和 64 之间")
	public String getWxType() {
		return wxType;
	}

	public void setWxType(String wxType) {
		this.wxType = wxType;
	}

	@Length(min=0, max=64, message="用户通行证ID长度必须介于 0 和 64 之间")
	public String getPassportUid() {
		return passportUid;
	}

	public void setPassportUid(String passportUid) {
		this.passportUid = passportUid;
	}

	@Length(min=0, max=64, message="开放用户ID长度必须介于 0 和 64 之间")
	public String getOpenUid() {
		return openUid;
	}

	public void setOpenUid(String openUid) {
		this.openUid = openUid;
	}

	@Length(min=0, max=64, message="用户昵称长度必须介于 0 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Length(min=0, max=255, message="用户头像长度必须介于 0 和 255 之间")
	public String getAlbumUrl() {
		return albumUrl;
	}

	public void setAlbumUrl(String albumUrl) {
		this.albumUrl = albumUrl;
	}

	@Length(min=0, max=64, message="用户openid长度必须介于 0 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Length(min=0, max=64, message="用户unionid长度必须介于 0 和 64 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	@Length(min=0, max=64, message="消息类型长度必须介于 0 和 64 之间")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Length(min=0, max=64, message="消息名称长度必须介于 0 和 64 之间")
	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	@Length(min=0, max=2000, message="消息内容长度必须介于 0 和 2000 之间")
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getFullMsgPkg() {
		return fullMsgPkg;
	}

	public void setFullMsgPkg(String fullMsgPkg) {
		this.fullMsgPkg = fullMsgPkg;
	}

	public Date getBeginSendTime() {
		return beginSendTime;
	}

	public void setBeginSendTime(Date beginSendTime) {
		this.beginSendTime = beginSendTime;
	}

	public Date getEndSendTime() {
		return endSendTime;
	}

	public void setEndSendTime(Date endSendTime) {
		this.endSendTime = endSendTime;
	}

}