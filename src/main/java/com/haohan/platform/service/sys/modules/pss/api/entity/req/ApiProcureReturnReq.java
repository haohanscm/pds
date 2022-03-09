package com.haohan.platform.service.sys.modules.pss.api.entity.req;

import com.haohan.platform.service.sys.modules.pss.entity.procure.PurchaseReturn;

/**
 * @author shenyu
 * @create 2018/11/29
 */
public class ApiProcureReturnReq extends PurchaseReturn {
    private Integer pageNo;
    private Integer pageSize;
    private String returnId;

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

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }
}
