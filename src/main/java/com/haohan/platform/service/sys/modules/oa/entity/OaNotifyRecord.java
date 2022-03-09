/**
 * Copyright &copy; 2012-2017 <a href="http://www.haohanwork.com">haohan</a> All rights reserved
 */
package com.haohan.platform.service.sys.modules.oa.entity;

import com.haohan.platform.service.sys.common.persistence.DataEntity;
import com.haohan.platform.service.sys.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 通知通告记录Entity
 *
 * @author ThinkGem
 * @version 2014-05-16
 */
public class OaNotifyRecord extends DataEntity<OaNotifyRecord> {

    private static final long serialVersionUID = 1L;
    private OaNotify oaNotify;        // 通知通告ID
    private User user;        // 接受人
    private String readFlag;        // 阅读标记（0：未读；1：已读）
    private Date readDate;        // 阅读时间


    public OaNotifyRecord() {
        super();
    }

    public OaNotifyRecord(String id) {
        super(id);
    }

    public OaNotifyRecord(OaNotify oaNotify) {
        this.oaNotify = oaNotify;
    }

    public OaNotify getOaNotify() {
        return oaNotify;
    }

    public void setOaNotify(OaNotify oaNotify) {
        this.oaNotify = oaNotify;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Length(min = 0, max = 1, message = "阅读标记（0：未读；1：已读）长度必须介于 0 和 1 之间")
    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

}