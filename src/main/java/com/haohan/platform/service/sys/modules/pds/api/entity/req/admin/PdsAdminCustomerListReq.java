package com.haohan.platform.service.sys.modules.pds.api.entity.req.admin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求用户列表
 *
 * @author dy
 * @date 2019/2/13
 */
public class PdsAdminCustomerListReq {

    @NotBlank(message = "pmId不能为空")
    private String pmId;
    private int pageNo;
    private int pageSize;

    @Length(min = 0, max = 64, message = "商家ID长度必须介于 0 和 64 之间")
    private String merchantId;
    @Length(min = 0, max = 64, message = "用户ID长度必须介于 0 和 64 之间")
    private String uid;
    @Length(min = 0, max = 10, message = "用户昵称长度必须介于 0 和 10 之间")
    private String nickName;
    @Length(min = 0, max = 5, message = "应用类型长度必须介于 0 和 5 之间")
    private String appType;


    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
