package com.haohan.platform.service.sys.modules.xiaodian.api.entity.merchant.manage;


import java.io.Serializable;

/**
 * 商户后台 管理信息请求参数
 * Created by dy on 2018/10/9.
 */
public class ReqManage implements Serializable {

    private int pageNo;
    private int pageSize;
    private String shopId;

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
