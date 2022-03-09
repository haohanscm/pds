package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.order.req;

/**
 * @author shenyu
 * @create 2019/1/2
 */
public class ExpressCompanyApiReq {
    private String merchantId;
    private String appid;
    private Integer pageNo;
    private Integer pageSize;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
